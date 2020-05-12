package com.gjn.easytool.ui;

import android.view.View;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.BindPresenter;
import com.gjn.easytool.easymvp.BindPresenters;
import com.gjn.easytool.easymvp.MvpLog;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.easymvp.base.BaseView;
import com.gjn.easytool.mvp.Mvp2.Mvp2Presenter;
import com.gjn.easytool.mvp.Mvp2.Mvp2View;
import com.gjn.easytool.mvp.TestMvp.TestMvpPresenter;

/**
 * Creator: Gjn
 * Time: 2019/12/11 11:23
 * Eamil: 732879625@qq.com
 **/
@BindPresenters({Mvp2Presenter.class, TestMvpPresenter.class})
public class Mvp2Activity extends BaseMvpActivity {

    @BindPresenter
    Mvp2Presenter presenter;
    @BindPresenter
    TestMvpPresenter testMvpPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected BaseView getMvpView() {
        return new Mvp2View(this) {
            @Override
            public void onSuccess() {
                MvpLog.e("Mvp2View 11111111111111111");
            }

            @Override
            public void onSuccess2() {
                MvpLog.e("Mvp2View 2222222222222222222222");
            }

            @Override
            public void success() {
                MvpLog.e("Mvp2View 33333333333");
                findViewById(R.id.btn).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mvpView.showProgress(false);
                    }
                }, 2000);
            }
        };
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpView.showProgress(true);
                presenter.onClick();
                testMvpPresenter.onClick();
            }
        });
    }
}
