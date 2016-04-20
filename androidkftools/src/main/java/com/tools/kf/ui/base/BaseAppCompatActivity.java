package com.tools.kf.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tools.kf.view.ViewInjectorImpl;

/**
 * Created by djh on 2016/3/13.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectorImpl.getInsatnce().inject(this);
    }

    /**
     * network connected
     */
    //protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * network disconnected
     */
    //protected abstract void onNetworkDisConnected();

    /***
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /***
     * 带Bundle startActivity
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != intent) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /***
     * startActivity 杀掉当前activity
     *
     * @param clazz
     */
    protected void readyGoThenEnd(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /***
     * startActivity 杀掉当前activity
     *
     * @param clazz
     */
    protected void readyGoThenEnd(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != intent) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }
}
