package com.gjn.easytool.easyrxevent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
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

    public static <T> void getMainThread(Class<T> eventType,
                                         Consumer<? super T> onNext) {
        getMainThread(eventType, onNext, Functions.ON_ERROR_MISSING);
    }

    public static <T> void getMainThread(Class<T> eventType, Consumer<? super T> onNext,
                                         Consumer<? super Throwable> onError) {
        getDefulat().toObservable(eventType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public static void postString(String str) {
        getDefulat().post(str);
    }

    public static void getStringMainThread(Consumer<String> onNext) {
        getStringMainThread(onNext, Functions.ON_ERROR_MISSING);
    }

    public static void getStringMainThread(Consumer<String> onNext, Consumer<? super Throwable> onError) {
        getMainThread(String.class, onNext, onError);
    }

    public static void postRxMsg(int code) {
        getDefulat().post(new RxMsg(code));
    }

    public static void postRxMsg(int code, String msg) {
        getDefulat().post(new RxMsg(code, msg));
    }

    public static void getRxMsgMainThread(Consumer<RxMsg> onNext) {
        getRxMsgMainThread(onNext, Functions.ON_ERROR_MISSING);
    }

    public static void getRxMsgMainThread(Consumer<RxMsg> onNext, Consumer<? super Throwable> onError) {
        getMainThread(RxMsg.class, onNext, onError);
    }

    public void post(Object o) {
        subjectBus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return subjectBus.ofType(eventType);
    }

}
