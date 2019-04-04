package com.gjn.easytool.utils;

import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;

/**
 * @author gjn
 * @time 2019/4/3 11:05
 */

public class ResourcesUtils {

    public static int getInternalId(String name){
        return getInternal(name, "id");
    }

    public static int getInternalLayout(String name){
        return getInternal(name, "layout");
    }

    public static int getInternalDrawable(String name){
        return getInternal(name, "drawable");
    }

    public static int getInternal(String name, String type){
        return Resources.getSystem().getIdentifier(name, type, "android");
    }

}
