package com.gjn.easybase;

import com.gjn.easytool.logger.EasyLog;

public class BaseLog {
    private static final String TAG = "BaseLog";

    public static boolean isDebug = true;

    public static void v(String tag, String msg, Throwable tr) {
        if (!isDebug) {
            return;
        }
        EasyLog.v(tag, msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (!isDebug) {
            return;
        }
        EasyLog.d(tag, msg, tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (!isDebug) {
            return;
        }
        EasyLog.i(tag, msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (!isDebug) {
            return;
        }
        EasyLog.w(tag, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (!isDebug) {
            return;
        }
        EasyLog.e(tag, msg, tr);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (!isDebug) {
            return;
        }
        EasyLog.wtf(tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void wtf(String tag, String msg) {
        wtf(tag, msg, null);
    }

    public static void wtf(String msg) {
        wtf(TAG, msg);
    }
}
