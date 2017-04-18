package com.tools.kf.sample_demo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.sample_demo.ui.fragment.SettingFragment;
import com.tools.kf.view.anotation.ContentView;

/**
 * Created by Administrator on 2016/4/22 0022.
 */
@ContentView(R.layout.activity_back_base)
public class SettingActivity extends BaseActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.base_back_content, SettingFragment.newInstance())
                    .commit();

        }


    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onNetworkConnected(NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {
        super.onNetworkDisConnected();
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected boolean isShowBugTags() {
        return true;
    }

    @Override
    protected Drawable getSystemBarDrawable() {
        return null;
    }
}
