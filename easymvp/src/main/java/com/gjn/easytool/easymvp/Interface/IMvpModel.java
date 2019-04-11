package com.gjn.easytool.easymvp.Interface;

import android.app.Activity;

/**
 * @author gjn
 * @time 2019/4/11 11:57
 */

public interface IMvpModel<V extends IMvpView> {
    void onBind(Activity activity, V v);

    void unBind();

    V getMvpView();

    Activity getActivity();
}
