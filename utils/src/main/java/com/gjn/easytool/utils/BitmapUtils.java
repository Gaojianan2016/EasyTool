package com.gjn.easytool.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author gjn
 * @time 2019/4/3 17:42
 */

public class BitmapUtils {
    public static byte[] compress2byte(Bitmap bitmap, int size){
        return bitmap2byte(bitmap, size);
    }

    public static byte[] compress2byte(File file, int size){
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap2byte(bitmap, size);
    }

    public static Bitmap compress2Bitmap(File file, int size){
        return byte2bitmap(compress2byte(file, size));
    }

    public static byte[] bitmap2byte(Bitmap bitmap){
        return bitmap2byte(bitmap, 100);
    }

    public static byte[] bitmap2byte(Bitmap bitmap, int quality){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }

    public static Bitmap byte2bitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap sample(File file, int w){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
        int bitmapW = options.outWidth;
        int sampleSize = 2;
        if (bitmapW > w) {
            sampleSize = bitmapW / w;
            if (sampleSize <= 1) {
                sampleSize = 2;
            }
        }
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file.getPath(), options);
        return bitmap;
    }
}
