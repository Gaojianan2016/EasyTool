package com.gjn.easytool.mvp.Mvp2;

import com.gjn.easytool.easymvp.Interface.IMvpView;
import com.gjn.easytool.easymvp.base.BaseView;
import com.gjn.easytool.mvp.TestMvp.TestMvpContract;

/**
 * Creator: Gjn
 * Time: 2019/12/11 11:29
 * Eamil: 732879625@qq.com
 **/
public abstract class Mvp2View extends BaseView implements Mvp2Contract.view, TestMvpContract.view {

    public Mvp2View(IMvpView mvpView) {
        super(mvpView);
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onSuccess2() {
    }

    @Override
    public void success() {
    }
}
