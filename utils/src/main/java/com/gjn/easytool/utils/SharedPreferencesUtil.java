package com.gjn.easytool.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * @author gjn
 * @time 2019/4/4 9:58
 */

public class SharedPreferencesUtil {
    private static SharedPreferences mSharedPreferences;

    //请在Application中初始化
    public static void create(Context context, String name){
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void setString(String key, String value){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(String key){
        return mSharedPreferences.getString(key,"");
    }

    public static void setStringSet(String key, Set<String> set){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putStringSet(key, set);
        edit.commit();
    }

    public static Set<String> getStringSet(String key){
        return mSharedPreferences.getStringSet(key,null);
    }

    public static void setBoolean(String key, boolean b){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(key, b);
        edit.commit();
    }

    public static boolean getBoolean(String key){
        return mSharedPreferences.getBoolean(key,false);
    }

    public static void setInt(String key, int i){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(key, i);
        edit.commit();
    }

    public static int getInt(String key){
        return mSharedPreferences.getInt(key,0);
    }

    public static void setFloat(String key, float f){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putFloat(key, f);
        edit.commit();
    }

    public static float getFloat(String key){
        return mSharedPreferences.getFloat(key,0);
    }

    public static void saveLong(String key, long l){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putLong(key, l);
        edit.commit();
    }

    public static long getLong(String key){
        return mSharedPreferences.getLong(key,0);
    }
}
