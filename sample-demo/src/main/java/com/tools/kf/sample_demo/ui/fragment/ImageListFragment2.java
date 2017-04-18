package com.tools.kf.sample_demo.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.http.callback.ImageListCallback;
import com.tools.kf.sample_demo.http.callback.NetImageListCallback;
import com.tools.kf.sample_demo.http.model.ImagesListEntity;
import com.tools.kf.sample_demo.http.model.NetImageListEntity;
import com.tools.kf.sample_demo.http.model.ResponseImagesListEntity;
import com.tools.kf.sample_demo.http.model.ResponseNetImageEntity;
import com.tools.kf.sample_demo.ui.adapter.ImagesAdapter;
import com.tools.kf.sample_demo.ui.base.BaseFragment;
import com.tools.kf.sample_demo.utils.UriHelper;
import com.tools.kf.view.anotation.ViewInject;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;


/**
 * 图片主页
 * Created by LikeGo on 2016/4/6.
 */
public class ImageListFragment2 extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @ViewInject(R.id.recyclerView)
    private EasyRecyclerView recyclerView;

    @ViewInject(R.id.fl_empty_view)
    private FrameLayout mFlEmptyView;

    private ArrayList<NetImageListEntity> mImagelist;


    private int mPage = 0;

    private String flag = "";
    private ImagesAdapter adapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImagelist = new ArrayList<>();
        recyclerView.setLayoutManager(chooseLayoutManager(2));
        recyclerView.setAdapterWithProgress(adapter = new ImagesAdapter(getActivity()));
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemClick(int position) {
                adapter.remove(position);
                return true;
            }
        });
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });


        recyclerView.setRefreshListener(this);
        flag = (String) getArguments().get("flag");
        onRefresh();

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_imagelist2;
    }


    public void execTopBtn() {
        recyclerView.setRefreshing(false);
        recyclerView.scrollToPosition(3);
        recyclerView.showEmpty();
    }

    private void requestData(String name, final int page) {
        OkHttpUtils.get().url(UriHelper.getInstance().getUrl(name, page)).build().execute(new NetImageListCallback() {
            @Override
            public void onError(Call call, Exception e) {
                recyclerView.showError();
            }

            @Override
            public void onResponse(ResponseNetImageEntity response) {

                if (page == 1) {
                    mImagelist.clear();
                }
                mPage = page + 1;
                adapter.addAll(response.getItems());
            }
        });
    }


    @Override
    public void onLoadMore() {
        requestData(flag, mPage);
    }

    @Override
    public void onRefresh() {
        requestData(flag, 1);
    }
}
