package com.gjn.easytool.easymvp.base;

import android.app.Activity;

import com.gjn.easytool.easymvp.Interface.IMvpModel;
import com.gjn.easytool.easymvp.Interface.IMvpPresenter;
import com.gjn.easytool.easymvp.Interface.IMvpView;
import com.gjn.easytool.utils.ReflexUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author gjn
 * @time 2019/4/11 12:01
 */

public class BasePresenter<V extends IMvpView, M extends IMvpModel<V>> implements IMvpPresenter<V, M> {
    private V v;
    private M m;
    private Activity activity;

    public BasePresenter() {
    }

    @Override
    public void onAttached(Activity activity, V view) {
        this.activity = activity;
        v = view;
        m = create();
        if (m != null) {
            m.onBind(activity, v);
        }
    }

    private M create() {
        M m = null;
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            m = ReflexUtils.createObj((Class<M>) types[1]);
        }
        return m;
    }

    @Override
    public void onDetached() {
        if (m != null) {
            m.unBind();
        }
        activity = null;
        m = null;
        v = null;
    }

    @Override
    public boolean isAttached() {
        return v != null && m != null;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public M getModel() {
        return m;
    }

    @Override
    public V getMvpView() {
        return v;
    }
}
