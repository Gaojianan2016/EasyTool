package com.gjn.easytool.utils;

import android.app.Activity;
import android.util.Log;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gjn
 * @time 2019/4/11 10:02
 */

public class AppManager {
    private static final String TAG = "AppManager";

    private static CopyOnWriteArrayList<Activity> mActivities;

    private static AppManager appManager;

    private AppManager() {
        mActivities = new CopyOnWriteArrayList<>();
    }

    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    public static CopyOnWriteArrayList<Activity> getActivities() {
        return mActivities;
    }

    public int getActivityItemCount() {
        return mActivities.size();
    }

    public Activity getActivityItem(int position) {
        return mActivities.get(position);
    }

    public void addActivity(Activity activity) {
        Log.d(TAG, "push " + activity.getClass().getSimpleName());
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            Log.d(TAG, "remove " + activity.getClass().getSimpleName());
            mActivities.remove(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void finishActivity(Class cls) {
        for (Activity activity : mActivities) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        for (Activity activity : mActivities) {
            finishActivity(activity);
        }
    }
}
