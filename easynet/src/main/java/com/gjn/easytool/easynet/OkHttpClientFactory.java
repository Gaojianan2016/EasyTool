package com.gjn.easytool.easynet;

import com.gjn.easytool.utils.HttpsUtils;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author gjn
 * @time 2019/4/11 15:47
 */

public class OkHttpClientFactory {

    public static OkHttpClient create() {
        return create(null);
    }

    public static OkHttpClient create(Interceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    public static OkHttpClient createHttps(){
        return createHttps(null);
    }

    public static OkHttpClient createHttps(Interceptor interceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        builder
                //创建一个证书对象
                .sslSocketFactory(HttpsUtils.getInstance().createSSLSocketFactory(),
                        HttpsUtils.getInstance().getTrustManager())
                //校验名称
                .hostnameVerifier(HttpsUtils.getInstance().getHostnameVerifier());
        return builder.build();
    }
}
