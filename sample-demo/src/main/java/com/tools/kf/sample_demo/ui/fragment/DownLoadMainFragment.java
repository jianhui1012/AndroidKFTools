package com.tools.kf.sample_demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tools.kf.sample_demo.ui.adapter.MyViewPagerAdapter;
import com.tools.kf.sample_demo.ui.base.BaseMainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/21 0021.
 */
public class DownLoadMainFragment extends BaseMainFragment {
    private static String[] titles = null;
    private List<Fragment> listcontent = new ArrayList<>();

    public static DownLoadMainFragment newInstance(String[] ts) {
        titles = ts;
        return new DownLoadMainFragment();
    }


    @Override
    public void setTopViewClick(final MyViewPagerAdapter mViewPagerAdapter, final ViewPager mViewPager) {

    }

    @Override
    public void setTopViewVisible() {
    }

    @Override
    public MyViewPagerAdapter setTitlesAndFragments() {

        for (String name : titles) {
            DownLoadFragment mFragment = new DownLoadFragment();
            Bundle bundle = new Bundle();
            bundle.putString("flag", name);
            mFragment.setArguments(bundle);
            listcontent.add(mFragment);
        }
        return new MyViewPagerAdapter(getChildFragmentManager(), titles, listcontent);
    }

    @Override
    public int setPageLimit() {
        return titles.length;
    }
}
