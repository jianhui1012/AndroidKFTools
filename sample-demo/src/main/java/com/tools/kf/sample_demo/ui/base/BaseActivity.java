package com.tools.kf.sample_demo.ui.base;

import android.os.Bundle;
import android.view.MotionEvent;

import com.bugtags.library.Bugtags;
import com.tools.kf.sample_demo.MyApplication;
import com.tools.kf.ui.base.BaseAppCompatActivity;

/**
 * Created by djh on 2016/3/13.
 */
public class BaseActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    protected MyApplication getBaseApplication() {
        return (MyApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

}
