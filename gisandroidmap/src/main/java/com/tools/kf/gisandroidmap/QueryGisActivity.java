package com.tools.kf.gisandroidmap;

import android.support.v7.widget.Toolbar;

import com.tools.kf.ui.base.BaseAppCompatActivity;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

@ContentView(R.layout.activity_query_gis)
public class QueryGisActivity extends BaseAppCompatActivity {

    @ViewInject(R.id.common_toolbar)
    public Toolbar common_toolbar;


    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar() {
        if (common_toolbar != null) {
            common_toolbar.setTitle("GIS信息");
            common_toolbar.setNavigationIcon(R.mipmap.ab_back);
            setSupportActionBar(common_toolbar);
        }
    }
}
