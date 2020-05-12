package com.gjn.easybase;

/**
 * Creator: Gjn
 * Time: 2019/12/11 11:15
 * Eamil: 732879625@qq.com
 **/
public abstract class BaseLazyFragment extends ABaseFragment {

    private boolean isPrepared = false;
    private boolean isVisible = false;

    @Override
    protected void init() {
        super.init();
        isPrepared = true;
        isCanLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            isCanLoadData();
        } else {
            isVisible = false;
        }
    }

    private void isCanLoadData() {
        if (isPrepared && isVisible) {
            lazyData();

            //防止重复加载数据
            isPrepared = false;
            isVisible = false;
        }
    }

    protected abstract void lazyData();
}
