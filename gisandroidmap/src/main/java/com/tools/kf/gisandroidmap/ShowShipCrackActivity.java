package com.tools.kf.gisandroidmap;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class ShowShipCrackActivity extends AppCompatActivity {

    private static Boolean flag = true;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Double[] mdata1 = new Double[]{122.334235, 29.949103};
    private Double[] mdata2 = new Double[]{122.334882, 29.946975};
    private LatLng p1 = null;
    private LatLng p2 = null;
    private MediaPlayer player = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        setContentView(mMapView);

        mBaiduMap = mMapView.getMap();
        //添加周围船位置
        addPiontMarker(p2, R.mipmap.icon_marka);
        //初始化当前用户船位置
        initLocation();
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    while (flag) {
                        Thread.sleep(3000);

                        p1 = new LatLng(p1.latitude, p1.longitude + 0.00001 * ((int) (Math.random() * 50)));

                        startLocation(p1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void initLocation() {
        MapStatusUpdate u = MapStatusUpdateFactory.zoomBy(5);
        mBaiduMap.animateMapStatus(u);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_geo);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                null, true,
                bitmap));


    }

    public void startLocation(LatLng location) {
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(1f)//设置定位数据的精度信息，单位：米
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(location.latitude)
                .longitude(location.longitude).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        double distance = CommonHelper.getDistance(location, p2);
        if (distance < 280) {
            soundRing();
        }
    }

    public void soundRing() {

        if (player == null) {
            player = new MediaPlayer().create(this, R.raw.jbvoice);
        }
        player.start();

    }

    public void initData() {

        p1 = new LatLng(mdata1[1], mdata1[0]);

        p2 = new LatLng(mdata2[1], mdata2[0]);

        mMapView = new MapView(this,
                new BaiduMapOptions().mapStatus(new MapStatus.Builder()
                        .target(p1).build()));

    }

    public void addPiontMarker(LatLng point, int rId) {

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(rId);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        flag = false;
    }
}