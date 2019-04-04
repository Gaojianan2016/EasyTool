package com.gjn.easytool.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gjn
 * @time 2019/1/24 16:29
 */

public class QRUtils {

    public static BitMatrix encode(String code)
            throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        return encode(code, 300, 300, hints);
    }

    public static BitMatrix encode(String code, int w, int h, Map<EncodeHintType, Object> hints)
            throws WriterException {
        return new QRCodeWriter().encode(code, BarcodeFormat.QR_CODE, w, h, hints);
    }

    public static String decode(BinaryBitmap binaryBitmap)
            throws FormatException, ChecksumException, NotFoundException {
        Map<DecodeHintType, Object> hints = new HashMap();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        return decode(binaryBitmap, hints);
    }

    public static String decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, Object> hints)
            throws FormatException, ChecksumException, NotFoundException {
        return new QRCodeReader().decode(binaryBitmap, hints).getText();
    }

    public static Bitmap bitMatrix2bitmap(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        //将BitMatrix的像素保存下来
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y * width + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
            }
        }
        //创建一样大小的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //将BitMatrix的像素绘制到Bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static BinaryBitmap bitmap2binaryBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        //获取像素
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        return new BinaryBitmap(new HybridBinarizer(source));
    }

    public static Bitmap str2bitmap(String str) throws WriterException {
        return bitMatrix2bitmap(encode(str));
    }

    public static String bitmap2str(Bitmap bitmap) throws FormatException, ChecksumException, NotFoundException {
        return decode(bitmap2binaryBitmap(bitmap));
    }
}
