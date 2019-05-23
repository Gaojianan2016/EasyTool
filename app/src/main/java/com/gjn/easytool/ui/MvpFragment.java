package com.gjn.easytool.ui;


import android.util.Log;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.BindPresenter;
import com.gjn.easytool.easymvp.BindPresenters;
import com.gjn.easytool.easymvp.base.BaseMvpFragment;
import com.gjn.easytool.mvp.TestMvp.TestMvpContract;
import com.gjn.easytool.mvp.TestMvp.TestMvpPresenter;

/**
 * @author gjn
 * @time 2019/5/23 15:37
 */

@BindPresenters({TestMvpPresenter.class})
public class MvpFragment extends BaseMvpFragment implements TestMvpContract.view{

    @BindPresenter
    TestMvpPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mvp;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
//        presenter.onClick();
    }

    @Override
    public void success() {
        Log.e("-s-", "1111111111");
    }
}
