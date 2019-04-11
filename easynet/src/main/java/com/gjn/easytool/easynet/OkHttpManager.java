package com.gjn.easytool.easynet;

import android.util.Log;

import com.gjn.easytool.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author gjn
 * @time 2019/4/11 15:46
 */

public class OkHttpManager {
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String TAG = "OkHttpManager";
    private static OkHttpClient okHttpClient;
    private static OkHttpManager okHttpManager;

    private OkHttpManager(Interceptor it) {
        if (it == null) {
            it = new DefaultInterceptor();
        }
        if (okHttpClient == null) {
            okHttpClient = OkHttpClientFactory.create(it);
        }
    }

    public static OkHttpManager getInstance() {
        return getInstance(null);
    }

    public static OkHttpManager getInstance(Interceptor interceptor) {
        if (okHttpManager == null) {
            synchronized (OkHttpManager.class) {
                if (okHttpManager == null) {
                    okHttpManager = new OkHttpManager(interceptor);
                }
            }
        }
        return okHttpManager;
    }

    public static void setOkHttpClient(OkHttpClient client) {
        okHttpClient = client;
    }

    public Response get(String url) throws IOException {
        return okHttpClient.newCall(url2Request(url, false)).execute();
    }

    public void getAsyn(String url, Callback callback) {
        okHttpClient.newCall(url2Request(url, false)).enqueue(callback);
    }

    public Response post(String url) throws IOException {
        return okHttpClient.newCall(url2Request(url, true)).execute();
    }

    public void postAsyn(String url, Callback callback) {
        okHttpClient.newCall(url2Request(url, true)).enqueue(callback);
    }

    public Response postJson(String url, String json) throws IOException {
        return okHttpClient.newCall(json2Request(url, json)).execute();
    }

    public void postAsynJson(String url, String json, Callback callback) {
        okHttpClient.newCall(json2Request(url, json)).enqueue(callback);
    }

    public Response postKeys(String url, Map<String, String> params) throws IOException {
        return okHttpClient.newCall(keys2Request(url, params)).execute();
    }

    public void postAsynKeys(String url, Map<String, String> params, Callback callback) {
        okHttpClient.newCall(keys2Request(url, params)).enqueue(callback);
    }

    public Response postFile(String url, File file) throws IOException {
        if (file == null || !file.exists()) {
            Log.e(TAG, "file is null.");
            return null;
        }
        return okHttpClient.newCall(file2Request(url, file)).execute();
    }

    public void postAsynFile(String url, File file, Callback callback) {
        if (file == null || !file.exists()) {
            Log.e(TAG, "file is null.");
            return;
        }
        okHttpClient.newCall(file2Request(url, file)).enqueue(callback);
    }

    private Request url2Request(String url, boolean isPost) {
        if (isPost) {
            return new Request.Builder()
                    .post(new FormBody.Builder().build())
                    .url(url)
                    .build();
        } else {
            return new Request.Builder()
                    .url(url)
                    .build();
        }
    }

    private Request json2Request(String url, String json) {
        json = json == null ? "" : json;
        return new Request.Builder()
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .url(url)
                .build();
    }

    private Request keys2Request(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        return new Request.Builder()
                .post(builder.build())
                .url(url)
                .build();
    }

    private Request file2Request(String url, File file) {
        String type = FileUtils.getTypeFromSuffix(file);
        if (type == null || type.isEmpty()) {
            type = "text/x-markdown";
        }
        return new Request.Builder()
                .post(RequestBody.create(MediaType.parse(type + "; charset=utf-8"), file))
                .url(url)
                .build();
    }
}
