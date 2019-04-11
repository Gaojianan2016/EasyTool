package com.gjn.easytool.easymvp.Interface;

import android.app.Activity;

/**
 * @author gjn
 * @time 2019/4/11 11:57
 */

public interface IMvpPresenter<V extends IMvpView, M extends IMvpModel<V>> {
    void onAttached(Activity activity, V view);

    void onDetached();

    boolean isAttached();

    Activity getActivity();

    M getModel();

    V getMvpView();
}
