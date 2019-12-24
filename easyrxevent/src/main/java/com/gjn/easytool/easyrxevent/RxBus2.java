package com.gjn.easytool.easyrxevent;

import android.annotation.SuppressLint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Creator: Gjn
 * Time: 2019/12/24 11:11
 * Eamil: 732879625@qq.com
 **/
public class RxBus2 {

    private final Subject<Object> bus;
    private final Map<Class<?>, Object> mStickyEventMap;

    private RxBus2() {
        bus = PublishSubject.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus2 getInstance() {
        return Holder.BUS;
    }

    @SuppressLint("CheckResult")
    public static <T> void getMainThread(Class<T> eventType, final OnSubscribeCallback<T> callback) {
        if (callback == null) {
            return;
        }
        getInstance().toObservable(eventType)
                .observeOn(AndroidSchedulers.mainThread())
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

    public static void getStringMainThread(final OnSubscribeCallback<String> callback) {
        getMainThread(String.class, callback);
    }

    public static void getRxMsgMainThread(final OnSubscribeCallback<RxMsg> callback) {
        getMainThread(RxMsg.class, callback);
    }

    public static void postString(String str) {
        getInstance().post(str);
    }

    public static void postRxMsg(int code) {
        getInstance().post(new RxMsg(code));
    }

    public static void postRxMsg(int code, String msg) {
        getInstance().post(new RxMsg(code, msg));
    }

    public static void postRxMsg(RxMsg rxMsg) {
        getInstance().post(rxMsg);
    }

    public void post(Object obj) {
        bus.onNext(obj);
    }

    public void postSticky(Object obj) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(obj.getClass(), obj);
        }
        post(obj);
    }

    /**
     * 传递粘性事件
     */
    public <T> Observable<T> toObservableSticky(final Class<T> tClass) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = toObservable(tClass);
            final Object event = mStickyEventMap.get(tClass);
            if (event != null) {
                return observable.mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                        subscriber.onNext(tClass.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return bus.ofType(tClass);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    private static class Holder {
        private static final RxBus2 BUS = new RxBus2();
    }
}
