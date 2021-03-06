package com.gjn.easytool.dialoger;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gjn.easytool.dialoger.base.BaseDialogFragment;
import com.gjn.easytool.dialoger.base.DataBindingHolder;
import com.gjn.easytool.dialoger.base.IDialogConvertView;
import com.gjn.easytool.dialoger.base.IDialogDataBinding;
import com.gjn.easytool.dialoger.base.ViewHolder;

/**
 * @author gjn
 * @time 2019/4/10 10:38
 */

public class EasyDialogFragment extends BaseDialogFragment {
    private AlertDialog.Builder dialogBuilder;
    @LayoutRes
    private int layoutId = View.NO_ID;

    @LayoutRes
    private int dataLayoutId = View.NO_ID;

    private IDialogConvertView create;

    private IDialogDataBinding dataCreate;

    public static EasyDialogFragment newInstance() {
        return newInstance(null);
    }

    public static EasyDialogFragment newInstance(AlertDialog.Builder builder) {
        EasyDialogFragment dialogFragment = new EasyDialogFragment();
        dialogFragment.dialogBuilder = builder;
        return dialogFragment;
    }

    public static EasyDialogFragment newInstance(@LayoutRes int layoutId) {
        return newInstance(layoutId, (IDialogConvertView) null);
    }

    public static EasyDialogFragment newInstance(@LayoutRes int layoutId, IDialogConvertView create) {
        EasyDialogFragment dialogFragment = new EasyDialogFragment();
        dialogFragment.layoutId = layoutId;
        dialogFragment.create = create;
        return dialogFragment;
    }

    public static EasyDialogFragment newInstance(@LayoutRes int layoutId, IDialogDataBinding create) {
        EasyDialogFragment dialogFragment = new EasyDialogFragment();
        dialogFragment.dataLayoutId = layoutId;
        dialogFragment.dataCreate = create;
        return dialogFragment;
    }

    public void setCreate(@LayoutRes int id) {
        this.layoutId = id;
    }

    public void setCreate(IDialogConvertView create) {
        this.create = create;
    }

    public void setCreate(IDialogDataBinding create) {
        this.dataCreate = create;
    }

    public void setCreate(@LayoutRes int id, IDialogConvertView create) {
        setCreate(id);
        setCreate(create);
    }

    public void setCreate(@LayoutRes int id, IDialogDataBinding create) {
        setCreate(id);
        setCreate(create);
    }

    public void setDialogBuilder(AlertDialog.Builder builder) {
        this.dialogBuilder = builder;
    }

    @Override
    public AlertDialog.Builder getDialogBuilder() {
        return dialogBuilder;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getDataLayoutId() {
        return dataLayoutId;
    }

    @Override
    public void convertView(ViewHolder holder, DialogFragment dialogFragment) {
        if (create != null) {
            create.convertView(holder, dialogFragment);
        }
    }

    @Override
    public void convertView(DataBindingHolder holder, DialogFragment dialogFragment) {
        if (dataCreate != null) {
            dataCreate.convertView(holder, dialogFragment);
        }
    }
}
