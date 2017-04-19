package com.tools.kf.gisandroidmap;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.map.BaiduMap;
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
import com.tools.kf.ui.base.BaseAppCompatActivity;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

@ContentView(R.layout.activity_showship)
public class ShowShipCrackActivity extends BaseAppCompatActivity {

    private static Boolean flag = true;
    private BaiduMap mBaiduMap;
    private Double[] mdata1 = new Double[]{122.334235, 29.949103};
    private Double[] mdata2 = new Double[]{122.334882, 29.946975};
    private LatLng p1 = null;
    private LatLng p2 = null;
    private MediaPlayer player = null;
    @ViewInject(R.id.mapview)
    public MapView mMapView;
    @ViewInject(R.id.common_toolbar)
    public Toolbar common_toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    @Override
    protected void initToolBar() {
        if (common_toolbar != null) {

            setSupportActionBar(common_toolbar);
            common_toolbar.setTitle("碰撞模拟");
            common_toolbar.setNavigationIcon(R.mipmap.ab_back);
            // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
            common_toolbar.setOnMenuItemClickListener(onMenuItemClick);
        }
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

    @Override
    public void initData() {

        p1 = new LatLng(mdata1[1], mdata1[0]);

        p2 = new LatLng(mdata2[1], mdata2[0]);
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                .target(p1).build());
        mMapView.getMap().setMapStatus(mMapStatusUpdate);
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
        if (player != null)
            player.reset();
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_refresh:
                    initData();
                    break;
            }

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
