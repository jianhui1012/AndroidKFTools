package com.tools.kf.sample_demo.http.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/25 0025.
 */
public class NetImageListEntity implements Serializable {

    private String thumbUrl;
    private String pic_url_noredirect;
    private int thumb_width;
    private int thumb_height;
    private int width;
    private int height;
    private String title;

    public String getThumbImg() {
        return thumbUrl;
    }


    public String getLargeImg() {
        return pic_url_noredirect;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public int getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPic_url_noredirect(String pic_url_noredirect) {
        this.pic_url_noredirect = pic_url_noredirect;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
