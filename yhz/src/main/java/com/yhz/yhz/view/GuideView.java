package com.yhz.yhz.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.yhz.yhz.R;
import com.yhz.yhz.adapter.GuideAdapter;
import com.yhz.yhz.interfaces.GuideCallBack;

import java.util.ArrayList;

/**
 * @description: GuideView (引导控件)
 * @author: Y.hz
 * @time: 2019/12/13 10:56
 */
public class GuideView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private ViewPager viewPager;
    private LinearLayout pointContent;
    private Button guideBtn;
    private View rootView;
    private int pageSize;
    private ArrayList<View> mPageViews;
    private ArrayList<ImageView> mPointView;
    private Bitmap selectPoint, unselectPoint;
    private GuideCallBack callBack;

    public GuideView(@NonNull Context context) {
        this(context, null);
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化布局
     *
     * @param context
     */
    private void initView(Context context) {
        mContext = context;
        mPageViews = new ArrayList<View>();
        mPointView = new ArrayList<ImageView>();
        rootView = LayoutInflater.from(context).inflate(R.layout.view_guild, this, true);
        viewPager = rootView.findViewById(R.id.guide_viewpager);
        pointContent = rootView.findViewById(R.id.point_content);
        guideBtn = rootView.findViewById(R.id.guide_btn);
    }


    public void setData(int[] pageImages, int[] guidePoint, GuideCallBack callBack) {
        this.callBack = callBack;
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        pageSize = pageImages.length;
        for (int i = 0; i < pageSize; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource((pageImages[i]));
            mPageViews.add(iv);
        }
        viewPager.setAdapter(new GuideAdapter(mPageViews));
        viewPager.addOnPageChangeListener(this);
        if (guidePoint!=null && guidePoint.length>=2){
            initPointViews(guidePoint);
        }
    }


    /**
     * 初始化点View
     */
    private void initPointViews(int[] guidePoint) {
        selectPoint = BitmapFactory.decodeResource(getResources(), guidePoint[0]);
        unselectPoint = BitmapFactory.decodeResource(getResources(), guidePoint[1]);
        mPointView = new ArrayList<ImageView>();
        pointContent.removeAllViews();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mParams.setMargins(dip2px(7), 0, 0, 0);
        for (int i = 0; i < pageSize; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageBitmap(unselectPoint);
            mPointView.add(iv);
            pointContent.addView(iv);
        }
        switchHighlightPoint(0);
    }

    private void switchHighlightPoint(int index) {
        if (index < 0 || index > pageSize - 1) {
            return;
        }
        int size = mPointView.size();
        for (int i = 0; i < size; i++) {
            if (index == i) {
                mPointView.get(i).setImageBitmap(selectPoint);
            } else {
                mPointView.get(i).setImageBitmap(unselectPoint);
            }
        }
    }

    /***
     * px 转 dp
     *
     * @return
     */
    public int dip2px(int dipValue) {
        if (dipValue == 0) {
            return 0;
        }
        final float scale = getScreenDensity();
        return (int) (dipValue * scale + 0.5f);
    }

    private float screenDensity;

    /***
     * 获取屏幕密度
     *
     * @return
     */
    public float getScreenDensity() {
        if (screenDensity != 0f) {
            return screenDensity;
        }
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        screenDensity = dm.density;
        return screenDensity;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switchHighlightPoint(i);
        if (callBack != null) {
            callBack.callSlidingPosition(i);
            if (i == pageSize - 1) {
//                mPageViews.get(pageSize - 1).setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        callBack.onClickLastListener();
//                    }
//                });
                guideBtn.setVisibility(VISIBLE);
                guideBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.onClickLastListener();
                    }
                });
                callBack.callSlidingLast();
            }else {
                guideBtn.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    public void clear() {
        pageSize = 0;
        clearPageViews();
        clearPointView();
        clearBitmap();

    }

    private void clearPageViews() {
        if (null != mPageViews) {
            int size = mPageViews.size();
            for (int i = 0; i < size; i++) {
                mPageViews.get(i).setBackgroundResource(0);
            }
            mPageViews.clear();
        }
        mPageViews = null;
    }

    private void clearPointView() {
        if (null != mPointView) {
            int size = mPointView.size();
            for (int i = 0; i < size; i++) {
                mPointView.get(i).setBackgroundResource(0);
            }
            mPointView.clear();
        }
        mPointView = null;
    }

    private void clearBitmap() {
        if (!selectPoint.isRecycled()) {
            selectPoint.recycle();
            selectPoint = null;
        }
        if (!unselectPoint.isRecycled()) {
            unselectPoint.recycle();
            unselectPoint = null;
        }
    }

}
