package com.gjn.easytool.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author gjn
 * @time 2018/5/9 11:36
 */
public class CacheUtlis {

    public static void clearAllCache(Context context){
        cleanInternalCache (context);
        cleanExternalCache (context);
        clearDatabases (context);
        clearSharedPrefs(context);
        clearFiles(context);
    }

    /**
     * 删除内外部cache和file
     */
    public static void clearCache(Context context){
        cleanInternalCache (context);
        cleanExternalCache (context);
    }

    /**
     * 删除内外部cache和file
     */
    public static void clearCache2(Context context){
        cleanInternalCache (context);
        cleanExternalCache (context);
        clearFiles(context);
    }

    /**
     * 清除外部缓存 /mnt/sdcard/android/data/com.xxx.xxx/cache
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.deleteFile(context.getExternalCacheDir());
        }
    }

    /**
     * 清除内部缓存 /data/data/com.xxx.xxx/cache
     */
    public static void cleanInternalCache(Context context) {
        FileUtils.deleteFile(context.getCacheDir());
    }

    /**
     * 清除File文件 /data/data/com.xxx.xxx/files
     */
    public static void clearFiles(Context context) {
        FileUtils.deleteFile(context.getFilesDir());
    }

    /**
     * 清除SharedPreference /data/data/com.xxx.xxx/shared_prefs
     */
    public static void clearSharedPrefs(Context context) {
        FileUtils.deleteFile(new File("/data/data/"+context.getPackageName()+"/shared_prefs"));
    }

    /**
     * 清除数据库 /data/data/com.xxx.xxx/databases
     */
    public static void clearDatabases(Context context) {
        FileUtils.deleteFile(new File("/data/data/"+context.getPackageName()+"/databases"));
    }

    public static String getCacheSizeStr(Context context){
        return StringUtils.getSizeStr(getCacheSize(context));
    }

    public static long getCacheSize(Context context){
        long externalCacheSize = getExternalCacheSize(context);
        long internalCacheSize = getInternalCacheSize(context);
        return externalCacheSize + internalCacheSize;
    }

    public static String getCacheSizeStr2(Context context){
        return StringUtils.getSizeStr(getCacheSize2(context));
    }

    public static long getCacheSize2(Context context){
        long externalCacheSize = getExternalCacheSize(context);
        long internalCacheSize = getInternalCacheSize(context);
        long filesCacheSize = getFilesCacheSize(context);
        return externalCacheSize + internalCacheSize + filesCacheSize;
    }


    public static long getFilesCacheSize(Context context) {
        return FileUtils.getFileSize(context.getFilesDir());
    }

    public static long getInternalCacheSize(Context context) {
        return FileUtils.getFileSize(context.getCacheDir());
    }

    public static long getExternalCacheSize(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return FileUtils.getFileSize(context.getExternalCacheDir());
        }
        return 0;
    }

}
