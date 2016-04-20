package com.tools.kf.sample_demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.FrameLayout;

import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.http.callback.ImageListCallback;
import com.tools.kf.sample_demo.http.model.ImagesListEntity;
import com.tools.kf.sample_demo.http.model.ResponseImagesListEntity;
import com.tools.kf.sample_demo.ui.adapter.ImageListAdapter;
import com.tools.kf.sample_demo.ui.base.BaseFragment;
import com.tools.kf.sample_demo.utils.EmptyViewUtils;
import com.tools.kf.sample_demo.utils.UriHelper;
import com.tools.kf.view.anotation.ViewInject;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import cn.finalteam.loadingviewfinal.SwipeRefreshLayoutFinal;
import okhttp3.Call;


/**
 * 图片测试碎片
 * Created by LikeGo on 2016/4/6.
 */
public class ImageListFragment extends BaseFragment {
    @ViewInject(R.id.refresh_layout)
    private SwipeRefreshLayoutFinal mRefreshLayout;
    @ViewInject(R.id.rv_imagelist)
    private RecyclerViewFinal recyclerViewFinal;
    @ViewInject(R.id.fl_empty_view)
    private FrameLayout mFlEmptyView;
    private ArrayList<ImagesListEntity> mImagelist;
    private ImageListAdapter mImageListAdapter;
    private int mPage = 0;


    private String flag = "";


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_imagelist;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mImagelist = new ArrayList<>();
        mImageListAdapter = new ImageListAdapter(getContext(), mImagelist);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);

        recyclerViewFinal.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewFinal.setAdapter(mImageListAdapter);
        recyclerViewFinal.setEmptyView(mFlEmptyView);

        flag = (String) getArguments().get("flag");

        setSwipeRefreshInfo(flag);
    }

    private void setSwipeRefreshInfo(final String flag) {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(flag, 1);
            }
        });
        recyclerViewFinal.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestData(flag, mPage);
            }
        });
        mRefreshLayout.autoRefresh();

    }

    private void requestData(String name, final int page) {
        OkHttpUtils.get().url(UriHelper.getInstance().getImagesListUrl(name, page)).build().execute(new ImageListCallback() {
            @Override
            public void onError(Call call, Exception e) {

                if (page == 1) {
                    mRefreshLayout.onRefreshComplete();
                } else {
                    recyclerViewFinal.onLoadMoreComplete();
                }

                if (mImagelist.size() == 0) {
                    EmptyViewUtils.showNetErrorEmpty(mFlEmptyView);
                } else {
                    recyclerViewFinal.showFailUI();
                }


            }

            @Override
            public void onResponse(ResponseImagesListEntity response) {

                if (page == 1) {
                    mImagelist.clear();
                }
                mPage = page + 1;

                mImagelist.addAll(response.getImgs());
                if (UriHelper.getInstance().calculateTotalPages(response.getTotalNum()) < mPage)
                {
                    recyclerViewFinal.setHasLoadMore(false);
                } else {
                    recyclerViewFinal.setHasLoadMore(true);
                }

                if (page == 1) {
                    mRefreshLayout.onRefreshComplete();
                } else {
                    recyclerViewFinal.onLoadMoreComplete();
                }

                mImageListAdapter.notifyDataSetChanged();
            }
        });
    }


}
