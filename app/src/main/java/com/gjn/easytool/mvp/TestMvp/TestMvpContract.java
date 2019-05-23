package com.gjn.easytool.mvp.TestMvp;

import com.gjn.easytool.easymvp.Interface.IMvpView;

public interface TestMvpContract {
    interface view extends IMvpView {
        void success();
    }

    interface presenter {
        void onClick();
    }
}