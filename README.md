# EasyTool
[![](https://jitpack.io/v/Gaojianan2016/EasyTool.svg)](https://jitpack.io/#Gaojianan2016/EasyTool) [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html) [![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19) [![Version](https://img.shields.io/badge/Android%20Studio-3.0.1-brightgreen.svg)](https://img.shields.io/badge/Android%20Studio-3.0.1-brightgreen.svg)

<br>

## 使用步骤
> Step 1.在**根目录**的build.gradle的repositories中添加：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
> Step 2.在**项目**的build.gradle的dependencies中加入引用：
```
dependencies {
    ...
    //简单DialogFragment 使用
    implementation 'com.github.Gaojianan2016.EasyTool:dialoger:x.y.z'
    //简单mvp接入 使用
    implementation 'com.github.Gaojianan2016.EasyTool:easymvp:x.y.z'
    //简单OkHttp3 或 Retrofit2 使用
    implementation 'com.github.Gaojianan2016.EasyTool:easynet:x.y.z'
    //简单log打印 使用
    implementation 'com.github.Gaojianan2016.EasyTool:logger:x.y.z'
    //简单Toast提示 使用
    implementation 'com.github.Gaojianan2016.EasyTool:toaster:x.y.z'
    //常用工具类集合
    implementation 'com.github.Gaojianan2016.EasyTool:utils:x.y.z'
}
```
**x.y.z请根据jitpack取值**

----------------

## Demo说明
![首页](https://github.com/Gaojianan2016/EasyTool/blob/master/%E8%B5%84%E6%BA%90/demoMain.png)

首页只作为跳转的简单布局
MainActivity.class
```
package com.gjn.easytool;

import android.view.View;

import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.easynet.DefaultInterceptor;
import com.gjn.easytool.ui.DialogActivity;
import com.gjn.easytool.ui.LoggerActivity;
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
    }
}
```

----------------

## 更新说明
**1.0.3**
```
easynet库 修改DownLoadManager管理类停止下载接口,修复某些条件下载无后缀bug，目前只做了单例模式（既单一下载管理，多下载后续加入）
utils库 修改FileUtils 新增一些对文件名称后缀判断的方法
```
**1.0.2**
```
easynet库 添加DownLoadManager管理类用于下载
utils库 修改FileUtils 为打开7.0以上的文件加入provider
```
**1.0.1-pre**
```
预版本，正常可用
```