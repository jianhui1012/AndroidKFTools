package com.tools.kf;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import java.lang.reflect.Method;

/**
 * Created by djh on 2016/1/21.
 */
public final class KFToolsCenter {

    private KFToolsCenter() {

    }

    public static boolean Isdebug() {
        return Ext.isdebug;
    }

    public static Application app() {
        if (Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("please invoke x.Ext.init(app) on Application#onCreate()"
                        + " and register your Application in manifest.");
            }
        }
        return Ext.app;
    }

    public static class Ext {

        //当前应用
        private static Application app;
        //调试标识符
        private static boolean isdebug = true;

        public static void setIsdebug(boolean debugvalue) {
            Ext.isdebug = debugvalue;
        }

        public static void init(Application application) {
            if (Ext.app == null) {
                Ext.app = application;
                //错误上报初始化
                if (KFToolsCenter.Isdebug()) {
                    //网络拦截初始化结合google浏览器
                    Stetho.initializeWithDefaults(application);
                }
            }


        }

    }

    private static class MockApplication extends Application {

        public MockApplication(Context basecontext) {
            this.attachBaseContext(basecontext);
        }

    }

}


