package com.gjn.easytool.easynet;

import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author gjn
 * @time 2019/4/12 10:05
 */

public class DownLoadManager {
    private static final String TAG = "DownLoadManager";

    private static final String LENGHT = "Content-Length";

    public static final String SDCARD = "/sdcard/";

    private static OkHttpClient okHttpClient;
    private static DownLoadManager downLoadManager;
    private Call call;

    private DownLoadManager(){
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
    }

    public static DownLoadManager getInstance() {
        if (downLoadManager == null) {
            synchronized (OkHttpManager.class) {
                if (downLoadManager == null) {
                    downLoadManager = new DownLoadManager();
                }
            }
        }
        return downLoadManager;
    }

    public static void setOkHttpClient(OkHttpClient client){
        okHttpClient = client;
    }

    public void download(String url, final OnDownLoadListener listener){
        String path = SDCARD;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getPath() + "/";
        }
        download(url, path, listener);
    }

    public void download(String url, String path, final OnDownLoadListener listener){
        download(url, path, null, listener);
    }

    public void download(String url, String path, String name, final OnDownLoadListener listener){
        if (TextUtils.isEmpty(name)) {
            Uri uri = Uri.parse(url);
            name = uri.getLastPathSegment();
        }
        File filePath = new File(path);
        final File file = new File(path, name);
        try {
            if (!filePath.exists()) {
                if (filePath.mkdirs()) {
                    EasyLog.i(TAG, "create filePath " + filePath.getPath());
                }
            }
            if (file.exists()) {
                if (file.delete()) {
                    EasyLog.i(TAG, "delete old file " + file.getName());
                }
            }
            if (file.createNewFile()) {
                EasyLog.i(TAG, "create new file " + file.getName());
            }
        }catch (Exception e){
            listener.error(e);
        }
        EasyLog.i(TAG, "download start " + url);

        call = okHttpClient.newCall(new Request.Builder().url(url).build());
        call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        EasyLog.w(TAG, "download file fail. " + e.getMessage());
                        listener.fail();
                        listener.error(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (downloadStream(response, file, listener)) {
                            listener.success(file);
                        }else {
                            listener.fail();
                        }
                    }
                });
    }

    public void downloadCancel(){
        if (call != null) {
            call.cancel();
        }
    }

    private boolean downloadStream(Response response, File file, OnDownLoadListener listener) {
        int lenght = Integer.parseInt(response.header(LENGHT));
        InputStream is = null;
        FileOutputStream fos = null;
        byte[] buf = new byte[setStreamBtye(lenght)];
        int len;
        int readStream = 0;
        listener.start(file, file.getName(), lenght);
        try {
            EasyLog.i(TAG, "stream start write file. lenght = " + lenght + "->" + StringUtils.getSizeStr(lenght));
            is = response.body().byteStream();
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                readStream += len;
                listener.loading(readStream);
            }
            fos.flush();
            listener.end();
            EasyLog.i(TAG, "stream write file success.");
            return true;
        }catch (Exception e){
            EasyLog.w(TAG, "stream write file error. " +e.getMessage());
            listener.error(e);
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        listener.end();
        EasyLog.i(TAG, "stream write file fail.");
        return false;
    }

    private int setStreamBtye(int lenght) {
        int result;
        if (lenght > 1024 * 1024 * 1024) {
            result = 1024 * 1024 * 50;
        } else if(lenght > 1024 * 1024 * 512){
            result = 1024 * 1024 * 30;
        } else if(lenght > 1024 * 1024 * 100){
            result = 1024 * 1024 * 10;
        } else if(lenght > 1024 * 1024 * 10){
            result = 1024 * 1024;
        } else if(lenght > 1024 * 1024){
            result = 1024 * 512;
        } else{
            result = 1024;
        }
        EasyLog.i(TAG, "readBuff " + StringUtils.getSizeStr(result));
        return result;
    }

    public static abstract class OnDownLoadListener {
        public void start(File file, String name, int lenght){
        }

        public void loading(int readStream){
        }

        public void success(File file){
        }

        public void fail(){
        }

        public void error(Throwable tr){
        }

        public void end(){
        }
    }
}
