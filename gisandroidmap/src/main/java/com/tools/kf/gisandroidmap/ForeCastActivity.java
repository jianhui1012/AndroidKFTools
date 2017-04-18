package com.tools.kf.gisandroidmap;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tools.kf.request.OkHttpUtils;
import com.tools.kf.ui.base.BaseAppCompatActivity;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ContentView(R.layout.activity_fore_cast)
public class ForeCastActivity extends BaseAppCompatActivity {

    @ViewInject(R.id.common_toolbar)
    private Toolbar common_toolbar;

    @ViewInject(R.id.city)
    private TextView city;

    @ViewInject(R.id.weather)
    private TextView weather;

    @ViewInject(R.id.wind_temperature)
    private TextView wind_temperature;

    @ViewInject(R.id.week_list)
    private ListView week_list;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    protected void initData() {
        mLocationClient = new LocationClient(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public void loadWeather(final String cityname) {
        OkHttpUtils.getInstance().getRequestMethod(this, "http://api.map.baidu.com/telematics/v3/weather?location=" + cityname + "&output=json&ak=DtezwdVStwyAj1yiEk19W7K5&mcode=00:7A:8B:3F:A6:A4:73:A9:0E:62:B6:50:DB:E7:88:C6:29:13:0D:2B;com.tools.kf.gisandroidmap", new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String data = response.body().string();
                ForeCastActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loadListView(cityname, data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });


    }

    private void loadListView(String cityname, String data) throws JSONException {
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        Map<String, Object> listem;

        JSONObject jsonObject = new JSONObject(data);
        JSONArray results = jsonObject.getJSONArray("results");
        JSONArray weather_data = results.getJSONObject(0).getJSONArray("weather_data");
        for (int i = 1; i < weather_data.length(); i++) {
            JSONObject weather_day = weather_data.getJSONObject(i);
            listem = new HashMap<String, Object>();
            listem.put("head", weather_day.getString("dayPictureUrl"));
            listem.put("name", weather_day.getString("date"));
            listem.put("desc", weather_day.getString("wind") + "  " + weather_day.getString("temperature"));
            listems.add(listem);
        }
        city.setText(cityname);
        weather.setText(weather_data.getJSONObject(0).getString("weather"));
        wind_temperature.setText(weather_data.getJSONObject(0).getString("wind") + "  "+ weather_data.getJSONObject(0).getString("temperature"));
        week_list.setAdapter(new WeekWeatherAdapter(ForeCastActivity.this, listems));
    }

    @Override
    protected void initToolBar() {
        if (common_toolbar != null) {
            common_toolbar.setTitle("天气预报");
            common_toolbar.setNavigationIcon(R.mipmap.ab_back);
            setSupportActionBar(common_toolbar);
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                loadWeather(location.getCity());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                loadWeather(location.getCity());
            }
        }
    }
}


