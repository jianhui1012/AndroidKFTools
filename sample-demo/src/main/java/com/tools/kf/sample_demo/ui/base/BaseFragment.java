package com.tools.kf.sample_demo.ui.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.tools.kf.ui.base.BaseLazyFragment;
import com.tools.kf.utils.LogHelper;


/**
 * Created by LikeGo on 2016/4/6.
 */
public abstract class BaseFragment extends BaseLazyFragment   {

    /***
     *
     * @param layoutid
     * @return
     */
    public RecyclerView.LayoutManager chooseLayoutManager(int layoutid) {
        RecyclerView.LayoutManager layoutManager=null;
        switch (layoutid) {
            case 0:
                layoutManager = new LinearLayoutManager(getContext());
                ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
            case 1:
                layoutManager = new GridLayoutManager(getContext(),2);
                ((GridLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
            case 2:
                layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                break;
        }
        return layoutManager;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        LogHelper.LogD(this.getClass().getName()+"weak activity has been recyled");
    }
}
