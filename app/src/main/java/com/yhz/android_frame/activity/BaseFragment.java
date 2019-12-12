package com.yhz.android_frame.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yhz.android_frame.BroadcastReceiver.NetBroadcastReceiver;

/**
 * @description: BaseFragment ()
 * @author: Y.hz
 * @time: 2019/12/02 14:26
 */
public abstract class BaseFragment extends Fragment implements NetBroadcastReceiver.NetChangeListener {
    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化netEvent
        netEvent = this;

        // 初始化方法
        initView();
        initHttp();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        netEvent = null;
    }

    // 抽象 - 初始化控件
    protected abstract void initView();
    // 抽象 - 初始化网络请求
    protected abstract void initHttp();
    // 抽象 - 初始化数据
    protected abstract void initData();

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
    }
}
