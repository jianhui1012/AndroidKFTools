package com.tools.kf.sample_demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tools.kf.image.PcassoTools;
import com.tools.kf.sample_demo.R;
import com.tools.kf.view.ViewInjectorImpl;
import com.tools.kf.view.anotation.ViewInject;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public String[] datas = null;

    private Context context;

    public MyAdapter(String[] datas) {
        this.datas = datas;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.sub_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(datas[position]);
        PcassoTools.loadUriToImageView(context, R.drawable.ic_launcher, viewHolder.image_icon);
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.length;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onItemClick(v, v.getTag());
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.title_name)
        public TextView mTextView;
        @ViewInject(R.id.image_icon)
        public ImageView image_icon;

        public ViewHolder(View view) {
            super(view);
            ViewInjectorImpl.getInsatnce().inject(this, view);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

}