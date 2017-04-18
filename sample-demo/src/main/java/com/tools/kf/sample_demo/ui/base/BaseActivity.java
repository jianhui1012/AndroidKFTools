package com.tools.kf.sample_demo.ui.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.tools.kf.sample_demo.MyApplication;
import com.tools.kf.sample_demo.R;
import com.tools.kf.ui.base.BaseAppCompatActivity;
import com.tools.kf.utils.LogHelper;
import com.tools.kf.view.ViewInjectorImpl;
import com.zhy.http.okhttp.OkHttpUtils;

import cn.waps.AppConnect;

/**
 * Created by djh on 2016/3/13.
 */
public abstract class BaseActivity extends BaseAppCompatActivity {

    protected Toolbar mToolbar;
    private DisplayMetrics dm;
    public int screenWidth = 0;
    public int screenHeight = 0;
    public float screenDensity = 1.0f;
    protected FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();
        if (isApplyKitKatTranslucency()) {
            if (getSystemBarDrawable() == null) {
                //setSystemBarTintDrawable(getResources().getDrawable(R.color.colorPrimary));
            } else {
                // setSystemBarTintDrawable(getSystemBarDrawable());
            }
        }
        if (dm == null) {
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        }

        screenDensity = dm.density;
        //设置屏幕的宽高
        screenWidth = dm.widthPixels;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            screenHeight = dm.heightPixels - this.getResources().getDimensionPixelSize(resourceId);
        } else {
            screenHeight = dm.heightPixels;
        }

        ViewInjectorImpl.getInsatnce().inject(this);
    }


    public void initad() {
        if (AppConnect.getInstance(this).hasPopAd(this)) {
            AppConnect.getInstance(this).showPopAd(this);
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) this.findViewById(R.id.id_toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            //设置返回按钮
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected MyApplication getBaseApplication() {
        return (MyApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消队列请求
        OkHttpUtils.getInstance().cancelTag(this);
        if (AppConnect.getInstance(this) != null) {
            AppConnect.getInstance(this).close();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        LogHelper.LogD(this.getClass().getName() + "weak activity has been recyled");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onNetworkDisConnected() {
        Snackbar.make(mToolbar, "网络已断开", Snackbar.LENGTH_SHORT).show();
    }

    protected abstract boolean isApplyKitKatTranslucency();

    protected abstract boolean isShowBugTags();


    protected abstract Drawable getSystemBarDrawable();

}
