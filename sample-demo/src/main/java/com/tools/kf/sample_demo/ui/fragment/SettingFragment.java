package com.tools.kf.sample_demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.activity.DownLoadActivity;
import com.tools.kf.sample_demo.ui.base.BaseFragment;
import com.tools.kf.view.anotation.Event;
import com.tools.kf.view.anotation.ViewInject;

/**
 * Created by Administrator on 2016/4/22 0022.
 */
public class SettingFragment extends BaseFragment {

    @ViewInject(R.id.update_re)
    private RelativeLayout update_re;
    @ViewInject(R.id.share_re)
    private RelativeLayout share_re;
    @ViewInject(R.id.download_re)
    private RelativeLayout download_re;
    @ViewInject(R.id.uptheme_re)
    private RelativeLayout uptheme_re;


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_aboutus;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Event(value = {R.id.update_re, R.id.share_re, R.id.download_re, R.id.uptheme_re},
            type = View.OnClickListener.class/*可选参数, 默认是View.OnClickListener.class*/)
    private void OnClickItem(View v) {
        switch (v.getId()) {
            case R.id.download_re:
                readyGo(DownLoadActivity.class);
                //Snackbar.make(update_re, "开发中...", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.uptheme_re:
                Snackbar.make(update_re, "开发中...", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.update_re:
                Snackbar.make(update_re, "版本已经最新", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.share_re:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "GO阅图GO.下载点击:https://www.hao123.com/");
                shareIntent.setType("text/plain");
                readyGo(Intent.createChooser(shareIntent, "分享到"));
                break;
        }

    }
}
