package com.jeez.guanpj.jreadhub.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 捕获全局异常,因为有的异常我们捕获不到
 *
 * @author river
 */
public class UncaughtException implements UncaughtExceptionHandler {

    private static final boolean DEBUG = true;
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private final static String TAG = "UncaughtException";
    private static UncaughtException mUncaughtException;
    private Context context;
    private String path = null;

    public Context getContext() {
        return context;
    }

    private UncaughtException() {
    }

    /**
     * 同步方法，以免单例多线程环境下出现异常
     *
     * @return
     */
    public synchronized static UncaughtException getInstance() {
        if (mUncaughtException == null) {
            mUncaughtException = new UncaughtException();
        }
        return mUncaughtException;
    }

    /**
     * 初始化，把当前对象设置成UncaughtExceptionHandler处理器
     */
    public void init(Context context, String appRootDir) {
        this.context = context;
        path = Environment.getExternalStorageDirectory().getPath() +
                "/" + appRootDir + "/log/";
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtException);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //处理异常,我们还可以把异常信息写入文件，以供后来分析。
        try {
            dumpExceptionToSDCard(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        showDialog();
    }

    private void showDialog() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                new AlertDialog.Builder(context).setTitle("提示").setCancelable(false).setMessage("抱歉，程序已停止运行，请重新启动。")
                        .setNeutralButton("确定", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        }).create().show();
                Looper.loop();
            }
        }.start();
    }

    /**
     * 信息写入SD卡中
     *
     * @param ex
     * @throws IOException
     */
    @SuppressLint("SimpleDateFormat")
    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        // 如果SD卡不存在或无法使用，则无法把异常信息写入SD�?
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            long current = System.currentTimeMillis();
            // 以当前时间创建log文件
            File file = new File(path + java.util.UUID.randomUUID().toString() + FILE_NAME_SUFFIX);
            //判断文件是否存在
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            // 导出发生异常的时�?
            pw.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(current)));
            dumpPhoneInfo(pw);
            pw.println();
            // 导出异常的调用栈信息
            ex.printStackTrace(pw);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "dump crash info failed");
        }
    }

    /**
     * 手机信息
     *
     * @param pw
     * @throws NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
        // 应用的版本名称和版本�?
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        // android版本�?
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");

        pw.println(Build.VERSION.SDK_INT);
        // 手机制�?�?
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    /**
     * 上传到服务器
     */
    private void uploadExceptionToServer() {
    }

}
