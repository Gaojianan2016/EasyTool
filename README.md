# EasyTool [![](https://jitpack.io/v/Gaojianan2016/EasyTool.svg)](https://jitpack.io/#Gaojianan2016/EasyTool) [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html) [![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19) [![Version](https://img.shields.io/badge/Android%20Studio-3.0.1-brightgreen.svg)](https://img.shields.io/badge/Android%20Studio-3.0.1-brightgreen.svg)

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
    implementation 'com.github.Gaojianan2016.EasyTool:dialoger:1.0.1-pre'
    //简单mvp接入 使用
    implementation 'com.github.Gaojianan2016.EasyTool:easymvp:1.0.1-pre'
    //简单OkHttp3 或 Retrofit2 使用
    implementation 'com.github.Gaojianan2016.EasyTool:easynet:1.0.1-pre'
    //简单log打印 使用
    implementation 'com.github.Gaojianan2016.EasyTool:logger:1.0.1-pre'
    //简单Toast提示 使用
    implementation 'com.github.Gaojianan2016.EasyTool:toaster:1.0.1-pre'
    //常用工具类集合
    implementation 'com.github.Gaojianan2016.EasyTool:utils:1.0.1-pre'
}
```
