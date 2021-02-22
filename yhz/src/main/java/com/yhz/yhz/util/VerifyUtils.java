package com.yhz.yhz.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: VerifyUtils (验证工具类)
 * @author: Y.hz
 * @time: 2019/12/12 11:15
 */
public class VerifyUtils {
    /**
     * @description:  * @param phoneNumber (验证手机号)
     * @return:
     * @author: Y.hz
     * @time:  2019/12/12 11:34
     */
    public static boolean isMobile(String phoneNumber) {
        String str = phoneNumber;
        String pattern = "^(13[0-9]|14[4-9]|15[^4]|16[6-7]|17[^9]|18[0-9]|19[1|8|9])[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }

    /**
     * @description:  * @param passwordNumber (验证密码，6-16位数字字母混合,不能全为数字,不能全为字母,首位不能为数字)
     * @return:
     * @author: Y.hz
     * @time:  2019/12/12 11:36
     */
    public boolean isPassword(String password){
        String regex="^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(password);
        boolean isMatch=m.matches();
        return isMatch;
    }

    /**
     * @description:  * @param emailNumber (验证邮箱格式)
     * @return:
     * @author: Y.hz
     * @time:  2019/12/12 11:38
     */
    public static boolean isEmail(String emailNumber) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return emailNumber.matches(strPattern);
        }
    }
    /**
     * @description:  * @param str (验证String数据是否为空)
     * @return: boolean
     * @author: Y.hz
     * @time:  2019/12/12 11:39
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.replaceAll(" ","").equals("");
    }

    /**
     * @description:  * @param str (验证字符串中是否有中文)
     * @return: boolean
     * @author: Y.hz
     * @time:  2019/12/12 11:56
     */
    public static boolean checkChinese(String str) {
        final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
        boolean result = false;
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(str);
        result = matcher.find();
        return result;
    }
}
