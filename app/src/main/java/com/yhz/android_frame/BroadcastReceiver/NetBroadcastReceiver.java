package com.yhz.android_frame.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.yhz.android_frame.activity.BaseActivity;
import com.yhz.android_frame.activity.BaseFragment;
import com.yhz.android_frame.activity.BaseFragmentActivity;
import com.yhz.yhz.util.NetworkUtil;

/**
 * @description: NetBroadcastReceiver ()
 * @author: Y.hz
 * @time: 2019/12/02 14:09
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean netWorkState = NetworkUtil.isNetworkConnected(context);
            // 接口回调传过去状态的类型
            if (BaseActivity.netEvent != null)
                BaseActivity.netEvent.onNetChange(netWorkState);
            if (BaseFragment.netEvent != null)
                BaseFragment.netEvent.onNetChange(netWorkState);
            if (BaseFragmentActivity.netEvent != null)
                BaseFragmentActivity.netEvent.onNetChange(netWorkState);
        }
    }
    // 网络状态变化接口
    public interface NetChangeListener {
        void onNetChange(boolean netWorkState);
    }
}
