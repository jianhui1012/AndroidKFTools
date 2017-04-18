package com.tools.kf.sample_demo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tools.kf.KFToolsCenter;

import cn.waps.AppConnect;

/**
 * Created by djh on 2016/3/13.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        KFToolsCenter.Ext.init(this);
        KFToolsCenter.Ext.setIsdebug(true);
        Fresco.initialize(this);

    }


}
