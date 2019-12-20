package com.yhz.yhz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yhz.yhz.R;
import com.yhz.yhz.interfaces.HeadCallBack;
import com.yhz.yhz.util.VerifyUtils;

/**
 * @description: HeadSearchView ()
 * @author: Y.hz
 * @time: 2019/12/20 16:11
 */
public class HeadSearchView extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private View view;
    private RelativeLayout headSearchRl;
    private ImageView headSearchImgLeft, headSearchImg;
    private EditText headSearchEd;
    private Button headSearchBtnRight;
    private HeadCallBack mHeadCallBack;

    public HeadSearchView(@NonNull Context context) {
        super(context);
    }

    public HeadSearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadSearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_head_search,this);
        headSearchRl = view.findViewById(R.id.headsearch_rl);
        headSearchImgLeft = view.findViewById(R.id.headsearch_img_left);
        headSearchImg = view.findViewById(R.id.headsearch_img);
        headSearchEd = view.findViewById(R.id.headsearch_ed);
        headSearchBtnRight = view.findViewById(R.id.headsearch_btn_right);
    }

    public void setData(@ColorRes int colorId , boolean leftVisibiliy , @DrawableRes int leftImgId ,
                        @DrawableRes int imgId , String hintText , @DrawableRes int btnBG ,
                        @ColorRes int btnColorId , HeadCallBack headCallBack){
        mHeadCallBack = headCallBack;
        if (colorId != 0){
            headSearchRl.setBackgroundColor(mContext.getResources().getColor(colorId));
        }
        if (leftVisibiliy){
            headSearchImgLeft.setVisibility(VISIBLE);
        }else {
            headSearchImgLeft.setVisibility(GONE);
        }
        if (leftImgId!=0){
            headSearchImgLeft.setBackgroundResource(leftImgId);
        }
        headSearchImgLeft.setOnClickListener(this);
        if (imgId != 0){
            headSearchImg.setBackgroundResource(imgId);
        }
        if (!VerifyUtils.isEmpty(hintText)){
            headSearchEd.setHint(hintText);
        }
        if (btnBG != 0){
            headSearchBtnRight.setBackgroundResource(btnBG);
        }
        if (btnColorId != 0){
            headSearchBtnRight.setTextColor(mContext.getResources().getColor(btnColorId));
        }
        headSearchBtnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.head_img_left){
            mHeadCallBack.onLeftClickLastListener();
        }else if (v.getId() == R.id.headsearch_btn_right){
            mHeadCallBack.onRightClickLastListener();
        }
    }
}
