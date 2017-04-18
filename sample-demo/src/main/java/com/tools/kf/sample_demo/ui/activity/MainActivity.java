package com.tools.kf.sample_demo.ui.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.sample_demo.ui.fragment.HomeMainFragment;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

import java.lang.reflect.Field;

import cn.waps.AppConnect;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    static String[] TITLES = {"主页", "分类", "设置", "关于我们"};

    static final String CONTENT_TAG_HOME = "content_home";
    static final String CONTENT_TAG_FL = "content_feilei";
    static final String CONTENT_TAG_SETTING = "content_setting";
    static final String CONTENT_TAG_ABOUTUS = "content_aboutus";
    static final String CONTENTS[] = {
            CONTENT_TAG_HOME,
            CONTENT_TAG_SETTING,
            CONTENT_TAG_FL,
            CONTENT_TAG_ABOUTUS
    };

    @ViewInject(R.id.id_drawerlayout)
    private DrawerLayout mDrawerLayout;

    @ViewInject(R.id.id_navigationview)
    private NavigationView mNavigationView;

    @ViewInject(R.id.main_toobar)
    private LinearLayout main_toobar;


    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String mCurrentContentTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initad();
        initTopMargin();
        initAppBarSetting();
        initNavigationViewSetting();

        if (savedInstanceState == null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.base_content, HomeMainFragment.newInstance(new String[]{"动漫", "美女", "明星", "壁纸", "风景", "摄影"}), CONTENT_TAG_HOME)
                    .commit();
            setTitle(TITLES[0]);
            mCurrentContentTag = CONTENT_TAG_HOME;
        }
    }


    private void initTopMargin() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Class c = null;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                int statusBarHeight = getResources().getDimensionPixelSize(x);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, statusBarHeight, 0, 0);
                main_toobar.setLayoutParams(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void initAppBarSetting() {

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        readyGo(AboutUsActivity.class);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


    }

    public void initNavigationViewSetting() {
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        //给NavigationView填充顶部区域，也可在xml中使用app:headerLayout="@layout/header_nav"来设置
        mNavigationView.inflateHeaderView(R.layout.header_nav);
        //给NavigationView填充Menu菜单，也可在xml中使用app:menu="@menu/menu_nav"来设置
        mNavigationView.inflateMenu(R.menu.menu_nav);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_home:
                        ft.replace(R.id.base_content, HomeMainFragment.newInstance(new String[]{"风景", "摄影", "汽车", "建筑", "星空"}), CONTENT_TAG_HOME)
                                .commit();
                        mToolbar.setTitle(TITLES[0]);
                        mCurrentContentTag = CONTENT_TAG_HOME;
                        break;
                    case R.id.nav_menu_categories:
                        ft.replace(R.id.base_content, HomeMainFragment.newInstance(new String[]{"科技", "军事", "经济", "人文", "教育", "天文", "航天"}), CONTENT_TAG_HOME)
                                .commit();
                        mToolbar.setTitle(TITLES[1]);
                        mCurrentContentTag = CONTENT_TAG_FL;
                        break;
                    case R.id.nav_menu_setting:
                        readyGo(SettingActivity.class);
                        break;
                    case R.id.nav_menu_aboutus:
                        readyGo(AboutUsActivity.class);
                        break;
                }
                // Menu item点击后选中，并关闭Drawerlayout
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });

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
        return false;
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置toobar右边布局
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);


        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        if (mEdit != null) {
            mEdit.setHintTextColor(Color.WHITE);
            mEdit.setTextColor(Color.WHITE);
            mEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mEdit.setHint("请输入搜索内容");
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bu = new Bundle();
                bu.putString("content", query);
                readyGo(SearchActivity.class, bu);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

}





