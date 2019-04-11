package com.gjn.easytool.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public static int getInternalColors(String name){
        return getInternal(name, "colors");
    }

    public static int getInternal(String name, String type){
        return Resources.getSystem().getIdentifier(name, type, "android");
    }

    public static View getView(Activity activity, int resource, ViewGroup root){
        return getView(activity, resource, root, root != null);
    }

    public static View getView(Activity activity, int resource, ViewGroup root, boolean attachToRoot){
        return LayoutInflater.from(activity).inflate(resource, root, attachToRoot);
    }

    public static View getView(LayoutInflater inflater, int resource, ViewGroup root){
        return inflater.inflate(resource, root, root != null);
    }

    public static View getView(LayoutInflater inflater, int resource, ViewGroup root, boolean attachToRoot){
        return inflater.inflate(resource, root, attachToRoot);
    }

}
