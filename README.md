# EasyTool [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html) [![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19) [![Version](https://img.shields.io/badge/Android%20Studio-3.0.1-brightgreen.svg)](https://img.shields.io/badge/Android%20Studio-3.0.1-brightgreen.svg)

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
    implementation xxx
}
```
