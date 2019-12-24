package com.yhz.yhz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yhz.yhz.R;
import com.yhz.yhz.interfaces.CircleAddCallBack;

/**
 * @description: CircleAddView (圆形添加控件)
 * @author: Y.hz
 * @time: 2019/12/20 18:00
 */
public class CircleAddView extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private View view;
    private RelativeLayout circleAddRl;
    private ImageView circleAddImg;
    private CircleAddCallBack mCircleAddCallBack;

    public CircleAddView(@NonNull Context context) {
        super(context);
    }

    public CircleAddView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleAddView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.view_circle_add,this);
        circleAddRl = view.findViewById(R.id.circleAdd_rl);
        circleAddImg = view.findViewById(R.id.circleAdd_img);
    }

    public void setData(@DrawableRes int rlBG , @DrawableRes int imgBG , CircleAddCallBack circleAddCallBack){
        mCircleAddCallBack = circleAddCallBack;
        if (rlBG != 0) circleAddRl.setBackgroundResource(rlBG);
        if (imgBG != 0) circleAddImg.setBackgroundResource(imgBG);
        circleAddRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCircleAddCallBack.onClickLastListener();
    }
}
