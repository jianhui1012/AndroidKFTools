package com.tools.kf.sample_demo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.adapter.MyAdapter;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.utils.DividerItemDecoration;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity   {

    @ViewInject(R.id.my_listview)
    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;

    @ViewInject(R.id.id_toolbar)
    private Toolbar mToolbar;

   private static  String[] datalist = {"注解模块", "图片模块", "网络模块", "日志模块", "UI模块" };
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();

        // 设置显示Toolbar
        setSupportActionBar(mToolbar);

    }

    private void initview() {
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        // mRecyclerView.setHasFixedSize(true);
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        myAdapter = new MyAdapter(datalist);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnRecyclerViewItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                switch ((int) data)
                {
                    case 0:

                        break;
                    case 1:
                        readyGo(ImageActivity.class);
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        readyGo(UiActivity.class);
                        break;

                }
                Toast.makeText(getApplicationContext(), (int) data+"", Toast.LENGTH_LONG).show();
            }
        });


    }

 
}




