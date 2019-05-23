package com.gjn.easytool;

import android.view.View;

import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.easynet.DefaultInterceptor;
import com.gjn.easytool.ui.DialogActivity;
import com.gjn.easytool.ui.LoggerActivity;
import com.gjn.easytool.ui.MvpActivity;
import com.gjn.easytool.ui.NetActivity;
import com.gjn.easytool.ui.QrcodeActivity;
import com.gjn.easytool.ui.ReflexActivity;
import com.gjn.easytool.ui.StringActivity;
import com.gjn.easytool.ui.ToastActivity;
import com.gjn.permissionlibrary.PermissionUtils;

public class MainActivity extends BaseMvpActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        DefaultInterceptor.isDebug = BuildConfig.DEBUG;
        click();

        PermissionUtils.requestPermissions(mActivity, 0x111, PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE,
                PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE);
    }

    private void click() {
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(LoggerActivity.class);
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(ToastActivity.class);
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(StringActivity.class);
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(QrcodeActivity.class);
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(ReflexActivity.class);
            }
        });
        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(DialogActivity.class);
            }
        });
        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(NetActivity.class);
            }
        });
        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext(MvpActivity.class);
            }
        });
    }
}
