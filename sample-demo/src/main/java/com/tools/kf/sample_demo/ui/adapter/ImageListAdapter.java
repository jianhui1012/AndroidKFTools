package com.tools.kf.sample_demo.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.http.model.ImagesListEntity;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.sample_demo.utils.UriHelper;
import com.tools.kf.view.ViewInjectorImpl;
import com.tools.kf.view.anotation.ViewInject;

import java.util.List;


/**
 * Created by LikeGo on 2016/4/16.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ListImageViewHolder> {

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private Context mContext;
    private List<ImagesListEntity> mData;
    private ViewGroup.LayoutParams layoutParams;
    float sccrenWidth = 0;
    private Bitmap newbm;

    public ImageListAdapter(Context context, List<ImagesListEntity> data) {
        this.mContext = context;
        this.mData = data;
        sccrenWidth = ((BaseActivity) context).screenWidth / 2;
    }

    @Override
    public ListImageViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_list_item, parent, false);
        return new ListImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListImageViewHolder holder, int position) {
        final ImagesListEntity imagesListEntity = mData.get(position);
        int viewwidth = 0;
        int viewheight = 0;
        int width = imagesListEntity.getThumbnailWidth();
        int height = imagesListEntity.getThumbnailHeight();

        final String imageUrl = imagesListEntity.getThumbnailUrl();
        if (!UriHelper.isEmpty(imageUrl)) {
            if (width > sccrenWidth) {
                viewwidth = (int) sccrenWidth;
            }
            if (height > width * 2) {
                height = width;
            }
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(holder.mIcGameIcon.getController())
                    .setImageRequest(request)
                    .build();
            holder.mIcGameIcon.setController(controller);
        }


        holder.mIcGameIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClick(v, imagesListEntity);
            }
        });
        holder.mIcGameIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                onRecyclerViewItemClickListener.onItemLongClick(v, imagesListEntity);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    static class ListImageViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.ic_game_icon)
        SimpleDraweeView mIcGameIcon;

        public ListImageViewHolder(View itemView) {
            super(itemView);
            ViewInjectorImpl.getInsatnce().inject(this, itemView);

        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ImagesListEntity data);

        void onItemLongClick(View view, ImagesListEntity data);
    }
}
