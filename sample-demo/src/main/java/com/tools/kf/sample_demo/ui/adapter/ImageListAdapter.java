package com.tools.kf.sample_demo.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tools.kf.image.PcassoTools;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.http.model.ImagesListEntity;
import com.tools.kf.sample_demo.utils.UriHelper;
import com.tools.kf.view.ViewInjectorImpl;
import com.tools.kf.view.anotation.ViewInject;
import com.tools.kf.widget.pla.PLAImageView;

import java.util.List;


/**
 * Created by LikeGo on 2016/4/16.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ListImageViewHolder> {


    private Context mContext;
    private List<ImagesListEntity> mData;

    public ImageListAdapter(Context context, List<ImagesListEntity> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ListImageViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_list_item, parent, false);
        return new ListImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListImageViewHolder holder, int position) {
        ImagesListEntity imagesListEntity = mData.get(position);

        int width = imagesListEntity.getThumbnailWidth();
        int height = imagesListEntity.getThumbnailHeight();

        final String imageUrl = imagesListEntity.getThumbnailUrl();
        if (!UriHelper.isEmpty(imageUrl)) {
            Picasso.with(mContext)
                    .load(imagesListEntity.getImageUrl()).resize(width, height).centerCrop()
                    .into(holder.mIcGameIcon);
        }
        holder.mIcGameIcon.setImageHeight(height);
        holder.mIcGameIcon.setImageWidth(width);
        holder.mIcGameIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, imageUrl, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ListImageViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.ic_game_icon)
        PLAImageView mIcGameIcon;

        public ListImageViewHolder(View itemView) {
            super(itemView);
            ViewInjectorImpl.getInsatnce().inject(this, itemView);

        }
    }
}
