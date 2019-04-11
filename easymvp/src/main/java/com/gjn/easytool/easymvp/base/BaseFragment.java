package com.gjn.easytool.easymvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjn.easytool.dialoger.base.BaseDialogFragment;
import com.gjn.easytool.easymvp.Interface.IUIEvent;
import com.gjn.easytool.toaster.EasyToast;
import com.gjn.easytool.utils.ActivityUtils;
import com.gjn.easytool.utils.ResourcesUtils;
import com.gjn.easytool.utils.ViewUtils;

/**
 * @author gjn
 * @time 2019/4/11 11:37
 */

public abstract class BaseFragment extends Fragment implements IUIEvent {

    protected Fragment mFragment;
    protected Activity mActivity;
    protected Bundle mBundle;
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);
        mFragment = this;
        mActivity = getActivity();
        mBundle = getArguments() == null ? new Bundle() : getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = ResourcesUtils.getView(inflater, getLayoutId(), container, false);
            init();
            initView();
            initData();
        }
        ViewUtils.removeParent(mView);
        return mView;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void preCreate() {
    }

    protected void init() {
    }

    protected final <T extends View> T findViewById(int id) {
        return mView.findViewById(id);
    }

    @Override
    public void showToast(String msg) {
        EasyToast.show(mActivity, msg);
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
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showDialog(dialogFragment);
        }
    }

    @Override
    public void dismissDialog(BaseDialogFragment dialogFragment) {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissDialog(dialogFragment);
        }
    }

    @Override
    public void showOnlyDialog(BaseDialogFragment dialogFragment) {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showOnlyDialog(dialogFragment);
        }
    }

    @Override
    public void dismissAll() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissAll();
        }
    }
}
