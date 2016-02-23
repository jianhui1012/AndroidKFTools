package com.tools.kf.gisandroidmap;

import android.os.Bundle;
import android.view.View;

import com.tools.kf.anotation.ContentView;
import com.tools.kf.request.OkHttpUtils;
import com.tools.kf.request.UpdateMangerUtils;
import com.tools.kf.ui.BasicActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

@ContentView(R.layout.activity_main)
public class MainActivity extends BasicActivity {

    private OkHttpUtils okHttpUtils;

    private UpdateMangerUtils updateMangerUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGuesture(true);
        okHttpUtils = OkHttpUtils.getInstance();


        //        okHttpUtils.getRequestMethod(this, "http://www.hao123.com/", new Callback() {
        //            @Override
        //            public void onFailure(Request request, IOException e) {
        //
        //            }
        //
        //            @Override
        //            public void onResponse(Response response) throws IOException {
        //                Log.d("com.tools.kf.gisandroidmap.MainActivity.MainActivity", response.body().string());
        //            }
        //        });

          updateMangerUtils = new UpdateMangerUtils(this, updateCallBack);
          updateMangerUtils.CheckUpdateFile(AppConfig.updateapkurl);

    }


    public void turnPage(View v) {
        startActivity(new Bundle(), Main2Activity.class);
    }

    public UpdateMangerUtils.UpdateCallBack updateCallBack = new UpdateMangerUtils.UpdateCallBack() {

        private SweetAlertDialog progressdialog = null, tipdialog = null;

        @Override
        public void openUpdateDialog(String updateurl, String description) {

            if (tipdialog == null) {
                tipdialog = new SweetAlertDialog(MainActivity.this);
            }
            tipdialog.setTitleText("提示框")
                    .setContentText(description)
                    .setConfirmText("确认")
                    .setCancelText("取消")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                            okHttpUtils.setCanselTag(false);
                        }
                    }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {

                    if (progressdialog == null) {
                        progressdialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                        progressdialog.setTitleText("正在下载中");
                        progressdialog.setCancelable(false);
                    }
                    progressdialog.show();

                    updateMangerUtils.downloadPackage(AppConfig.updateapkdir);


                }
            }).show();


        }

        @Override
        public void onDownLoadMessage(int progress) {
            progressdialog.getProgressHelper().setProgress(progress);
        }

        @Override
        public void onSuccessMessage(String path, String filename) {
            if (progressdialog != null && tipdialog != null) {
                progressdialog.dismiss();
                tipdialog.dismiss();
            }

            updateMangerUtils.installApk(path, filename);
        }

        @Override
        public void onFailureMessage(String msg) {
            //弹出对话框

        }

        @Override
        public void onCanselMessage(String msg) {
            if (progressdialog != null)
                progressdialog.cancel();
        }
    };
}

