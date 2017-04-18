/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tools.kf.sample_demo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

import cn.waps.AppConnect;

@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @ViewInject(R.id.splash_image)
    private ImageView mSplashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViews(R.drawable.login_background);
        //方式①：通过代码设置 APP_ID 和 APP_PID
        AppConnect.getInstance("2c42e7cb1583f0c656c375368816d8e2", "xiaomi", this);
        AppConnect.getInstance(this).initPopAd(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onNetworkConnected(NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

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


    public void initializeViews(int backgroundResId) {
        mSplashImage.setImageResource(backgroundResId);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                readyGoThenEnd(MainActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashImage.startAnimation(animation);
    }


}
