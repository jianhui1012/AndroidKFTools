package com.tools.kf.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import com.tools.kf.view.ViewInjectorImpl;
import com.tools.kf.utils.LogHelper;

public class BasicActivity extends AppCompatActivity implements OnGestureListener {

    protected BasicActivity context;
    public int screenWidth = 0;
    public int screenHeight = 0;
    private GestureDetector detector;
    private boolean isGesture = false;
    private DisplayMetrics dm;

    protected static final int REQUEST_CODE_CAMERA = 1;
    protected static final int REQUEST_CODE_PHOTO = 2;
    protected static final int REQUEST_CODE_PHOTO_DEAL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (dm == null) {
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        //设置屏幕的宽高
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        // 手势
        detector = new GestureDetector(context, this);
        //注解初始化
        ViewInjectorImpl.getInsatnce().inject(this);
        LogHelper.LogD(getClass().getName() + " onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogHelper.LogD(getClass().getName() + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogHelper.LogD(getClass().getName() + " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogHelper.LogD(getClass().getName() + " onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogHelper.LogD(getClass().getName() + " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.LogD(getClass().getName() + " onDestroy");
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (detector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (isGesture) {
            float subX = e2.getX() - e1.getX();
            float subY = e2.getY() - e1.getY();
            if (subX > 150 && Math.abs(subY) < 170) {
                scrollXBack();
            } else if (subX < -150 && Math.abs(subY) < 170) {
                srollYRight();
            }
        }
        return false;
    }

    protected void scrollXBack() {
        finish();
    }

    protected void srollYRight() {

    }

    /**
     * 设置为可以侧滑关闭
     *
     * @param state
     */
    public void setGuesture(boolean state) {
        isGesture = state;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    /**
     * 覆写finish方法，覆盖默认方法，加入切换动画
     */
    public void finish() {
        super.finish();

    }


    public void finishResult() {
        setResult(RESULT_OK);
        this.finish();
    }

    /***
     * 调用父类finish方法
     */
    public void finishSimple() {
        super.finish();
    }

    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }

    /**
     * 带回调的跳转
     *
     * @param bundle
     * @param requestCode
     * @param target
     */
    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);

    }

    /**
     * 打开照相机
     */
    protected void openCamera(String sdtempdir) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                Uri.parse(sdtempdir));
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 打开照片选择
     */
    protected void pickUpPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    /*
     * 对图片进行剪裁，通过Intent来调用系统自带的图片剪裁API
     */
    protected void cropPhoto(Uri uri, String sdtempdir) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 5);

        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(sdtempdir));
        startActivityForResult(intent, REQUEST_CODE_PHOTO_DEAL);
    }


}
