package com.gjn.easytool.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * FileUtils
 * Author: gjn.
 * Time: 2018/4/12.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    private static final String FILEPROVIDER = ".fileprovider";

    private static final String APP = "/app";

    public static boolean hasFile(String filePath){
        return new File(filePath).exists();
    }

    public static void openFile(Context context, String filePath) {
        File file = new File(filePath);
        openFile(context, file);
    }

    public static void openFile(Context context, File file) {
        String mimeType = getTypeFromSuffix(file);
        Uri uri = getFileUri(context, file);
        if (mimeType != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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

    public static Uri getFileUri(Context context, String filePath) {
        return getFileUri(context, new File(filePath));
    }

    public static Uri getFileUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getPackageName() + FILEPROVIDER, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static File getFileFromUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case ContentResolver.SCHEME_CONTENT:
                return getFileFormContentUri(context, uri);
            case ContentResolver.SCHEME_FILE:
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    public static String getFileFromUriPath(Context context, Uri uri) {
        File file = getFileFromUri(context, uri);
        if (file != null) {
            return file.getPath();
        }
        return "";
    }

    public static File getFileFormContentUri(Context context, Uri uri) {
        File file = null;
        String filePath = "";
        if (uri.getAuthority().contains(context.getPackageName())) {
            filePath = uri.getPath().replace(APP, "");
        } else {
            String[] projection = new String[]{MediaStore.Video.Media.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, projection, null, null, null);
            if (cursor == null) {
                Log.w(TAG, "query is null.");
                return null;
            }
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(projection[0]);
                if (index != -1) {
                    filePath = cursor.getString(index);
                }
            }
            cursor.close();
        }
        if (!TextUtils.isEmpty(filePath)) {
            file = new File(filePath);
        }
        return file;
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

    public static void unZip(Context context, String assetName, String outputDirectory)
            throws IOException {
        // 创建解压目标目录
        File file = new File(outputDirectory);
        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 打开压缩文件
        InputStream inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        // 使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        // 解压时字节计数
        int count = 0;
        // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            // 如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者是文件不存在
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                // 如果是文件
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者文件不存在，则解压文件
                if (!file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }
}
