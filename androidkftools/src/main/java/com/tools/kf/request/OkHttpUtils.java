package com.tools.kf.request;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.tools.kf.KFToolsCenter;
import com.tools.kf.utils.FileHelper;
import com.tools.kf.utils.LogHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * okhttp工具类
 * Created by djh on 2016/1/16.
 */
public class OkHttpUtils {

    private static OkHttpUtils ourInstance = null;

    private OkHttpClient okHttpClient = null;

    private static final String TAG = "OkHttpUtils";

    private ResultCallBack resultCallBack;

    private boolean cansel = false;

    private Handler mDelivery;


    public static OkHttpUtils getInstance() {
        if (ourInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (ourInstance == null) {
                    ourInstance = new OkHttpUtils();
                }
            }
        }
        return ourInstance;
    }

    private OkHttpUtils() {
        if (okHttpClient == null) {
            //创建okhttp客户端与handler对象
            okHttpClient = new OkHttpClient();
            mDelivery = new Handler(Looper.getMainLooper());
            if (KFToolsCenter.Isdebug()) {
                //设置拦截器--配合谷歌浏览器调试使用网络访问情况
                okHttpClient.networkInterceptors().add(new StethoInterceptor());
            }
        }
    }

    /***
     * 同步get请求
     *
     * @param context
     * @param urladdress
     * @return
     * @throws IOException
     */
    public Response getRequestMethod(Context context, String urladdress) throws IOException {
        Request request = new Request.Builder().url(urladdress).tag(context)
                .build();
        return okHttpClient.newCall(request).execute();
    }


    /***
     * 异步get请求
     *
     * @param context
     * @param urladdress
     * @param reponsecallback
     */
    public void getRequestMethod(Context context, String urladdress,
                                 Callback reponsecallback) {
        Request request = new Request.Builder().url(urladdress).tag(context)
                .build();
        okHttpClient.newCall(request).enqueue(reponsecallback);
    }

    /***
     * 异步post请求
     *
     * @param context
     * @param urladdress
     * @param reponsecallback
     */
    public void postRequestMethod(Context context, String urladdress, RequestBody requestBody,
                                  Callback reponsecallback) {
        Request request = new Request.Builder().url(urladdress).post(requestBody).tag(context)
                .build();
        okHttpClient.newCall(request).enqueue(reponsecallback);
    }

    /***
     * 取消当前网络请求
     *
     * @param context
     */
    public void canselCurRequestByTag(Context context) {
        okHttpClient.cancel(context);
    }


    /***
     * 异步下载文件信息
     *
     * @param context
     * @param urladdress
     * @param savepath
     */
    public void downLoadFileByAsyn(Context context, final String urladdress,
                                   final String savepath, final ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;

        final Request request = new Request.Builder().url(urladdress).tag(context)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogHelper.LogD(TAG, "下载地址:" + urladdress + ",IO异常:" + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {

                InputStream inputStream = null;

                long contentLength = response.body().contentLength();

                byte[] buff = new byte[2048];

                FileOutputStream fileOutputStream = null;

                String filename=FileHelper.getFileName(urladdress);

                String filepath=FileHelper.Get_Path(savepath);
                try {

                    inputStream = response.body().byteStream();


                    File file = new File(filepath,filename);
                    if (file.exists()) {
                        file.delete();
                    }

                    fileOutputStream = new FileOutputStream(file);

                    int count, num = 0;

                    while (!cansel) {
                        if ((count = inputStream.read(buff)) != -1) {

                            num += count;

                            int progress=(int) (((float) num / contentLength)* 100);

                            sendDownLoadMessageCallback(progress,resultCallBack);
                            //resultCallBack.onDownLoadMessage((int) ((float) num / contentLength) * 100);

                            fileOutputStream.write(buff, 0, count);
                        } else {
                            //如果下载文件成功，第一个参数为文件的绝对路径
                            sendSuccessMessageCallback(filepath,filename,resultCallBack);
                           // resultCallBack.onSuccessMessage("下载成功", "");
                            break;
                        }
                    }
                    if (cansel) {
                        //resultCallBack.onCanselMessage("取消成功");
                        sendCanselMessageCallback("取消成功",resultCallBack);
                    }

                    fileOutputStream.flush();

                } catch (IOException e) {
                    //resultCallBack.onFailureMessage(e.toString());
                    sendFailureMessageCallback(e.toString(), resultCallBack);

                } finally {

                    if (inputStream != null)
                        inputStream.close();

                    if (fileOutputStream != null)
                        fileOutputStream.close();

                }


            }
        });
    }

    /**
     * 设置取消下载标识符
     *
     * @param iscansel
     */
    public void setCanselTag(boolean iscansel) {
        this.cansel = iscansel;
    }

    private void sendDownLoadMessageCallback(final int progress, final ResultCallBack callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onDownLoadMessage(progress);

            }
        });
    }

    private void sendSuccessMessageCallback(final String path, final String filename, final ResultCallBack callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onSuccessMessage(path, filename);

            }
        });
    }

    private void sendFailureMessageCallback(final String msg ,final ResultCallBack callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onFailureMessage(msg);

            }
        });
    }

    private void sendCanselMessageCallback(final String msg ,final ResultCallBack callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onCanselMessage(msg);

            }
        });
    }

    public void sendOpenUpdateDialogCallback(final String msg ,final String description ,final UpdateMangerUtils.UpdateCallBack callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.openUpdateDialog(msg,description);

            }
        });
    }


}
