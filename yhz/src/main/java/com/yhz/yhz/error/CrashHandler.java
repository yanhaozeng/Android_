package com.yhz.yhz.error;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;
import com.yhz.yhz.util.VerifyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * @description: CrashHandler (全局异常捕获)
 * @author: Y.hz
 * @time: 2019/12/11 16:13
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    /**
     * 系统默认UncaughtExceptionHandler
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public static String UpLoadToServiceUrl = "";

    //日志文件夹
    private String LOG_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/android/log/";
    private File logTxt;

    /**
     * context
     */
    private Context mContext;

    private static CrashHandler mInstance;

    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例
     */
    public static synchronized CrashHandler getInstance() {
        if (null == mInstance) {
            mInstance = new CrashHandler();
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void init(Context context, String url) {
        mContext = context;
        UpLoadToServiceUrl = url;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * uncaughtException 回调函数
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //导出到SD卡
        dumpExceptonToSDCard(ex);

        //得到异常信息字符串
        String exceptionInfoStr = getExceptionInfoStr();

        //上传服务器
        uploadToServer(exceptionInfoStr, logTxt);

        //如果系统提供的默认的异常处理器，让系统去处理
        if (null != mDefaultHandler) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }


    /**
     * 上传信息到服务器
     *
     * @param exceptionInfoStr
     */
    private void uploadToServer(String exceptionInfoStr, File logTxt) {
        if (!VerifyUtils.isEmpty(UpLoadToServiceUrl)) {
            Kalle.post(UpLoadToServiceUrl)
                    .file("file", logTxt)
                    .perform(new SimpleCallback<String>() {
                        @Override
                        public void onResponse(SimpleResponse<String, String> response) {

                        }
                    });
        }
    }

    /**
     * 导出到sd卡,需要写入SD卡权限 如果无法写入。请查看APP设置是否有权限
     */
    private void dumpExceptonToSDCard(Throwable ex) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        File file = new File(LOG_DIR);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return;
            }
        }

        long currentTimeMillis = System.currentTimeMillis();
        String format = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(currentTimeMillis);
        logTxt = new File(LOG_DIR + format + ".txt");

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logTxt)));
            pw.println(format);
            phoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //设备的信息
    private void phoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();

        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_RECEIVERS);
        pw.println("App Version:" + pi.versionName);
        pw.println("AppCode:" + pi.versionCode);
        pw.println("OS version :" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        pw.println("Vendor:" + Build.MANUFACTURER);
        pw.println("Model:" + Build.MODEL);
    }

    //返回异常信息字符串
    private String getExceptionInfoStr() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(logTxt));
            String str = "";
            StringBuilder sb = new StringBuilder();

            while ((str = bufferedReader.readLine()) != null) {
                sb.append("\n");
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "异常信息文件文件未找到";
    }

    //返回异常文件路径
    public String getLOG_DIR(){
        return LOG_DIR;
    }

}
