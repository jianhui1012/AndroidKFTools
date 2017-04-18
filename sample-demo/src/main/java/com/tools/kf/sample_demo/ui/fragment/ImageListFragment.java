package com.tools.kf.sample_demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.http.callback.NetImageListCallback;
import com.tools.kf.sample_demo.http.model.NetImageListEntity;
import com.tools.kf.sample_demo.http.model.ResponseNetImageEntity;
import com.tools.kf.sample_demo.ui.activity.LargeImageActivity;
import com.tools.kf.sample_demo.ui.adapter.ImageListAdapter;
import com.tools.kf.sample_demo.ui.adapter.NetImageListAdapter;
import com.tools.kf.sample_demo.ui.base.BaseFragment;
import com.tools.kf.sample_demo.utils.AndroidShare;
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
 * 图片主页
 * Created by LikeGo on 2016/4/6.
 */
public class ImageListFragment extends BaseFragment {

    @ViewInject(R.id.refresh_layout)
    private SwipeRefreshLayoutFinal mRefreshLayout;
    @ViewInject(R.id.rv_imagelist)
    private RecyclerViewFinal recyclerViewFinal;
    @ViewInject(R.id.fl_empty_view)
    private FrameLayout mFlEmptyView;


    private ArrayList<NetImageListEntity> mImagelist;

    private NetImageListAdapter mImageListAdapter;

    private int mPage = 0;

    private String flag = "";

    public static ImageListFragment newInstance() {
        return new ImageListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mImagelist = new ArrayList<>();
        mImageListAdapter = new NetImageListAdapter(getContext(), mImagelist);
        mImageListAdapter.setOnRecyclerViewItemClickListener(new NetImageListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, NetImageListEntity data) {
                Bundle bu = new Bundle();
                bu.putString("pic_url", data.getLargeImg());
                bu.putInt("pic_width", data.getWidth());
                bu.putInt("pic_height", data.getHeight());
                bu.putString("pic_title", data.getTitle());
                readyGo(LargeImageActivity.class, bu);
            }

            @Override
            public void onItemLongClick(View view, NetImageListEntity data) {
                AndroidShare as = new AndroidShare(
                        getActivity(),
                        "来自阅图分享--" + data.getTitle(),
                        data.getLargeImg());
                as.show();

            }
        });

        recyclerViewFinal.setLayoutManager(chooseLayoutManager(2));
        recyclerViewFinal.setAdapter(mImageListAdapter);
        recyclerViewFinal.setEmptyView(mFlEmptyView);
        flag = (String) getArguments().get("flag");

        setSwipeRefreshInfo(flag);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_imagelist;
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

    public void execTopBtn() {
        mRefreshLayout.setRefreshing(false);
        recyclerViewFinal.scrollToPosition(3);
    }

    private void requestData(String name, final int page) {
        OkHttpUtils.get().url(UriHelper.getInstance().getUrl(name, page)).tag(getActivity()).build().execute(new NetImageListCallback() {
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
            public void onResponse(ResponseNetImageEntity response) {

                if (page == 1) {
                    mImagelist.clear();
                    mRefreshLayout.onRefreshComplete();
                } else {
                    recyclerViewFinal.onLoadMoreComplete();
                }
                if (response.getItemsOnPage() > 0) {
                    mImagelist.addAll(response.getItems());
                    recyclerViewFinal.setHasLoadMore(true);
                } else if (response.getItemsOnPage() == 0) {
                    EmptyViewUtils.showNoDataEmpty(mFlEmptyView);
                } else {
                    recyclerViewFinal.setHasLoadMore(false);
                }
                mPage = page + 1;

                mImageListAdapter.notifyDataSetChanged();
            }
        });
    }


}
