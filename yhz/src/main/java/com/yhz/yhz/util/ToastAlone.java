package com.yhz.yhz.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @description: ToastAlone ()
 * @author: Y.hz
 * @time: 2021/01/15 15:36
 */
public class ToastAlone extends Toast {
    Context context;
    /**
     * 唯一的toast
     */
    private static Toast mToast = null;
    public ToastAlone(Context context) {
        super(context);
        this.context=context;
    }

    public static Toast showToast(Context ctx, int stringid, int lastTime) {
        if (mToast != null) {
            //mToast.cancel();
        } else {
            mToast = Toast.makeText(ctx, stringid, lastTime);
        }
        mToast.setText(stringid);
        mToast.show();
        return mToast;
    }

    public static Toast showToast(Context ctx, String tips, int lastTime) {
        if (mToast != null) {
            //mToast.cancel();
        } else {
            mToast = Toast.makeText(ctx, tips, lastTime);
        }
        mToast.setText(tips);
        mToast.show();
        return mToast;
    }

    public static Toast showToast(Context ctx, String tips) {
        if (mToast != null) {
            //mToast.cancel();
        } else {
            mToast = Toast.makeText(ctx, tips, Toast.LENGTH_SHORT);
        }
        mToast.setText(tips);
        mToast.show();
        return mToast;
    }

}
