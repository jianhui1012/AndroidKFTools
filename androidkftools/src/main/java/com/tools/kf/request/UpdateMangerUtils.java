package com.tools.kf.request;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tools.kf.utils.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by djh on 2016/1/17.
 */
public class UpdateMangerUtils {

    private static final String TAG = "UpdateMangerUtils";

    private Context context;

    // 获取服务器版本号
    private String newversionname;

    // 获取服务器版本号(内部识别号)
    private int newversioncode;

    // 获取当前包版本号(呈现给用户)
    private String curversionname;

    // 获取当前包版本号(内部识别号)
    private int curversioncode;

    private String newdescription;

    private String downloadurl;

    // 服务器最新的版本
    private JSONObject jo;

    private UpdateCallBack updateCallBack;


    public UpdateMangerUtils(Context context, UpdateCallBack updateCallBack) {
        this.context = context;
        this.updateCallBack = updateCallBack;
        getVersionName();
    }


    public void CheckUpdateFile(final String updateapkurl) {


        OkHttpUtils.getInstance().getRequestMethod(context, updateapkurl, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String updatestr = response.body().string();
                try {
                    JSONArray array = new JSONArray(updatestr);
                    if (array.length() >= 0) {
                        try {
                            // 获取最新的的更新配置(json形式)
                            jo = array.getJSONObject(0);

                            // 获取最新的版本号
                            newversioncode = Integer.parseInt(jo
                                    .getString("verCode"));
                            if (newversioncode > curversioncode) {
                                LogHelper.LogD(TAG, "版本号不同 ,提示用户升级 ");
                                // 获取json数据中的更新地址
                                newdescription = jo.getString("description");
                                downloadurl = jo.getString("url");
                                OkHttpUtils.getInstance().sendOpenUpdateDialogCallback(downloadurl, newdescription, updateCallBack);
                            }
                        } catch (Exception e) {
                            LogHelper.LogD(TAG, "获取失败:" + e.toString());
                        }
                    }

                } catch (JSONException e) {
                    LogHelper.LogD(TAG, "获取版本信息失败:" + e.toString());
                }
            }

        });
    }

    /***
     * 下载文件到指定目录
     * @param fileml--文件目录
     */
    public void downloadPackage(String fileml) {

        OkHttpUtils.getInstance().downLoadFileByAsyn(context, downloadurl, fileml, updateCallBack);

    }


    /**
     * 获取当前安装apk的版本号
     *
     * @return 版本号
     * @throws NameNotFoundException
     */

    private void getVersionName() {

        try {
            // 获取packagemanager的实例
            PackageManager pm = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo pi;
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            newversionname = pi.versionName;
            newversioncode = pi.versionCode;
        } catch (NameNotFoundException e) {
            LogHelper.LogD(TAG, e.toString());
        }

    }

    /***
     *  安装apk
     * @param path-apk路径
     * @param filename-apk文件名
     */
    public void installApk(String path,String filename) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(new File(path, filename)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public interface UpdateCallBack extends ResultCallBack {

        void openUpdateDialog(String updateurl, String description);

    }
}
