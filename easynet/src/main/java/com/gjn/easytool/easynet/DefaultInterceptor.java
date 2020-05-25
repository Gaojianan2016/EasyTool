package com.gjn.easytool.easynet;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.utils.JsonUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author gjn
 * @time 2019/4/11 15:47
 */

public class DefaultInterceptor implements Interceptor {
    private static final String TAG = "DefaultInterceptor";

    public static boolean isDebug = false;

    private OnHttpHeadersListener onHttpHeadersListener;

    public DefaultInterceptor() {
    }

    public DefaultInterceptor(OnHttpHeadersListener onHttpHeadersListener) {
        this.onHttpHeadersListener = onHttpHeadersListener;
    }

    public void setOnHttpHeadersListener(OnHttpHeadersListener onHttpHeadersListener) {
        this.onHttpHeadersListener = onHttpHeadersListener;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (onHttpHeadersListener != null) {
            Map<String, String> oldHeads = new HashMap<>();
            for (int i = 0; i < request.headers().size(); i++) {
                oldHeads.put(request.headers().name(i), request.headers().value(i));
            }
            Map<String, String> heads = onHttpHeadersListener.addRequestHeaders(String.valueOf(request.url()), oldHeads);
            if (heads != null && heads.size() > 0) {
                Request.Builder builder = request.newBuilder()
                        .method(request.method(), request.body());
                for (Map.Entry<String, String> entry : heads.entrySet()) {
                    builder.header(entry.getKey(), entry.getValue());
                }
                request = builder.build();
            }
        }
        long startTime = System.nanoTime();
        Response response = chain.proceed(request);
        if (response.headers().size() > 0) {
            if (onHttpHeadersListener != null) {
                onHttpHeadersListener.getResponseHeader(String.valueOf(response.request().url()), response.headers());
            }
        }
        log(printRequest(request) + "\n" + printResponse(response, startTime));
        log(request.url() + "\n" + printResponseBody(response));
        return response;
    }

    private String printRequest(Request request) throws IOException {
        StringBuilder log = new StringBuilder();
        log.append(EasyLog.HEAD).append('\n').append(EasyLog.BODY + "--> ")
                .append(request.method()).append(" ").append(request.url());
        Headers headers = request.headers();
        if (headers.size() > 0) {
            log.append('\n').append(EasyLog.BODY + "RequestHeaders ");
            for (int i = 0; i < headers.size(); i++) {
                log.append('\n').append(EasyLog.BODY).append(headers.name(i)).append(" : ").append(headers.value(i));
            }
        }
        RequestBody body = request.body();
        if (body != null) {
            log.append('\n').append(EasyLog.BODY + "BodyType = ").append(body.contentType());
            log.append('\n').append(EasyLog.BODY + "BodyLenght = ").append(body.contentLength());
            if (body instanceof FormBody) {
                log.append('\n').append(EasyLog.BODY + "Post Key Value ");
                for (int i = 0; i < ((FormBody) body).size(); i++) {
                    log.append('\n').append(EasyLog.BODY).append(((FormBody) body)
                            .encodedName(i)).append(" = ").append(((FormBody) body).encodedValue(i));
                }
            }else {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                Charset charset = StandardCharsets.UTF_8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(StandardCharsets.UTF_8);
                }
                if (isPlaintext(buffer)) {
                    log.append('\n').append(EasyLog.BODY).append(buffer.readString(charset));
                }
            }
        }
        log.append('\n').append(EasyLog.BODY).append(EasyLog.LINE);
        return log.toString();
    }

    private String printResponse(Response response, long startTime) {
        StringBuilder log = new StringBuilder();
        long endTime = System.nanoTime();
        log.append(String.format(EasyLog.BODY + "--> %s : %.1fms", response.request().url(), (endTime - startTime) / 1e6d));
        log.append('\n').append(EasyLog.BODY + "--> HttpCode = ").append(response.code());
        Headers headers = response.headers();
        if (headers.size() > 0) {
            log.append('\n').append(EasyLog.BODY + "ResponseHeaders ");
            for (int i = 0; i < headers.size(); i++) {
                log.append('\n').append(EasyLog.BODY).append(headers.name(i)).append(" : ").append(headers.value(i));
            }
        }
        log.append('\n').append(EasyLog.FOOT);
        return log.toString();
    }

    private String printResponseBody(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset() == null ? StandardCharsets.UTF_8 : contentType.charset();
        }
        String result = buffer.clone().readString(charset);
        if (result.startsWith("{\"") || result.startsWith("[{")) {
            return JsonUtils.formatString(result);
        } else {
            return result;
        }
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }

    private void log(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public interface OnHttpHeadersListener {
        Map<String, String> addRequestHeaders(String url, Map<String, String> headers);

        void getResponseHeader(String url, Headers headers);
    }
}
