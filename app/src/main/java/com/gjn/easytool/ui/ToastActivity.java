package com.gjn.easytool.ui;

import android.view.View;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.toaster.EasyToast;

public class ToastActivity extends BaseMvpActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toast;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("正常Toast");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showInfo(mActivity, "请先输入用户名");
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showWarning(mActivity, "无法修改数据");
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showError(mActivity, "修改失败！");
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showSuccess(mActivity, "更改成功");
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showFail(mActivity, "登录失败");
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showWait(mActivity, "更新中...");
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showProhibit(mActivity, "禁止访问");
            }
        });
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showConfirm(mActivity, "确定通过");
            }
        });
    }
}
