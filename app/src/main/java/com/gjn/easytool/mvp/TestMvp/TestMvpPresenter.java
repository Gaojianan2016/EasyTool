package com.gjn.easytool.mvp.TestMvp;

import com.gjn.easytool.easymvp.base.BasePresenter;

public class TestMvpPresenter extends BasePresenter<TestMvpContract.view, TestMvpModel>
        implements TestMvpContract.presenter {

    @Override
    public void onClick() {
        if (isAttached()) {
            getModel().onClick();
        }
    }
}