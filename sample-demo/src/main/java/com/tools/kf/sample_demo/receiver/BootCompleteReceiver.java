package com.tools.kf.sample_demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tools.kf.sample_demo.ui.activity.SplashActivity;
import com.tools.kf.utils.LogHelper;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //开机启动
//        Intent in = new Intent(context, SplashActivity.class);
//        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(in);
//        Toast.makeText(context,"接受成功",Toast.LENGTH_SHORT).show();
    }
}
