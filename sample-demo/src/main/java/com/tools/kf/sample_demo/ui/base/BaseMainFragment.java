package com.tools.kf.sample_demo.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.adapter.MyViewPagerAdapter;
import com.tools.kf.sample_demo.ui.fragment.ImageListFragment;
import com.tools.kf.sample_demo.ui.fragment.ImageListFragment2;
import com.tools.kf.view.anotation.ViewInject;

/**
 * Created by Administrator on 2016/4/21 0021.
 */
public abstract class BaseMainFragment extends BaseFragment {

    @ViewInject(R.id.main_tablayout)
    private TabLayout mTabLayout;
    @ViewInject(R.id.main_viewpager)
    protected ViewPager mViewPager;
    @ViewInject(R.id.top)
    protected FloatingActionButton top;

    protected MyViewPagerAdapter mViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPagerAdapter = setTitlesAndFragments();
        // 初始化ViewPager的适配器，并设置给它
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(setPageLimit());
        // 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
        // mViewPager.addOnPageChangeListener(this);
        //mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
         mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);

        setTopViewVisible();
        setTopViewClick(mViewPagerAdapter,mViewPager);

    }

    public abstract void  setTopViewClick(final MyViewPagerAdapter mViewPagerAdapter,final ViewPager mViewPager);

    public abstract void  setTopViewVisible();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_viewpager_tabs;
    }

    public abstract MyViewPagerAdapter setTitlesAndFragments();

    public abstract int setPageLimit();


}
