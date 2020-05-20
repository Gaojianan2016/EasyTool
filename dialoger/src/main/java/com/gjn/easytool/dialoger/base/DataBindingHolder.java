package com.gjn.easytool.dialoger.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class DataBindingHolder {
    private ViewDataBinding dataBinding;

    public DataBindingHolder(ViewDataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }


    public static DataBindingHolder create(ViewDataBinding dataBinding) {
        return new DataBindingHolder(dataBinding);
    }

    public static DataBindingHolder create(Activity activity, int id, ViewGroup container) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), id, container, false);
        return create(binding);
    }

    public ViewDataBinding getDataBinding() {
        return dataBinding;
    }
}
