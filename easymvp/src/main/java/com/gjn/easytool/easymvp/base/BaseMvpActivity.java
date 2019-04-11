package com.gjn.easytool.easymvp.base;

import com.gjn.easytool.easymvp.Interface.IMvpView;
import com.gjn.easytool.easymvp.MvpBindAnnotations;

/**
 * @author gjn
 * @time 2019/4/11 14:25
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IMvpView {
    protected MvpBindAnnotations mvpBindAnnotations;

    @Override
    protected void init() {
        super.init();
        mvpBindAnnotations = MvpBindAnnotations.newInstance(mActivity);
    }

    protected P getPresenter() {
        return mvpBindAnnotations.getPresenterItem();
    }

    protected P getPresenter(int i) {
        return mvpBindAnnotations.getPresenterItem(i);
    }

    @Override
    protected void onDestroy() {
        mvpBindAnnotations.detachedPresenter();
        super.onDestroy();
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            mDialogManager.showMiddleLoadingDialog();
        } else {
            mDialogManager.dismissMiddleLoadingDialog();
        }
    }

    @Override
    public void fail(String msg) {
        showToast(msg);
    }

    @Override
    public void error(Throwable tr) {
    }
}
