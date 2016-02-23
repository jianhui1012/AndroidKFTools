package com.tools.kf.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by djh on 2016/1/16.
 */
public class FileHelper {


    /***
     * 获取url下载地址的文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 返回SD卡上的绝对路径
     *
     * @param path
     *            相对路径
     * @return
     */
    public static String Get_Path(String path) {

        File file_path = new File(Environment.getExternalStorageDirectory()+ path);
        if (!file_path.exists()) {
            file_path.mkdir();
        }
        return file_path.getPath();
    }
}
