package com.tools.kf.request;

/**
 * Created by djh on 2016/1/17.
 */
public interface ResultCallBack {

    void onDownLoadMessage(int progress);

    void onSuccessMessage(String path, String filename);

    void onFailureMessage(String msg);

    void onCanselMessage(String msg);
}