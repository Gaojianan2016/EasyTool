package com.gjn.easytool.toaster;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjn.easytool.utils.ReflexUtils;
import com.gjn.easytool.utils.ResourcesUtils;

/**
 * @author gjn
 * @time 2019/4/3 9:59
 */

public class EasyToast {
    public static final int EASY_TYPE_CONFIRM =     0;
    public static final int EASY_TYPE_INFO =        1;
    public static final int EASY_TYPE_WARNING =     2;
    public static final int EASY_TYPE_ERROR =       3;
    public static final int EASY_TYPE_FAIL =        4;
    public static final int EASY_TYPE_SUCCESS =     5;
    public static final int EASY_TYPE_WAIT =        6;
    public static final int EASY_TYPE_PROHIBIT =    7;

    private static EasyToast mEasyToast = new EasyToast();

    private static Toast mToast;
    private static Context mContext;
    private static View mEasyView;
    private static View mDefaultView;
    private static int defaultGravity;
    private static int defaultYOffset;

    private EasyToast() {
    }

    public static EasyToast getInstance(Context context) {
        if (mToast == null) {
            mContext = context;
            mToast = Toast.makeText(mContext.getApplicationContext(), "", Toast.LENGTH_SHORT);
            mDefaultView = mToast.getView();
            defaultGravity = mToast.getGravity();
            defaultYOffset = mToast.getYOffset();
        }
        if (mEasyView == null) {
            mEasyView = LayoutInflater.from(mContext).inflate(
                    R.layout.easytoast_transient_notification, null);
        }
        return mEasyToast;
    }

    public static void show(Context context, String msg) {
        getInstance(context).show(msg);
    }

    public static void show(Context context, String msg, int duration) {
        getInstance(context).show(msg, duration);
    }

    public static void showInfo(Context context, String msg) {
        getInstance(context).showInfo(msg);
    }

    public static void showConfirm(Context context, String msg) {
        getInstance(context).showConfirm(msg);
    }

    public static void showWarning(Context context, String msg) {
        getInstance(context).showWarning(msg);
    }

    public static void showSuccess(Context context, String msg) {
        getInstance(context).showSuccess(msg);
    }

    public static void showFail(Context context, String msg) {
        getInstance(context).showFail(msg);
    }

    public static void showError(Context context, String msg) {
        getInstance(context).showError(msg);
    }

    public static void showWait(Context context, String msg) {
        getInstance(context).showWait(msg);
    }

    public static void showProhibit(Context context, String msg) {
        getInstance(context).showProhibit(msg);
    }

    private void show(View view, CharSequence msg, int duration, int gravity, int xOffset, int yOffset) {
        if (mContext != null) {
            mToast.setView(view);
            mToast.setText(msg);
            mToast.setDuration(duration);
            mToast.setGravity(gravity, xOffset, yOffset);
            mToast.show();
        }
    }

    public void show(CharSequence msg) {
        show(msg, mToast.getDuration());
    }

    public void show(CharSequence msg, int duration) {
        show(msg, duration, defaultGravity);
    }

    public void show(CharSequence msg, int duration, int gravity) {
        show(msg, duration, gravity, mToast.getXOffset(), defaultYOffset);
    }

    public void show(CharSequence msg, int duration, int gravity, int xOffset, int yOffset) {
        defaultGravity = gravity;
        defaultYOffset = yOffset;
        show(mDefaultView, msg, duration, gravity, xOffset, yOffset);
    }

    public void showInfo(CharSequence msg) {
        showInfo(msg, mToast.getDuration());
    }

    public void showInfo(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_INFO, msg, duration);
    }

    public void showWarning(CharSequence msg) {
        showWarning(msg, mToast.getDuration());
    }

    public void showWarning(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_WARNING, msg, duration);
    }

    public void showSuccess(CharSequence msg) {
        showSuccess(msg, mToast.getDuration());
    }

    public void showSuccess(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_SUCCESS, msg, duration);
    }

    public void showFail(CharSequence msg) {
        showFail(msg, mToast.getDuration());
    }

    public void showFail(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_FAIL, msg, duration);
    }

    public void showError(CharSequence msg) {
        showError(msg, mToast.getDuration());
    }

    public void showError(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_ERROR, msg, duration);
    }

    public void showWait(CharSequence msg) {
        showWait(msg, mToast.getDuration());
    }

    public void showWait(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_WAIT, msg, duration);
    }

    public void showProhibit(CharSequence msg) {
        showProhibit(msg, mToast.getDuration());
    }

    public void showProhibit(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_PROHIBIT, msg, duration);
    }

    public void showConfirm(CharSequence msg) {
        showConfirm(msg, mToast.getDuration());
    }

    public void showConfirm(CharSequence msg, int duration) {
        showEasy(EASY_TYPE_CONFIRM, msg, duration);
    }

    public void showEasy(int type, CharSequence msg, int duration) {
        ImageView iv = mEasyView.findViewById(R.id.iv_easy_toast_transient_notification);
        switch (type) {
            case EASY_TYPE_PROHIBIT:
                iv.setImageResource(R.drawable.easy_toast_prohibit);
                break;
            case EASY_TYPE_WAIT:
                iv.setImageResource(R.drawable.easy_toast_wait);
                break;
            case EASY_TYPE_SUCCESS:
                iv.setImageResource(R.drawable.easy_toast_success);
                break;
            case EASY_TYPE_FAIL:
                iv.setImageResource(R.drawable.easy_toast_fail);
                break;
            case EASY_TYPE_ERROR:
                iv.setImageResource(R.drawable.easy_toast_error);
                break;
            case EASY_TYPE_WARNING:
                iv.setImageResource(R.drawable.easy_toast_warning);
                break;
            case EASY_TYPE_CONFIRM:
                iv.setImageResource(R.drawable.easy_toast_confirm);
                break;
            case EASY_TYPE_INFO:
            default:
                iv.setImageResource(R.drawable.easy_toast_info);
        }
        show(mEasyView, msg, duration, Gravity.CENTER, 0, 0);
    }
}
