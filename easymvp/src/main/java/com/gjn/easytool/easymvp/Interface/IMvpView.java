package com.gjn.easytool.easymvp.Interface;

/**
 * @author gjn
 * @time 2019/4/11 11:52
 */

public interface IMvpView {
    void showProgress(boolean isShow);

    void fail(String msg);

    void error(Throwable tr);
}
