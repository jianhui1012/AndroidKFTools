package com.tools.kf.sample_demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.adapter.MyAdapter;
import com.tools.kf.sample_demo.ui.base.BaseFragment;
import com.tools.kf.view.anotation.ViewInject;

/**
 * Created by Administrator on 2016/4/16 0016.
 */
public class UiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.id_swiperefreshlayout)
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewInject(R.id.id_recyclerview)
    private RecyclerView mRecyclerView;

    private int flag = 0;
    private MyAdapter myAdapter;
    String[] datalist = {"注解模块", "图片模块", "网络模块", "日志模块", "UI模块"};
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_ui;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flag = (int) getArguments().get("flag");
        configRecyclerView();

        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void configRecyclerView() {

        myAdapter = new MyAdapter(datalist);
        mRecyclerView.setAdapter(myAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onRefresh() {

        // 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);

            }
        }, 1000);
    }

}
