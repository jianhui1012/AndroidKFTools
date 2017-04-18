package com.tools.kf.sample_demo.ui.adapter;

import android.net.Uri;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.http.model.ImagesListEntity;
import com.tools.kf.sample_demo.http.model.NetImageListEntity;
import com.tools.kf.sample_demo.utils.UriHelper;


/**
 * Created by Mr.Jude on 2015/2/22.
 */
public class ImageListViewHolder extends BaseViewHolder<NetImageListEntity> {

    private SimpleDraweeView mIcGameIcon;
    private ViewGroup.LayoutParams layoutParams;

    public ImageListViewHolder(ViewGroup parent) {
        super(parent, R.layout.adapter_list_item);
        mIcGameIcon = $(R.id.ic_game_icon);
    }

    @Override
    public void setData(final NetImageListEntity imagesListEntity) {
        int width = imagesListEntity.getThumb_width();
        int height = imagesListEntity.getThumb_height();

        final String imageUrl = imagesListEntity.getThumbImg();
        if (!UriHelper.isEmpty(imageUrl)) {
            layoutParams = mIcGameIcon.getLayoutParams();
            //layoutParams.height = (int) (height * 3.0);
            mIcGameIcon.setLayoutParams(layoutParams);
            mIcGameIcon.setImageURI(Uri.parse(imageUrl));
        }


    }
}
