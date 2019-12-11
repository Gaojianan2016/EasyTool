package com.gjn.easytool.easymvp.base;

import com.gjn.easytool.easymvp.Interface.IMvpView;

/**
 * Creator: Gjn
 * Time: 2019/12/11 11:37
 * Eamil: 732879625@qq.com
 **/
public class BaseView<V extends IMvpView> implements IMvpView {

    private V v;

    public BaseView(V v) {
        this.v = v;
    }

    @Override
    public void showProgress(boolean isShow) {
        if (v != null) {
            v.showProgress(isShow);
        }
    }

    @Override
    public void fail(String msg) {
        if (v != null) {
            v.fail(msg);
        }
    }

    @Override
    public void error(Throwable tr) {
        if (v != null) {
            v.error(tr);
        }
    }
}
