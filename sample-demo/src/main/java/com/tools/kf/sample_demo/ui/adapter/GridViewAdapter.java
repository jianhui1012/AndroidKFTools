package com.tools.kf.sample_demo.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.model.DLPic;

import java.util.List;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
public class GridViewAdapter extends BaseAdapter {

    private OnItemClickListener mOnItemClickListener;
    private final Context mContext;
    private final List<DLPic> mData;

    public GridViewAdapter(Context context, List<DLPic> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DLPic dlPic = mData.get(position);
        final Object tag;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        tag = convertView.getTag();
        viewHolder.simpleDraweeView.setImageURI(Uri.parse("file://" + dlPic.getFilepath()));

        viewHolder.gridview_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(dlPic, tag);
            }
        });

        viewHolder.gridview_re.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onLongClick(dlPic, tag);
                return false;
            }
        });
        return convertView;
    }


    public interface OnItemClickListener {

        void onItemClick(DLPic dlPic, Object viewholder);

        void onLongClick(DLPic dlPic, Object viewholder);
    }

    public class ViewHolder {
        private SimpleDraweeView simpleDraweeView;

        public ImageView chooseDelete;

        private RelativeLayout gridview_re;

        public ViewHolder(View convertView) {
            simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.large_draweeview);
            chooseDelete = (ImageView) convertView.findViewById(R.id.chooseDelete);
            gridview_re = (RelativeLayout) convertView.findViewById(R.id.gridview_re);
        }
    }
}
