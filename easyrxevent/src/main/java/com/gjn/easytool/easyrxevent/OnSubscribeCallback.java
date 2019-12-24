package com.gjn.easytool.easyrxevent;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Creator: Gjn
 * Time: 2019/12/24 12:12
 * Eamil: 732879625@qq.com
 **/
public abstract class OnSubscribeCallback<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }
}
