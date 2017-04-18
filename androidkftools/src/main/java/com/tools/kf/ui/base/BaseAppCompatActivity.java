package com.tools.kf.ui.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tools.kf.request.netstatus.NetChangeObserver;
import com.tools.kf.request.netstatus.NetStateReceiver;
import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.view.ViewInjectorImpl;

/**
 * Created by djh on 2016/3/13.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    //private NetChangeObserver mNetChangeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseAppManager.getInstance().addActivity(this);
//        // base setup
//        Bundle extras = getIntent().getExtras();
//        if (null != extras) {
//            //getBundleExtras(extras);
//        }
        ViewInjectorImpl.getInsatnce().inject(this);
        initToolBar();
        initData();
//        mNetChangeObserver = new NetChangeObserver() {
//            @Override
//            public void onNetConnected(NetType type) {
//                super.onNetConnected(type);
//                onNetworkConnected(type);
//            }
//
//            @Override
//            public void onNetDisConnect() {
//                super.onNetDisConnect();
//                onNetworkDisConnected();
//            }
//        };
//        //注册网络变化监听器
//        NetStateReceiver.registerObserver(mNetChangeObserver);
//        NetStateReceiver.registerNetworkStateReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
//        NetStateReceiver.unRegisterNetworkStateReceiver(this);
    }

    /**
     * 获取传递的数据Bundle
     *
     * @param extras
     */
   // protected abstract void getBundleExtras(Bundle extras);


    /**
     * use SytemBarTintManager
     * 引入com.readystatesoftware.systembartint:systembartint:1.0.3
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        //判断版本是4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        //判断版本是4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected abstract void initData();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void initToolBar();

    /**
     * network connected
     */
    //protected abstract void onNetworkConnected(NetType type);

    /**
     * network disconnected
     */
    //protected abstract void onNetworkDisConnected();

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }

    /***
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    protected void readyGo(Intent intent) {
        startActivity(intent);
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
