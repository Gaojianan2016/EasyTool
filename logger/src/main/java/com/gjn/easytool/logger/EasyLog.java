package com.gjn.easytool.logger;

import android.util.Log;

import com.gjn.easytool.utils.JsonUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * @author gjn
 * @time 2019/4/2 14:47
 */

public class EasyLog {
    private static final String TAG = "EasyLog";

    public static final int LEVEL_V =   2;
    public static final int LEVEL_D =   3;
    public static final int LEVEL_I =   4;
    public static final int LEVEL_W =   5;
    public static final int LEVEL_E =   6;
    public static final int LEVEL_WTF = 7;

    public static final String HEAD = "╔══════════════════════════════════════════════════════════";
    public static final String BODY = "║ ";
    public static final String FOOT = "╚══════════════════════════════════════════════════════════";

    public static boolean isDebug = true;

    private static void println(int level, String tag, String msg, Throwable tr) {
        if (!isDebug) {
            return;
        }
        switch (level) {
            case LEVEL_WTF:
                Log.wtf(tag, msg, tr);
                break;
            case LEVEL_E:
                Log.e(tag, msg, tr);
                break;
            case LEVEL_W:
                Log.w(tag, msg, tr);
                break;
            case LEVEL_I:
                Log.i(tag, msg, tr);
                break;
            case LEVEL_D:
                Log.d(tag, msg, tr);
                break;
            case LEVEL_V:
            default:
                Log.v(tag, msg, tr);
        }
    }

    private static void header(int level, String tag) {
        println(level, tag, HEAD, null);
    }

    private static void body(int level, String tag, String msg) {
        println(level, tag, BODY + msg, null);
    }

    private static void footer(int level, String tag) {
        println(level, tag, FOOT, null);
    }

    public static void v(String tag, String msg, Throwable tr) {
        println(LEVEL_V, tag, msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        println(LEVEL_D, tag, msg, tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        println(LEVEL_I, tag, msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        println(LEVEL_W, tag, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        println(LEVEL_E, tag, msg, tr);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        println(LEVEL_WTF, tag, msg, tr);
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

    public static void json(Object obj) {
        if (obj == null) {
            e("obj is null.");
            return;
        }
        String json;
        if (obj instanceof String) {
            json = ((String) obj);
        } else if (obj instanceof JSONObject) {
            json = obj.toString();
        } else {
            json = new Gson().toJson(obj);
        }
        d(HEAD);
        d(JsonUtils.formatString(BODY, json));
        d(FOOT);
    }
}
