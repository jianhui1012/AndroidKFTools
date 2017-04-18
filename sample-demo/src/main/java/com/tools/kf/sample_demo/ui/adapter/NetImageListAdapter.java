package com.tools.kf.sample_demo.ui.adapter;

import android.content.Context;
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
import com.tools.kf.sample_demo.http.model.NetImageListEntity;
import com.tools.kf.sample_demo.http.model.ResponseNetImageEntity;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.sample_demo.utils.UriHelper;
import com.tools.kf.utils.LogHelper;
import com.tools.kf.view.ViewInjectorImpl;
import com.tools.kf.view.anotation.ViewInject;

import java.util.List;


/**
 * Created by LikeGo on 2016/4/16.
 */
public class NetImageListAdapter extends RecyclerView.Adapter<NetImageListAdapter.ListImageViewHolder> {

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private Context mContext;
    private List<NetImageListEntity> mData;
    private ViewGroup.LayoutParams layoutParams;
    private int sccrenWidth = 0;
    private float screenDensity = 0;


    public NetImageListAdapter(Context context, List<NetImageListEntity> data) {
        this.mContext = context;
        this.mData = data;
        sccrenWidth = ((BaseActivity) context).screenWidth / 2;
        screenDensity = ((BaseActivity) context).screenDensity;
    }

    @Override
    public ListImageViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_list_item, parent, false);
        return new ListImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListImageViewHolder holder, int position) {
        final NetImageListEntity imagesListEntity = mData.get(position);
        int viewwidth = 0;
        int viewheight = 0;
        int width = imagesListEntity.getThumb_width();
        int height = imagesListEntity.getThumb_height();

        final String imageUrl = imagesListEntity.getThumbImg();
        if (!UriHelper.isEmpty(imageUrl)) {
//            layoutParams = holder.mIcGameIcon.getLayoutParams();
//            layoutParams.height = (int) (height * screenDensity);
//            holder.mIcGameIcon.setLayoutParams(layoutParams);
//            holder.mIcGameIcon.setImageURI(Uri.parse(imageUrl));


            //宽远大于高
            if (width > height * 1.5) {
                if (width > sccrenWidth)
                    viewwidth = sccrenWidth - 10;
                else
                    viewwidth = width - 10;
                viewheight = (viewwidth * height) / width + 20;
            } else
                //高远大于宽
                if (height > width * 1.5) {
                    if (width > sccrenWidth)
                        viewwidth = sccrenWidth - 10;
                    else
                        viewwidth = width - 10;
                    viewheight = (viewwidth * height) / width + 20;
                } else {
                    if (width > sccrenWidth)
                        viewwidth = sccrenWidth - 10;
                    else
                        viewwidth = width - 10;
                    viewheight = (viewwidth * height) / width + 20;
                }
            try {
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                        .setResizeOptions(new ResizeOptions(viewwidth, viewheight))
                        .build();
                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.mIcGameIcon.getController())
                        .setImageRequest(request)
                        .build();
                holder.mIcGameIcon.setController(controller);
                layoutParams = holder.mIcGameIcon.getLayoutParams();
                layoutParams.height = viewheight;
                holder.mIcGameIcon.setLayoutParams(layoutParams);
            } catch (Exception ex) {
                LogHelper.LogD(ex.toString());
            }

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
        void onItemClick(View view, NetImageListEntity data);

        void onItemLongClick(View view, NetImageListEntity data);
    }
}
