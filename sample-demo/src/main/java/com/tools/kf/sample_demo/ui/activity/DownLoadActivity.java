package com.tools.kf.sample_demo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.db.YueTuDB;
import com.tools.kf.sample_demo.model.DLPic;
import com.tools.kf.sample_demo.ui.adapter.GridViewAdapter;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.utils.FileHelper;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6 0006.
 */
@ContentView(R.layout.activity_download)
public class DownLoadActivity extends BaseActivity {

    @ViewInject(R.id.dlmanger_view)
    private GridView gridView;
    private YueTuDB yueTuDB;
    private List<DLPic> dlPicList;

    private List<DLPic> choose_dlPicList = new ArrayList<>();
    private GridViewAdapter gridViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initad();
        initAppBarSetting();
        yueTuDB = YueTuDB.getInstance(this);
        dlPicList = yueTuDB.loadDLPic();

        gridViewAdapter = new GridViewAdapter(this, dlPicList);
        gridViewAdapter.setItemClickListener(new GridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DLPic dlPic, Object viewholder) {

            }

            @Override
            public void onLongClick(DLPic dlPic, Object viewholder) {
                ImageView imageView = ((GridViewAdapter.ViewHolder) viewholder).chooseDelete;
                if (imageView.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.GONE);
                    choose_dlPicList.remove(dlPic);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    if (!choose_dlPicList.contains(dlPic))
                        choose_dlPicList.add(dlPic);
                }
            }
        });
        gridView.setAdapter(gridViewAdapter);

    }

    private void initAppBarSetting() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        if (choose_dlPicList.size() == 0) {
                            Snackbar.make(gridView, "未选中目标", Snackbar.LENGTH_SHORT).show();
                            break;
                        }

                        Snackbar.make(gridView, "正在删除中...", Snackbar.LENGTH_SHORT).show();
                        Iterator<DLPic> iterator = choose_dlPicList.iterator();
                        boolean isok = true;
                        while (iterator.hasNext()) {
                            DLPic dlpic = iterator.next();
                            if (!FileHelper.deleteFile(dlpic.getFilepath()) || !yueTuDB.deleteDLPic(dlpic.getId())) {
                                isok = false;
                                break;
                            }
                        }
                        if (isok)
                            Snackbar.make(gridView, "删除成功", Snackbar.LENGTH_SHORT).show();
                        else
                            Snackbar.make(gridView, "删除失败", Snackbar.LENGTH_SHORT).show();

                       /* dlPicList.removeAll(choose_dlPicList);
                        choose_dlPicList.clear();
                        gridViewAdapter.notifyDataSetChanged();*/

                        dlPicList.removeAll(choose_dlPicList);
                        choose_dlPicList.clear();
                        gridViewAdapter = new GridViewAdapter(DownLoadActivity.this, dlPicList);
                        gridView.setAdapter(gridViewAdapter);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected boolean isShowBugTags() {
        return false;
    }

    @Override
    protected Drawable getSystemBarDrawable() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onNetworkConnected(NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {
        super.onNetworkDisConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置toobar右边布局
        getMenuInflater().inflate(R.menu.download_menu, menu);
        return true;
    }
}
