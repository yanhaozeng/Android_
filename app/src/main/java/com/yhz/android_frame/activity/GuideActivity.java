package com.yhz.android_frame.activity;

import com.yhz.android_frame.R;
import com.yhz.yhz.activity.BaseActivity;
import com.yhz.yhz.interfaces.GuideCallBack;
import com.yhz.yhz.view.GuideView;

import butterknife.BindView;


/**
 * @description: GuideActivity (引导页使用演示)
 * @author: Y.hz
 * @time: 2019/12/13 14:18
 */
public class GuideActivity extends BaseActivity implements GuideCallBack {

    @BindView(R.id.guide_view)
    GuideView guideView;
    //引导页图片集合
    private final int[] mPageImages = {
    };
    //引导页指示器集合（只用第一个和第二个参数），第一参数为当前所在状态图片，第二参数为其他状态图片
    private final int[] mGuidePoint = {
    };


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
        guideView.setData(mPageImages, mGuidePoint, this);
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

    //当前滑动第几个view
    @Override
    public void callSlidingPosition(int position) {

    }

    //滑动到最后一个view
    @Override
    public void callSlidingLast() {

    }

    //最后一个view中 跳过的点击事件
    @Override
    public void onClickLastListener() {

    }
}
