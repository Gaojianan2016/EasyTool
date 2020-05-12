package com.gjn.easytool.easyrxevent;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    private static RxBus defaultRxBus;

    private final Subject<Object> subjectBus;

    private RxBus() {
        subjectBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getDefulat() {
        if (defaultRxBus == null) {
            synchronized (RxBus.class) {
                if (defaultRxBus == null) {
                    defaultRxBus = new RxBus();
                }
            }
        }
        return defaultRxBus;
    }

    @SuppressLint("CheckResult")
    public static <T> void getMainThread(Class<T> eventType, final OnSubscribeCallback<T> callback) {
        if (callback == null) {
            return;
        }
        getDefulat().toObservable(eventType).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onSubscribe(d);
                    }

                    @Override
                    public void onNext(T t) {
                        callback.onNext(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        callback.onComplete();
                    }
                });
    }

    public static void getStringMainThread(OnSubscribeCallback<String> callback) {
        getMainThread(String.class, callback);
    }

    public static void getRxMsgMainThread(OnSubscribeCallback<RxMsg> callback) {
        getMainThread(RxMsg.class, callback);
    }

    public static void postString(String str) {
        getDefulat().post(str);
    }

    public static void postRxMsg(int code) {
        getDefulat().post(new RxMsg(code));
    }

    public static void postRxMsg(int code, String msg) {
        getDefulat().post(new RxMsg(code, msg));
    }

    public static void postRxMsg(RxMsg rxMsg) {
        getDefulat().post(rxMsg);
    }

    public void post(Object o) {
        subjectBus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return subjectBus.ofType(eventType);
    }

    public boolean hasObservers() {
        return defaultRxBus.hasObservers();
    }
}
