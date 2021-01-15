package com.yhz.android_frame.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yhz.android_frame.R;
import com.yhz.yhz.activity.BaseActivity;
import com.yhz.yhz.util.ActivityUtil;
import com.yhz.yhz.util.ConstantUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @description: MainActivity (演示activity类)
 * @author: Y.hz
 * @time: 2019/12/02 16:30
 */
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断权限（Android 10以上没有手机IEMI权限 获取不到了）
//        if (!hasPermission(Manifest.permission.READ_PHONE_STATE)) {
//            requestPermission(ConstantUtil.PERMISSIONS_REQUEST_READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
//        }

        //欢迎页
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        timer.schedule(timerTask,1000*3);

    }
    // 处理请求权限结果
    @Override
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantUtil.PERMISSIONS_REQUEST_READ_PHONE_STATE:// 读取手机信息权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限请求成功
                    Toast.makeText(this, "权限请求成功", Toast.LENGTH_SHORT).show();
                } else {
                    // 权限请求失败
                    Toast.makeText(this, "权限请求失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // 处理网络状态结果
    @Override
    public void onNetChange(boolean netWorkState) {
        super.onNetChange(netWorkState);
    }

    // 设置返回按钮的监听事件
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 5000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtil.getInstance().exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean initIsOpenKeyboardEvent() {
        return false;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        findViewById(R.id.hold_id);
        findViewById(R.id.hold_selected_state);
        findViewById(R.id.hold_userid);
        findViewById(R.id.honorRequest);
        findViewById(R.id.id_iv);
        findViewById(R.id.id_ll_item);
        findViewById(R.id.id_rv);
        findViewById(R.id.id_tv);
        findViewById(R.id.ignore);
        findViewById(R.id.ignoreRequest);
        findViewById(R.id.invisible);
        findViewById(R.id.itemMain_tv);
        findViewById(R.id.jumpToEnd);
        findViewById(R.id.jumpToStart);
        findViewById(R.id.layout);
        findViewById(R.id.left);
        findViewById(R.id.linear);
        findViewById(R.id.middle);
        findViewById(R.id.motion_base);
        findViewById(R.id.NO_DEBUG);
        findViewById(R.id.packed);
        findViewById(R.id.parent);
        findViewById(R.id.parentRelative);

    }

    @Override
    protected void initHttp() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initMethod() {

    }
}
