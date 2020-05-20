package com.gjn.easytool.ui;

import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.gjn.easytool.R;
import com.gjn.easytool.databinding.DialogTestBinding;
import com.gjn.easytool.dialoger.EasyDialogFragment;
import com.gjn.easytool.dialoger.EasyDialogManager;
import com.gjn.easytool.dialoger.base.BaseDialogFragment;
import com.gjn.easytool.dialoger.base.DataBindingHolder;
import com.gjn.easytool.dialoger.base.IDialogDataBinding;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;

/**
 * @author gjn
 * @time 2019/4/15 16:54
 */

public class DialogActivity extends BaseMvpActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showEasyNormalDialog("我是普通的自带Dialog", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击确定");
                        check();
                    }
                });
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showEasyInputDialog("请填写意见", "提交", 20, new EasyDialogManager.EasyInputListener() {
                    @Override
                    public void confirm(View v, Editable msg, int maxSize) {
                        showToast("意见如下\n" + msg.toString());
                        check();
                    }
                });
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showEasyDelayDialog("我是自带延时Dialog", 4, "延时确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击确定");
                        check();
                    }
                });
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showEasyOneBtnDialog("我是自带单按钮Dialog", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击确定");
                        check();
                    }
                });
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showSmallLoadingDialog();
                check();
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showMiddleLoadingDialog();
                check();
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showLargeLoadingDialog();
                check();
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManager.showAndroidDialog("提示", "我是Android自带Dialog", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("点击是");
                        check();
                    }
                });
            }
        });
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialogFragment dialogFragment = EasyDialogFragment.newInstance(R.layout.dialog_test, new IDialogDataBinding() {
                    @Override
                    public void convertView(DataBindingHolder holder, DialogFragment dialogFragment) {
                        DialogTestBinding binding = (DialogTestBinding) holder.getDataBinding();
                        binding.btnDt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("点击DialogTestBinding.btnDt");
                                check();
                            }
                        });

                    }
                });
                dialogFragment.setTransparent(true);
                showDialog(dialogFragment);
            }
        });
    }

    private void check() {
        Log.e("-s-", "size = " + mDialogManager.getFragments().size());
    }
}
