package com.tools.kf.sample_demo.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.adapter.MyViewPagerAdapter;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.sample_demo.ui.callback.DrawerMenuCallBack;
import com.tools.kf.sample_demo.ui.fragment.ImageListFragment;
import com.tools.kf.sample_demo.ui.fragment.UiFragment;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LikeGo on 2016/4/14.
 */
@ContentView(R.layout.activity_my)
public class UiActivity extends BaseActivity implements DrawerMenuCallBack {

    //初始化各种控件，照着xml中的顺序写
    @ViewInject(R.id.id_drawerlayout)
    private DrawerLayout mDrawerLayout;
    @ViewInject(R.id.id_toolbar)
    private Toolbar mToolbar;
    @ViewInject(R.id.id_navigationview)
    private NavigationView mNavigationView;
    @ViewInject(R.id.id_tablayout)
    private TabLayout mTabLayout;
    @ViewInject(R.id.id_viewpager)
    private ViewPager mViewPager;


    // TabLayout中的tab标题
    private String[] mTitles = {"对话框", "按钮", "搜素栏", "加载框", "下拉控件"};

    public static String[] mTitles2 = {"动漫", "美女", "汽车", "明星", "摄影"};
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化mTitles、mFragments等ViewPager需要的数据
        //这里的数据都是模拟出来了，自己手动生成的，在项目中需要从网络获取数据
        initData2();

        configViews();
    }


    private void initData2() {
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles2.length; i++) {
            Bundle mBundle = new Bundle();
            mBundle.putString("flag", mTitles2[i]);
            ImageListFragment mFragment = new ImageListFragment();
            mFragment.setArguments(mBundle);
            mFragments.add(i, mFragment);
        }
    }


    private void configViews() {

        // 设置显示Toolbar
        setSupportActionBar(mToolbar);

        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        //给NavigationView填充顶部区域，也可在xml中使用app:headerLayout="@layout/header_nav"来设置
        mNavigationView.inflateHeaderView(R.layout.header_nav);
        //给NavigationView填充Menu菜单，也可在xml中使用app:menu="@menu/menu_nav"来设置
        mNavigationView.inflateMenu(R.menu.menu_nav);

        // 自己写的方法，设置NavigationView中menu的item被选中后要执行的操作
        onNavgationViewMenuItemSelected(mNavigationView);


        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles2, mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(5);
        // 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
       // mViewPager.addOnPageChangeListener(this);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);


    }

    /**
     * 设置NavigationView中menu的item被选中后要执行的操作
     *
     * @param mNav
     */
    private void onNavgationViewMenuItemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                String msgString = "";

                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_home:
                        getFragmentManager().beginTransaction();
                        break;
                    case R.id.nav_menu_categories:

                        break;
                    case R.id.nav_menu_feedback:

                        break;
                    case R.id.nav_menu_setting:
                        msgString = (String) menuItem.getTitle();
                        break;
                }

                // Menu item点击后选中，并关闭Drawerlayout
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                // android-support-design兼容包中新添加的一个类似Toast的控件。
                //SnackbarUtil.show(mViewPager, msgString, 0);

                return true;
            }
        });
    }

    @Override
    public void OnClickHome() {

    }

    @Override
    public void OnClickOrderBy() {

    }

    @Override
    public void OnClickSetting() {

    }

    @Override
    public void OnClickAboutUs() {

    }
}
