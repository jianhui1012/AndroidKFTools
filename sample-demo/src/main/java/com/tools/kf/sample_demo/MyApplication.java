package com.tools.kf.sample_demo;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsCallback;
import com.bugtags.library.BugtagsOptions;

/**
 * Created by djh on 2016/3/13.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBugTags();


    }


    private void initBugTags() {
        //customizable init option
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置
                trackingCrashLog(true).//是否收集crash
                trackingConsoleLog(true).//是否收集console log
                trackingUserSteps(true).//是否收集用户操作步骤
                build();
        Bugtags.start("fd8a95353a7a8f8bf8aa1d59270bb2c1", this, Bugtags.BTGInvocationEventBubble, options);


        Bugtags.setBeforeSendingCallback(new BugtagsCallback() {
            @Override
            public void run() {
                Bugtags.log("before");
            }
        });

        Bugtags.setAfterSendingCallback(new BugtagsCallback() {
            @Override
            public void run() {
                Bugtags.log("after");
            }
        });
    }


}
