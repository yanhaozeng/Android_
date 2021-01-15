package com.yhz.yhz.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yhz.yhz.broadcastReceiver.NetBroadcastReceiver;
import com.yhz.yhz.util.ActivityUtil;
import com.yhz.yhz.util.ConstantUtil;

/**
 * @description: BaseActivity (activity基本类)
 * @author: Y.hz
 * @time: 2019/12/02 11:29
 */
public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetChangeListener {
    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件
    private boolean isOpenKeyboardEvent = false;
//    private MMLoading mmLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 执行初始化方法
        isOpenKeyboardEvent = initIsOpenKeyboardEvent();
        // 沉浸效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        } else {
            // 隐藏标题栏
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
        }

        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);
        //设置布局
        setContentView(initLayout());
        // 初始化netEvent
        netEvent = this;
        initView();
        initData();
        initHttp();
        initMethod();
    }

    // 抽象 - 初始化方法，可以对数据进行初始化
    protected abstract boolean initIsOpenKeyboardEvent();

    // 抽象 - 初始化布局
    protected abstract int initLayout();

    // 抽象 - 初始化控件
    protected abstract void initView();

    // 抽象 - 初始化网络请求
    protected abstract void initHttp();

    // 抽象 - 初始化数据
    protected abstract void initData();

    // 抽象 - 初始化方法
    protected abstract void initMethod();

    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = ConstantUtil.TEXTVIEWSIZE;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            System.out.println("down");
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event)
    {
        if (v != null && (v instanceof EditText))
        {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom)
            {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else
            {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token)
    {
        if (token != null)
        {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // System.gc();
        netEvent = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
//        hideLoading();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
    }

    // 修改状态栏的颜色
    public void setStatusBarColor(int color) {
        try {
            Window window = getWindow();
            // 取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            // 设置状态栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取状态栏的高度
    public int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId);
        return 0;
    }

    // 底部导航栏的高度
    public int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId);
        return 0;
    }

    // 判断底部导航栏是否显示
    public boolean isNavigationBarShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(getApplicationContext()).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !menu && !back;
        }
    }

    // 事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (isOpenKeyboardEvent) {
//            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//                View v = getCurrentFocus();
//                if (isShouldHideInput(v, ev)) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    hideKeyboard(v.getWindowToken());   //收起键盘
//                    if (imm != null) {
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    }
//                }
//                return super.dispatchTouchEvent(ev);
//            }
//            // 必不可少，否则所有的组件都不会有TouchEvent了
//            return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
//        } else {
//            return super.dispatchTouchEvent(ev);
//        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /*protected void showLoading() {
        if (!this.isFinishing())//xActivity即为本界面的Activity
        {
            if (mmLoading == null) {
                MMLoading.Builder builder = new MMLoading.Builder(this)
                        .setMessage("加载中...")
                        .setCancelable(false)
                        .setCancelOutside(false);
                mmLoading = builder.create();
            } else {
                mmLoading.dismiss();
                MMLoading.Builder builder = new MMLoading.Builder(this)
                        .setMessage("加载中...")
                        .setCancelable(false)
                        .setCancelOutside(false);
                mmLoading = builder.create();
            }


            mmLoading.show();
        }
    }

    protected void showLoading(String msg) {
        if (!this.isFinishing())//xActivity即为本界面的Activity
        {
            if (mmLoading == null) {
                MMLoading.Builder builder = new MMLoading.Builder(this)
                        .setMessage(msg)
                        .setCancelable(false)
                        .setCancelOutside(false);
                mmLoading = builder.create();
            } else {
                mmLoading.dismiss();
                MMLoading.Builder builder = new MMLoading.Builder(this)
                        .setMessage(msg)
                        .setCancelable(false)
                        .setCancelOutside(false);
                mmLoading = builder.create();
            }

            mmLoading.show();
        }
    }

    protected void hideLoading() {
        if (!this.isFinishing())//xActivity即为本界面的Activity
        {
            if (mmLoading != null && mmLoading.isShowing()) {
                mmLoading.dismiss();
            }
        }
    }*/

}
