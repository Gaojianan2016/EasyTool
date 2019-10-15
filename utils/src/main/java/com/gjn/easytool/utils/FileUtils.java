package com.gjn.easytool.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.regex.Pattern;

/**
 * FileUtils
 * Author: gjn.
 * Time: 2018/4/12.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    private static final String FILEPROVIDER = ".fileprovider";

    public static void openFile(Context context, String filePath) {
        File file = new File(filePath);
        openFile(context, file);
    }

    public static void openFile(Context context, File file) {
        String mimeType = getTypeFromSuffix(file);
        Uri uri = Uri.fromFile(file);
        if (mimeType != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, mimeType);
            context.startActivity(intent);
        }
    }

    public static void openFileApi24(Context context, String filePath) {
        File file = new File(filePath);
        openFileApi24(context, file);
    }

    public static void openFileApi24(Context context, File file) {
        String mimeType = getTypeFromSuffix(file);
        Uri uri = Uri.fromFile(file);
        if (mimeType != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + FILEPROVIDER, file);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setDataAndType(uri, mimeType);
            context.startActivity(intent);
        }
    }

    public static boolean isFileName(String value){
        Pattern pattern = Pattern.compile(".+[.]\\D.+");
        return pattern.matcher(value).find();
    }

    public static String getSuffix(File file) {
        return getSuffix(file.getName());
    }

    public static String getSuffix(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getTypeFromSuffix(File file) {
        return getTypeFromSuffix(getSuffix(file));
    }

    public static String getTypeFromSuffix(String suffix) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
    }

    public static String getSuffixFromType(String type) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(type);
    }

    public static void deleteFile(File directory) {
        if (directory != null && directory.exists()) {
            if (directory.isDirectory()) {
                String[] children = directory.list();
                for (String child : children) {
                    deleteFile(new File(directory, child));
                }
            }
            directory.delete();
        }
    }

    public static void deleteFile(String directoryPath) {
        File directory = new File(directoryPath);
        deleteFile(directory);
    }

    public static long getFileSize(String directoryPath) {
        File directory = new File(directoryPath);
        return getFileSize(directory);
    }

    public static long getFileSize(File directory) {
        long size = 0;
        try {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    size += getFileSize(file);
                } else {
                    size += file.length();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "get file size error", e);
        }
        return size;
    }
}
