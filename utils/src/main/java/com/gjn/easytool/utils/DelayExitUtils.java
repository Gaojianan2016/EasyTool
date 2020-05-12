package com.gjn.easytool.utils;

import android.view.KeyEvent;

/**
 * Creator: Gjn
 * Time: 2019/11/7 11:33
 * Eamil: 732879625@qq.com
 **/
public class DelayExitUtils {
    private static final long BACKTIME = 2000;

    private long oldTime = 0;
    private long backTime = BACKTIME;

    private OnDelayExitListener onDelayExitListener;

    private OnDelayTipListener onDelayTipListener;

    public DelayExitUtils(long backTime) {
        this.backTime = backTime;
    }

    public DelayExitUtils(OnDelayTipListener onDelayTipListener) {
        this.onDelayTipListener = onDelayTipListener;
    }

    public DelayExitUtils(long backTime, OnDelayExitListener onDelayExitListener, OnDelayTipListener onDelayTipListener) {
        this.backTime = backTime;
        this.onDelayExitListener = onDelayExitListener;
        this.onDelayTipListener = onDelayTipListener;
    }

    public void setOnDelayExitListener(OnDelayExitListener onDelayExitListener) {
        this.onDelayExitListener = onDelayExitListener;
    }

    public void setOnDelayTipListener(OnDelayTipListener onDelayTipListener) {
        this.onDelayTipListener = onDelayTipListener;
    }

    public void setBackTime(long backTime){
        this.backTime = backTime;
    }

    public boolean delayExit(int keyCode){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - oldTime > backTime) {
                oldTime = System.currentTimeMillis();
                if (onDelayTipListener != null) {
                    onDelayTipListener.delayTip();
                }
            } else {
                if (onDelayExitListener != null) {
                    onDelayExitListener.exit();
                }else {
                    //完全退出
                    System.exit(0);
                }
            }
            return true;
        }
        return false;
    }

    public interface OnDelayExitListener{
        void exit();
    }

    public interface OnDelayTipListener{
        void delayTip();
    }
}
