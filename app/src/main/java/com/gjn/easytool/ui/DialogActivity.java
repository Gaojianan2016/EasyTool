package com.gjn.easytool.ui;

import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;

import com.gjn.easytool.R;
import com.gjn.easytool.dialoger.EasyDialogManager;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;

/**
 * @author gjn
 * @time 2019/4/15 16:54
 */

public class DialogActivity extends BaseMvpActivity {

    EasyDialogManager dialogManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initView() {
        dialogManager = new EasyDialogManager(mActivity);
    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showEasyNormalDialog("我是普通的自带Dialog", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击确定");
                    }
                });
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showEasyInputDialog("请填写意见", "提交", 20, new EasyDialogManager.EasyInputListener() {
                    @Override
                    public void confirm(View v, Editable msg, int maxSize) {
                        showToast("意见如下\n" + msg.toString());
                    }
                });
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showEasyDelayDialog("我是自带延时Dialog", 4, "延时确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击确定");
                    }
                });
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showEasyOneBtnDialog("我是自带单按钮Dialog", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击确定");
                    }
                });
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showSmallLoadingDialog();
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showMiddleLoadingDialog();
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showLargeLoadingDialog();
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManager.showAndroidDialog("提示", "我是Android自带Dialog", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("点击是");
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        dialogManager.clearDialog();
        super.onDestroy();
    }
}
