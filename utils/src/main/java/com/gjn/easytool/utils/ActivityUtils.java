package com.gjn.easytool.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * @author gjn
 * @time 2019/4/11 11:08
 */

public class ActivityUtils {
    private static final String TAG = "ActivityUtils";

    private static boolean isDebug = false;

    public static void toNext(Activity activity, Class cls){
        toNext(activity, cls, null);
    }

    public static void toNext(Activity activity, Class cls, Bundle bundle){
        showNext(activity, cls, bundle);
        activity.finish();
    }

    public static void showNext(Activity activity, Class cls){
        showNext(activity, cls, null);
    }

    public static void showNext(Activity activity, Class cls, Bundle bundle){
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        printLog(activity, cls);
        activity.startActivity(intent);
    }

    private static void printLog(Activity activity, Class cls) {
        if (isDebug) {
            Log.d(TAG, activity.getClass().getSimpleName() + " open " + cls.getSimpleName());
        }
    }
}
