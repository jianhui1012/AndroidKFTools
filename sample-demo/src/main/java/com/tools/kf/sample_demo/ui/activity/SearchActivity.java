package com.tools.kf.sample_demo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.sample_demo.ui.fragment.ImageListFragment;
import com.tools.kf.view.anotation.ContentView;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
@ContentView(R.layout.activity_back_base)
public class SearchActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private String content;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initad();
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            ImageListFragment mFragment = ImageListFragment.newInstance();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("flag", content);
            mFragment.setArguments(bundle);
            ft.replace(R.id.base_back_content, mFragment)
                    .commit();

        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        content = getIntent().getStringExtra("content") == null ? "输入内容为空" : getIntent().getStringExtra("content");
        setTitle(content);
    }

    @Override
    protected void onNetworkConnected(NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {
        super.onNetworkDisConnected();
    }
}
