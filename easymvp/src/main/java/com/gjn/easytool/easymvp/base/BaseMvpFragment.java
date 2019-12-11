package com.gjn.easytool.easymvp.base;

import com.gjn.easytool.easymvp.Interface.IMvpView;
import com.gjn.easytool.easymvp.MvpBindAnnotations;

/**
 * @author gjn
 * @time 2019/4/11 14:32
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseLazyFragment implements IMvpView {
    protected MvpBindAnnotations mvpBindAnnotations;
    protected BaseView mvpView;

    @Override
    protected void init() {
        super.init();
        mvpBindAnnotations = MvpBindAnnotations.newInstance(mActivity, mFragment);
        mvpView = getMvpView();
        mvpBindAnnotations.attachedPresenter(mvpView);
    }

    @Override
    protected void lazyData() {
    }

    protected BaseView getMvpView() {
        return null;
    }

    protected P getPresenter() {
        return mvpBindAnnotations.getPresenterItem();
    }

    protected P getPresenter(int i) {
        return mvpBindAnnotations.getPresenterItem(i);
    }

    @Override
    public void onDestroyView() {
        mvpBindAnnotations.detachedPresenter();
        super.onDestroyView();
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
