package com.gjn.easytool.easynet;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author gjn
 * @time 2019/4/11 16:29
 */

public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public RetrofitManager(Interceptor it) {
        if (it == null) {
            it = new DefaultInterceptor();
        }
        if (okHttpClient == null) {
            okHttpClient = OkHttpClientFactory.create(it);
        }
    }

    public static RetrofitManager getInstance() {
        return getInstance(null);
    }

    public static RetrofitManager getInstance(Interceptor interceptor) {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager(interceptor);
                }
            }
        }
        return retrofitManager;
    }

    public static void setRetrofit(Retrofit r) {
        retrofit = r;
    }

    public static void setOkHttpClient(OkHttpClient client) {
        okHttpClient = client;
    }

    public static <T> T create(String url, Class<T> service) {
        return getInstance().url(url).create(service);
    }

    public static <T> T create2(String url, Class<T> service) {
        return getInstance().url2(url).create(service);
    }

    public static <T> void linkOnMainThread(Observable<T> observable, Consumer<T> onNext) {
        linkOnMainThread(observable, onNext, Functions.ON_ERROR_MISSING);
    }

    public static <T> void linkOnMainThread(Observable<T> observable, Consumer<T> onNext,
                                            Consumer<? super Throwable> onError) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public RetrofitManager url(String url) {
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        return this;
    }

    public RetrofitManager url2(String url) {
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return this;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
