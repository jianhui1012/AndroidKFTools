package com.tools.kf.utils;

import android.util.Log;

import com.tools.kf.KFToolsCenter;

/**
 * 日志帮助类
 * Created by djh on 2016/1/16.
 */
public class LogHelper {

    private static final String TAG="LogHelper";

    private LogHelper() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /***
     * 调试级别输出日志
     *
     * @param tag--标识
     * @param msg--打印信息
     */
    public static void LogD(String tag, String msg) {
        if (KFToolsCenter.Isdebug())
            Log.d(tag, msg);
    }

    /***
     * 调试级别输出日志
     * @param msg--打印信息
     */
    public static void LogD(String msg) {
        if (KFToolsCenter.Isdebug())
            Log.d(TAG, msg);
    }

    public static void LogV(String tag, String msg) {
        if (KFToolsCenter.Isdebug())
            Log.v(tag, msg);
    }

    public static void LogE(String tag, String msg) {
        if (KFToolsCenter.Isdebug())
            Log.i(tag, msg);
    }

    public static void LogW(String tag, String msg) {
        if (KFToolsCenter.Isdebug())
            Log.w(tag, msg);
    }
}
