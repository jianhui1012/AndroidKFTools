package com.tools.kf.sample_demo.ui.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.db.YueTuDB;
import com.tools.kf.sample_demo.model.DLPic;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.sample_demo.utils.AndroidShare;
import com.tools.kf.sample_demo.utils.AppConfig;
import com.tools.kf.utils.FileHelper;
import com.tools.kf.utils.LogHelper;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
@ContentView(R.layout.activity_largeimage)
public class LargeImageActivity extends BaseActivity {

    @ViewInject(R.id.large_draweeview)
    private SimpleDraweeView large_draweeview;
    private ViewGroup.LayoutParams layoutParams;
    private int pic_height;
    private String pic_url;
    private int pic_width;
    private String pic_title;

    private YueTuDB yueTuDB;
    private String filename;

    public boolean isdownloading = false;


    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected boolean isShowBugTags() {
        return true;
    }

    @Override
    protected Drawable getSystemBarDrawable() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yueTuDB = YueTuDB.getInstance(this);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_download:
                        if (isdownloading) {
                            Snackbar.make(mToolbar, "正在下载中请,勿重复点击", Snackbar.LENGTH_SHORT).show();
                            break;
                        }
                        if (!yueTuDB.isCanDownLoad(pic_url)) {
                            Snackbar.make(mToolbar, "已下载该图片", Snackbar.LENGTH_SHORT).show();
                            break;
                        }
                        isdownloading = true;
                        OkHttpUtils.get().url(pic_url).build().execute(new FileCallBack(AppConfig.Paths.IMAGE_LOADER_APPPATH, filename)//
                        {
                            @Override
                            public void inProgress(float progress, long total) {
                                // mProgressBar.setProgress((int) (100 * progress));
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                Snackbar.make(mToolbar, "下载失败" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                LogHelper.LogD(AppConfig.TAG, "onResponse :" + e.getMessage());
                            }

                            @Override
                            public void onResponse(File file) {
                                // LogHelper.LogD(AppConfig.TAG, "onResponse :" + file.getAbsolutePath());
                                DLPic dlPic = new DLPic();
                                dlPic.setOriginurl(pic_url);
                                dlPic.setFilepath(AppConfig.Paths.IMAGE_LOADER_APPPATH + filename);
                                dlPic.setFilename(filename);
                                dlPic.setIslike(false);
                                if (yueTuDB.insertDLPic(dlPic)) {
                                    Snackbar.make(mToolbar, "图片已下载完成", Snackbar.LENGTH_SHORT).show();
                                    isdownloading = false;
                                }
                            }
                        });
                        break;
                    case R.id.action_share:
                        AndroidShare as = new AndroidShare(
                                LargeImageActivity.this,
                                "来自阅图分享--" + pic_title,
                                pic_url);
                        as.show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        setTitle("大图预览");
        layoutParams = large_draweeview.getLayoutParams();

        if (pic_width != 0 && pic_height != 0) {
            if (pic_height <= pic_width * 2) {
                float mHeight = ((float) (pic_height) / ((float) (pic_width))) * screenWidth;
                layoutParams.width = screenWidth;
                layoutParams.height = (int) mHeight;
            } else {
                float mWidth = ((float) (pic_width) / ((float) (pic_height))) * screenHeight;
                layoutParams.height = screenHeight;
                layoutParams.width = (int) mWidth;
            }
        } else {
            layoutParams.width = screenWidth;
            layoutParams.height = screenHeight;
        }
//        if (pic_height*screenDensity >= screenHeight) {
//            layoutParams.height = screenHeight;
//        } else {
//            layoutParams.height = (int)(pic_height*screenDensity);
//        }
//        if (pic_width*screenDensity >= screenWidth) {
//            layoutParams.width = screenWidth;
//        } else {
//            layoutParams.width = (int)(pic_width*screenDensity);
//        }

        large_draweeview.setLayoutParams(layoutParams);
        large_draweeview.setImageURI(Uri.parse(pic_url));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        pic_url = extras.getString("pic_url");
        pic_width = extras.getInt("pic_width");
        pic_height = extras.getInt("pic_height");
        pic_title = extras.getString("pic_title");
        filename = FileHelper.getFileName(pic_url);
    }

    @Override
    protected void onNetworkConnected(NetType type) {
        // Snackbar.make(mToolbar, "网络已连接", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onNetworkDisConnected() {
        super.onNetworkDisConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置toobar右边布局
        getMenuInflater().inflate(R.menu.large_menu, menu);
        return true;
    }
}
