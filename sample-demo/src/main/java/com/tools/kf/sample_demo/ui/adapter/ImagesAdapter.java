package com.tools.kf.sample_demo.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tools.kf.sample_demo.http.model.ImagesListEntity;
import com.tools.kf.sample_demo.http.model.NetImageListEntity;

/**
 * Created by Mr.Jude on 2015/7/18.
 */
public class ImagesAdapter extends RecyclerArrayAdapter<NetImageListEntity> {
    public ImagesAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageListViewHolder(parent);
    }
}
