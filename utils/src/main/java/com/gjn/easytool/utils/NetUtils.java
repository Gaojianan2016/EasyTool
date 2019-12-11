package com.gjn.easytool.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Creator: Gjn
 * Time: 2019/12/11 12:55
 * Eamil: 732879625@qq.com
 **/
public class NetUtils {
    /**
     * 静态方法获取是否有网络连接
     *
     * @param context 上下文
     * @return 是否连接
     */
    public static boolean hasNet(Context context) {
        //获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();

        NetworkInfo.State wifiState = NetworkInfo.State.UNKNOWN;
        NetworkInfo.State mobileState = NetworkInfo.State.UNKNOWN;

        if (wifiNetworkInfo != null) {
            wifiState = wifiNetworkInfo.getState();
        }
        if (mobileNetworkInfo != null) {
            mobileState = mobileNetworkInfo.getState();
        }
        if (NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
            return false;
        }
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    /**
     * 静态判断是不是4G网络
     *
     * @param context 上下文
     * @return 是否是4G
     */
    public static boolean is4GConnected(Context context) {
        //获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        NetworkInfo.State mobileState = NetworkInfo.State.UNKNOWN;

        if (mobileNetworkInfo != null) {
            mobileState = mobileNetworkInfo.getState();
        }
        return NetworkInfo.State.CONNECTED == mobileState;
    }
}
