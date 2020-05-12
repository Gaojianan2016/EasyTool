package com.gjn.easytool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author gjn
 * @time 2019/4/3 15:06
 */

public class ViewUtils {

    public static void removeParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }

    public static Bitmap getImageViewBitmap(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static String getTextViewText(TextView textView){
        return textView.getText().toString();
    }

    public static int getViewWidth(View view){
        int w = view.getWidth();
        if (w == 0) {
            view.measure(0, 0);
            w = view.getMeasuredWidth();
        }
        return w;
    }

    public static int getViewHeight(View view){
        int h = view.getHeight();
        if (h == 0) {
            view.measure(0, 0);
            h = view.getMeasuredHeight();
        }
        return h;
    }

    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

//    public static void setRefreshing(SwipeRefreshLayout srl, boolean refreshing){
//        if (srl != null) {
//            srl.setRefreshing(refreshing);
//        }
//    }
}
