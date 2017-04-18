package com.tools.kf.sample_demo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tools.kf.request.netstatus.NetType;
import com.tools.kf.sample_demo.R;
import com.tools.kf.sample_demo.ui.base.BaseActivity;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.ViewInject;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
@ContentView(R.layout.activity_aboutus)
public class AboutUsActivity extends BaseActivity {

    @ViewInject(R.id.id_webview)
    private WebView id_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //启用支持javascript
        WebSettings settings = id_webview.getSettings();
        settings.setJavaScriptEnabled(true);
        id_webview.addJavascriptInterface(new JsInteration(), "control");
        //id_webview.loadUrl("file:///android_asset/fcqy.html");
        id_webview.loadUrl("https://github.com/smithLiLi/AndroidKFTools/blob/master/README.md");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        id_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    public class JsInteration {

        @JavascriptInterface
        public void turnQiYeDetail() {
            readyGo(MainActivity.class);
        }

    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
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
}
