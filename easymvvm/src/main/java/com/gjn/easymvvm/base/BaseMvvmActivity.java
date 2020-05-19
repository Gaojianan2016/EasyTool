package com.gjn.easymvvm.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.gjn.easybase.ABaseActivity;
import com.gjn.easymvvm.BindViewModel;
import com.gjn.easytool.utils.AnnotationsUtils;
import com.gjn.easytool.utils.ReflexUtils;

import java.lang.reflect.Field;
import java.util.List;

public abstract class BaseMvvmActivity<VDB extends ViewDataBinding>
        extends ABaseActivity {

    protected VDB dataBinding;

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected void init() {
        super.init();
        dataBinding = DataBindingUtil.setContentView(this, getDataLayoutId());
        dataBinding.setLifecycleOwner(this);

        createViewModel();
    }

    protected void createViewModel() {
        List<Field> fields = AnnotationsUtils.getField(this, BindViewModel.class);
        for (Field field : fields) {
            Class clz = field.getType();
            ReflexUtils.setField(this, field, ViewModelProvider.AndroidViewModelFactory
                    .getInstance(getApplication()).create(clz));
        }
    }

    protected abstract int getDataLayoutId();

}
