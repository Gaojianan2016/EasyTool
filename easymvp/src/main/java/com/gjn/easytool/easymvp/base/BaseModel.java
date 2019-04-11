package com.gjn.easytool.easymvp.base;

import android.app.Activity;

import com.gjn.easytool.easymvp.Interface.IMvpModel;
import com.gjn.easytool.easymvp.Interface.IMvpView;

/**
 * @author gjn
 * @time 2019/4/11 12:03
 */

public class BaseModel<V extends IMvpView> implements IMvpModel<V> {

    private Activity activity;
    private V v;

    public BaseModel() {
    }

    @Override
    public void onBind(Activity activity, V v) {
        this.activity = activity;
        this.v = v;
    }

    @Override
    public void unBind() {
        activity = null;
        v = null;
    }

    @Override
    public V getMvpView() {
        return v;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }
}
