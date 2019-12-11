package com.gjn.easytool.mvp.Mvp2;

import com.gjn.easytool.easymvp.base.BaseModel;

public class Mvp2Model extends BaseModel<Mvp2Contract.view>
        implements Mvp2Contract.presenter {

    @Override
    public void onClick() {
        getMvpView().onSuccess();

        getMvpView().onSuccess2();
    }
}