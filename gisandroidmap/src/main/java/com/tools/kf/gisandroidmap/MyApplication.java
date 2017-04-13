package com.tools.kf.gisandroidmap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
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
        SDKInitializer.initialize(this);
        Fresco.initialize(this);
    }
}
