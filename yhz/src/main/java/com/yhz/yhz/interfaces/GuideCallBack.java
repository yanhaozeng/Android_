package com.yhz.yhz.interfaces;

/**
 * @description: CallBack (滑动接口)
 * @author: Y.hz
 * @time: 2019/12/13 10:58
 */
public interface GuideCallBack {

    // 滑动位置
    void callSlidingPosition(int position);

    // 滑动到最后一个
    void callSlidingLast();

    // 点击最后一个
    void onClickLastListener();
}
