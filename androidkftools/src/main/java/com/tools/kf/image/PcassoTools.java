package com.tools.kf.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

/**
 * Picasso开源库网络请求采用okhhtp("无"本地缓存--通过request header请求控制)
 * 网络加载二级缓存(硬盘与内存)
 * 采用lru算法(当前缓存区突破临界值近期不用的图片优先删除)
 * 图片压缩
 * Created by LikeGo on 2016/4/5.
 */
public class PcassoTools {

    /***
     * 加载资源到ImageView
     *
     * @param context--当前上下文
     * @param path--远程图片资源路径
     * @param imageView
     */
    public static void loadUriToImageView(Context context, String path, ImageView imageView) {
        Picasso.with(context).load(path).into(imageView);
    }

    public static void loadUriToImageView(Context context, int res, ImageView imageView) {
        Picasso.with(context).load(res).into(imageView);
    }

    public static void loadUriToImageView(Context context, Uri uri, ImageView imageView) {
        Picasso.with(context).load(uri).into(imageView);
    }

    public static void loadUriToImageView(Context context, String path, ImageView imageView, int placeholder, int placeholder_error) {
        Picasso.with(context).load(path).placeholder(placeholder)
                .error(placeholder_error).into(imageView);

    }

}
