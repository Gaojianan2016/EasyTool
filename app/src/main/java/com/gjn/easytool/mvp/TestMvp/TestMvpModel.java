package com.gjn.easytool.mvp.TestMvp;

import com.gjn.easytool.easymvp.base.BaseModel;

public class TestMvpModel extends BaseModel<TestMvpContract.view>
        implements TestMvpContract.presenter {

    @Override
    public void onClick() {
        getMvpView().success();
    }
}