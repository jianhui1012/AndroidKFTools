package com.tools.kf.gisandroidmap;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.tools.kf.ui.BasicActivity;
import com.tools.kf.ui.base.BaseAppCompatActivity;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

//设置布局注解
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseAppCompatActivity {
    //设置控件注解
    @ViewInject(R.id.gis_re)
    RelativeLayout gis_re;

    @ViewInject(R.id.forecast_re)
    RelativeLayout forecast_re;

    @ViewInject(R.id.wxjb_re)
    RelativeLayout wxjb_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置跳转事件
        gis_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(QueryGisActivity.class);
            }
        });

        forecast_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ForeCastActivity.class);
            }
        });

        wxjb_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ShowShipCrackActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar() {

    }
}
