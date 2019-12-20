package com.yhz.yhz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.yhz.yhz.R;
import com.yhz.yhz.interfaces.HeadCallBack;

/**
 * @description: HeadView (公共头部控件)
 * @author: Y.hz
 * @time: 2019/12/20 14:50
 */
public class HeadView extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private View view;
    private RelativeLayout headRl;
    private ImageView headImgLeft, headImgRight;
    private TextView headTvTitle;
    private HeadCallBack headCallBack;

    public HeadView(Context context) {
        super(context);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_head, this);
        headRl = view.findViewById(R.id.head_rl);
        headImgLeft = view.findViewById(R.id.head_img_left);
        headImgRight = view.findViewById(R.id.head_img_right);
        headTvTitle = view.findViewById(R.id.head_tv_title);
    }

    public void setData(@ColorRes int colorId, String titleText, boolean leftVisibiliy,
                        @DrawableRes int leftImgId, boolean rightVisibiliy,
                        @DrawableRes int rightImgId, HeadCallBack headCallBack) {
        this.headCallBack = headCallBack;
        if (colorId!=0){
            headRl.setBackgroundColor(mContext.getResources().getColor(colorId));
        }
        if (leftVisibiliy) {
            headImgLeft.setVisibility(VISIBLE);
        } else {
            headImgLeft.setVisibility(INVISIBLE);
        }
        if (leftImgId!=0){
            headImgLeft.setBackgroundResource(leftImgId);
        }
        headImgLeft.setOnClickListener(this);

        if (rightVisibiliy) {
            headImgRight.setVisibility(VISIBLE);
        } else {
            headImgRight.setVisibility(INVISIBLE);
        }
        if (rightImgId!=0){
            headImgRight.setBackgroundResource(rightImgId);
        }
        headImgRight.setOnClickListener(this);

        headTvTitle.setText(titleText);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.head_img_left) {
            headCallBack.onLeftClickLastListener();
        }else if (v.getId() == R.id.head_img_right){
            headCallBack.onRightClickLastListener();
        }
    }
}
