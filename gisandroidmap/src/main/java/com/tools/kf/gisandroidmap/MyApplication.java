package com.tools.kf.gisandroidmap;

import android.app.Application;

import com.tools.kf.KFToolsCenter;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KFToolsCenter.Ext.init(this);
        KFToolsCenter.Ext.setIsdebug(true);
    }
}
