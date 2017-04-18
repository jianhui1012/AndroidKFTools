package com.tools.kf.gisandroidmap;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/4/13.
 */
public class WeekWeatherAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, Object>> mData = new ArrayList<>();

    public WeekWeatherAdapter(Context content, List<Map<String, Object>> data) {
        this.mContext = content;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (getCount() > 0)
            return mData.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {// 如果是第一次显示该页面(要记得保存到viewholder中供下次直接从缓存中调用)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.week_list_item, null);
            holder = new ViewHolder(convertView);
            // 以下为保存这一屏的内容，供下次回到这一屏的时候直接refresh，而不用重读布局文件
            convertView.setTag(holder);

        } else {// 如果之前已经显示过该页面，则用viewholder中的缓存直接刷屏
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, Object> hashMap = mData.get(position);
        String heads=(String) hashMap.get("head");
        holder.head.setImageURI(Uri.parse(heads));
        holder.name.setText((String) hashMap.get("name"));
        holder.desc.setText((String) hashMap.get("desc"));
        return convertView;
    }

    public final class ViewHolder {
        public SimpleDraweeView head;
        public TextView name;
        public TextView desc;

        public ViewHolder(View convertView) {
            this.head = (SimpleDraweeView) convertView.findViewById(R.id.head);
            this.name = (TextView) convertView.findViewById(R.id.name);
            this.desc = (TextView) convertView.findViewById(R.id.desc);
        }
    }
}
