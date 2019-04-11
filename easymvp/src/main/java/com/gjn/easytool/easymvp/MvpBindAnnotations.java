package com.gjn.easytool.easymvp;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gjn.easytool.easymvp.Interface.IMvpView;
import com.gjn.easytool.easymvp.base.BasePresenter;
import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.utils.ReflexUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gjn
 * @time 2019/4/11 13:56
 */

public class MvpBindAnnotations {
    private static final String TAG = "MvpBindAnnotations";

    private Activity activity;
    private Fragment fragment;
    private Object object;
    private Map<String, BasePresenter> presenterMap;
    private List<BasePresenter> presenters;

    private MvpBindAnnotations(Activity activity, Fragment fragment) {
        if (activity != null) {
            this.activity = activity;
            object = activity;
        }
        if (fragment != null) {
            this.fragment = fragment;
            object = fragment;
            EasyLog.d(TAG, "bind fragment.");
        } else {
            EasyLog.d(TAG, "bind activity.");
        }
        if (presenterMap == null) {
            presenterMap = new HashMap<>();
        }
        if (presenters == null) {
            presenters = new ArrayList<>();
        }
        savePresenters();
        bindPresenter();
        attachedPresenter();
    }

    public static MvpBindAnnotations newInstance(Activity activity) {
        return newInstance(activity, null);
    }

    public static MvpBindAnnotations newInstance(Activity activity, Fragment fragment) {
        return new MvpBindAnnotations(activity, fragment);
    }

    private void savePresenters() {
        BindPresenters bps = AnnotationsUtils.getAnnotations(object, BindPresenters.class);
        if (bps != null) {
            EasyLog.d(TAG, "save Annotations:");
            for (Class<?> clz : bps.value()) {
                addBP(clz.getCanonicalName(), (BasePresenter) ReflexUtils.createObj(clz));
            }
        } else {
            Type type = object.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                EasyLog.d(TAG, "save Generic:");
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                for (Type t : types) {
                    Class clz = (Class) t;
                    addBP(clz.getCanonicalName(), (BasePresenter) ReflexUtils.createObj(clz));
                }
            }
        }
    }

    private void bindPresenter() {
        List<Field> fields = AnnotationsUtils.getField(object, BindPresenter.class);
        for (Field field : fields) {
            String name = field.getType().getName();
            BasePresenter bp = presenterMap.get(name);
            if (bp != null) {
                ReflexUtils.setField(object, field, bp);
            }
        }
    }

    private void attachedPresenter() {
        for (BasePresenter presenter : getPresenters()) {
            if (presenter != null) {
                presenter.onAttached(activity, (IMvpView) (fragment != null ? fragment : activity));
            }
        }
    }

    private void addBP(String name, BasePresenter bp) {
        presenterMap.put(name, bp);
        presenters.add(bp);
        EasyLog.d(TAG, "┗━━" + bp.getClass().getSimpleName());
    }

    public void detachedPresenter() {
        for (BasePresenter presenter : getPresenters()) {
            if (presenter != null) {
                presenter.onDetached();
            }
        }
    }

    public int getPresentersCount() {
        return presenters.size();
    }

    public List<BasePresenter> getPresenters() {
        return presenters;
    }

    public <P extends BasePresenter> P getPresenterItem() {
        return getPresenterItem(0);
    }

    public <P extends BasePresenter> P getPresenterItem(int i) {
        if (getPresentersCount() == 0) {
            return null;
        } else if (i > getPresentersCount() - 1) {
            i = getPresentersCount() - 1;
        } else if (i < 0) {
            i = 0;
        }
        return (P) presenters.get(i);
    }
}