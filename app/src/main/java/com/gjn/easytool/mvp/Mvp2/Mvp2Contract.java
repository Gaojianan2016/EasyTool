package com.gjn.easytool.mvp.Mvp2;

import com.gjn.easytool.easymvp.Interface.IMvpView;

public interface Mvp2Contract {
    interface view extends IMvpView {
        void onSuccess();

        void onSuccess2();
    }

    interface presenter {
        void onClick();
    }
}