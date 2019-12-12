package com.yhz.yhz.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @description: PhoneUtils (手机信息工具类)
 * @author: Y.hz
 * @time: 2019/12/12 9:54
 */
public class PhoneUtils {
    /**
     * 获取终端型号
     */
    public static String getTem_Model() {
        String tem_no = android.os.Build.MODEL;
        return tem_no;
    }

    /**
     * 获取终端品牌
     */
    public static String getTem_Release() {
        String tem_title = android.os.Build.VERSION.RELEASE;
        return tem_title;
    }


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(TELEPHONY_SERVICE); // 提供了一系列用于访问与手机通讯相关的状态和信息的get方法
        @SuppressLint("MissingPermission")
        String imei = tm.getDeviceId();// 返回移动终端的软件版本，例如：GSM手机的IMEI/SV码。
        imei = imei == null ? "" : imei;
        return imei;

    }
}
