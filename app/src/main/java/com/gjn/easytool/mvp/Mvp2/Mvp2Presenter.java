package com.gjn.easytool.mvp.Mvp2;

import com.gjn.easytool.easymvp.base.BasePresenter;

public class Mvp2Presenter extends BasePresenter<Mvp2Contract.view, Mvp2Model>
        implements Mvp2Contract.presenter {

    @Override
    public void onClick() {
        if (isAttached()) {
            getModel().onClick();
        }
    }
}