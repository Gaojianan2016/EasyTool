package com.gjn.easytool.easymvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gjn.easytool.dialoger.EasyDialogManager;
import com.gjn.easytool.dialoger.base.BaseDialogFragment;
import com.gjn.easytool.easymvp.Interface.IUIEvent;
import com.gjn.easytool.toaster.EasyToast;
import com.gjn.easytool.utils.ActivityUtils;
import com.gjn.easytool.utils.AppManager;

/**
 * @author gjn
 * @time 2019/4/11 10:51
 */

public abstract class BaseActivity extends AppCompatActivity implements IUIEvent {

    protected AppCompatActivity mActivity;
    protected Context mContext;
    protected Bundle mBundle;
    protected EasyDialogManager mDialogManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppManager.getInstance().addActivity(this);
        preCreate();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActivity = this;
        mContext = this;
        mBundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        mDialogManager = new EasyDialogManager(this);
        init();
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void preCreate() {
    }

    protected void init() {
    }

    @Override
    public void showToast(String msg) {
        EasyToast.show(mContext, msg);
    }

    @Override
    public void showNext(Class cls) {
        ActivityUtils.showNext(mActivity, cls);
    }

    @Override
    public void showNext(Class cls, Bundle bundle) {
        ActivityUtils.showNext(mActivity, cls, bundle);
    }

    @Override
    public void toNext(Class cls) {
        ActivityUtils.toNext(mActivity, cls);
    }

    @Override
    public void toNext(Class cls, Bundle bundle) {
        ActivityUtils.toNext(mActivity, cls, bundle);
    }

    @Override
    public void showDialog(BaseDialogFragment dialogFragment) {
        mDialogManager.showDialog(dialogFragment);
    }

    @Override
    public void dismissDialog(BaseDialogFragment dialogFragment) {
        mDialogManager.dismissDialog(dialogFragment);
    }

    @Override
    public void showOnlyDialog(BaseDialogFragment dialogFragment) {
        mDialogManager.showOnlyDialog(dialogFragment);
    }

    @Override
    public void dismissAll() {
        mDialogManager.clearDialog();
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
        mDialogManager.clearDialog();
    }
}
