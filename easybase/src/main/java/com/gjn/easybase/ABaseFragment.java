package com.gjn.easybase;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gjn.easytool.dialoger.EasyDialogManager;
import com.gjn.easytool.dialoger.base.BaseDialogFragment;
import com.gjn.easytool.toaster.EasyToast;
import com.gjn.easytool.utils.ActivityUtils;
import com.gjn.easytool.utils.ResourcesUtils;
import com.gjn.easytool.utils.ViewUtils;

public abstract class ABaseFragment extends Fragment implements IUIEvent {

    protected Fragment mFragment;
    protected Activity mActivity;
    protected Bundle mBundle;
    protected View mView;
    protected EasyDialogManager mDialogManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);
        mFragment = this;
        mActivity = getActivity();
        mBundle = getArguments() == null ? new Bundle() : getArguments();
        mDialogManager = new EasyDialogManager(this);
        getBundle();
        addLifecycleObserver();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = ResourcesUtils.getView(inflater, getLayoutId(), container, false);
        }
        ViewUtils.removeParent(mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void preCreate() {
    }

    protected void getBundle() {
    }

    protected void addLifecycleObserver() {
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
    public void onDestroyView() {
        dismissAll();
        super.onDestroyView();
    }
}