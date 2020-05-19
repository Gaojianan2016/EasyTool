package com.gjn.easymvvm.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.gjn.easybase.ABaseFragment;
import com.gjn.easymvvm.BindViewModel;
import com.gjn.easytool.utils.AnnotationsUtils;
import com.gjn.easytool.utils.ReflexUtils;
import com.gjn.easytool.utils.ViewUtils;

import java.lang.reflect.Field;
import java.util.List;

public abstract class BaseMvvmFragment<VDB extends ViewDataBinding>
        extends ABaseFragment {

    protected VDB dataBinding;

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            dataBinding = DataBindingUtil.inflate(inflater, getDataLayoutId(), container, false);
            mView = dataBinding.getRoot();
            dataBinding.setLifecycleOwner(this);
            init();
            createViewModel();
            initView();
            initData();
        }
        ViewUtils.removeParent(mView);
        return mView;
    }

    protected void createViewModel() {
        List<Field> fields = AnnotationsUtils.getField(this, BindViewModel.class);
        for (Field field : fields) {
            Class clz = field.getType();
            ReflexUtils.setField(this, field, ViewModelProvider.AndroidViewModelFactory
                    .getInstance(mActivity.getApplication()).create(clz));
        }
    }

    protected abstract int getDataLayoutId();
}
