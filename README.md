# EasyTool
[![](https://jitpack.io/v/Gaojianan2016/EasyTool.svg)](https://jitpack.io/#Gaojianan2016/EasyTool) [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html) [![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

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
**x.y.z请根据jitpack取值 请取1.0.6之后的版本**
**后缀带有x的版本是更新为androidx的版本**


----------------

## 更新说明
**2.0.2x**
```
Base基础类新增getBundle()方法用于直接获取Bundle
Mvvm基础类将createViewModel改为protected
Utils新增媒体选择类 file新增部分方法
```
**2.0.1x**
```
旧版本修复1.1.3~1.1.6的bug内容也升级到androidx
新增easybase 用于设置基础base
新增easymvvm 基础版本mvvm模式
MvpLog改为BaseLog
```
**2.0.0x**
```
所有版本升级androidx
```
**1.1.2**
```
utils 新增BlurBitmapUtil处理高斯模糊图片。
```
**1.1.1**
```
easyrxevent 新增库，用于管理rxbus。
```
**1.1.0**
```
utils ReflexUtils setValue，getValue进行区分新增setJsonValue和getJsonValue用来区别一个是用as自动生成的set get方法和用gson工具生成的get set方法
easysqlite 新增库，用于简单管理sqlite。拥有正常增删改查功能，自定义的数据库版本升级需要自己写代码（这边的数据库升级是原来的库新增了参数或者减少了参数这种）。
```
**1.0.9**
```
easymvp 修复onDestroy可能崩溃bug，由于mDialogManager.clearDialog()调用在super之后的原因
dialoger 将onDismiss的监听去除，还是使用onCancel监听，防止多次调用dialogFragment崩溃
```
**1.0.8**
```
easymvp 修改Fragment的EasyDialogManager相关操作，防止多次调用dialog显示隐藏可能会崩溃（最好还是自己控制显示隐藏dialog）
easymvp 新增MvpLog用于控制是否打印log
logger 去除EasyLog中的控制打印log变量
dialoger 新增获得默认三个进度框的方法
```
**1.0.7**
```
easytool FileUtils修改打开文件方法默认使用7.0以前的打开方式，新增方法openFileApi24用来调用7.0之后的方式打开
```
**1.0.6**（之前的版本存在致命bug 绑定mvp模式崩溃）
```
easymvp 修改绑定mvp模式崩溃bug。
easytool AnnotationsUtils新增checkAnnotations(Field field, Class<? extends Annotation> annotationCls)方法
```
**1.0.5**
```
easymvp 修改Fragment和Activity无法同时存在会崩溃bug。
```
**1.0.4**
```
easynet库 修改DownLoadManager管理类新增downloadOnUI方法，传入Activity可以让最后的结果转为UI线程。
```

----------------

## Demo说明

### 首页 MainActivity
![首页](https://github.com/Gaojianan2016/EasyTool/blob/master/%E8%B5%84%E6%BA%90/demoMain.png)

首页只作为跳转的简单布局
```
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

### logger工具类 LoggerActivity

主要实现一些log打印功能 多加了一个直接打印json的功能 需要接入Gson
```
public class LoggerActivity extends BaseMvpActivity {

    String json = "{\"name\": \"王五\",\"sex\": \"男\",\"age\": 24,\"info\": {\"phone\": \"12345698710\",\"isStudent\": true}}";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logger;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyLog.v("Version LOG");
                EasyLog.d("Debug LOG");
                EasyLog.i("Info LOG");
                EasyLog.w("Warn LOG");
                EasyLog.e("Error LOG");
                EasyLog.wtf("WTF LOG");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyLog.json(json);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", "张三");
                    object.put("sex", "男");
                    object.put("age", 17);
                    JSONObject info = new JSONObject();
                    info.put("phone", "12345678901");
                    info.put("isStudent", true);
                    object.put("info", info);
                    EasyLog.json(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setName("李四");
                user.setSex("女");
                user.setAge(26);
                User.InfoBean info = new User.InfoBean();
                info.setPhone("10987654321");
                info.setIsStudent(false);
                user.setInfo(info);
                EasyLog.json(user);
            }
        });
    }
}
```

### toaster工具类 ToastActivity
![toaster工具类](https://github.com/Gaojianan2016/EasyTool/blob/master/%E8%B5%84%E6%BA%90/toast%E6%BC%94%E7%A4%BA.gif)
除了正常的Toast提示 还加入了一些自定义的统一样式

```
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
```

### dialoger工具类 DialogActivity
![dialoger工具类](https://github.com/Gaojianan2016/EasyTool/blob/master/%E8%B5%84%E6%BA%90/dialog%E6%BC%94%E7%A4%BA.gif)
拥有自带的Android Dialog调用和一些简单的模板对话框
```
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
```

### easynet工具类 NetActivity
![easynet工具类](https://github.com/Gaojianan2016/EasyTool/blob/master/%E8%B5%84%E6%BA%90/%E4%B8%8B%E8%BD%BD%E6%BC%94%E7%A4%BA.gif)

正常的访问网络get、post方式和简单的单一下载管理功能
下载地址是一张百度云上面的图片地址有点长。

```
public class NetActivity extends BaseMvpActivity {

    ProgressBar progressBar, progressBar2;
    Call Call, Call2;
    private String downloadUrl = "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg";
    String downloadUrl2 = "https://ww1.sinaimg.cn/large/0065oQSqly1g2hekfwnd7j30sg0x4djy.jpg";
    private String imgUrl = "http://gank.io/api/data/福利/";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net;
    }

    @Override
    protected void initView() {
        DefaultInterceptor.isDebug = true;
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpManager.getInstance().getAsyn(imgUrl + "10/1", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("OkHttpManager访问失败");
                            }
                        });
                        EasyLog.e("OkHttpManager访问失败 " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("OkHttpManager访问成功");
                            }
                        });
                        EasyLog.e("OkHttpManager访问成功");
                    }
                });
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager.linkOnMainThread(RetrofitManager.create(imgUrl, IUrl.class).getPic(1, 10),
                        new Consumer<Pic>() {
                            @Override
                            public void accept(Pic pic) throws Exception {
                                showToast("RetrofitManager访问成功");
                                EasyLog.e("RetrofitManager访问成功");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showToast("RetrofitManager访问失败");
                                EasyLog.e("RetrofitManager访问失败 " + throwable.getMessage());
                            }
                        });
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadManager.getInstance().downloadOnUI(mActivity, downloadUrl, new DownLoadManager.OnDownLoadListener() {
                    @Override
                    public void start(Call call, File file, String name, int lenght) {
                        Call = call;
                        progressBar.setProgress(0);
                        progressBar.setMax(lenght);
                    }

                    @Override
                    public void loading(Call call, int readStream, int allStream) {
                        progressBar.setProgress(readStream);
                    }

                    @Override
                    public void success(Call call, File file) {
                        EasyLog.e("progressBar下载成功");
                        showToast("progressBar下载成功");
                        openFile(file);
                    }

                    @Override
                    public void fail(Call call) {
                        showToast("progressBar下载失败");
                        EasyLog.e("progressBar下载失败");
                    }
                });
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Call != null) {
                    Call.cancel();
                }
                progressBar.setProgress(0);
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadManager.getInstance().downloadOnUI(mActivity, downloadUrl2, new DownLoadManager.OnDownLoadListener() {
                    @Override
                    public void start(Call call, File file, String name, int lenght) {
                        Call2 = call;
                        progressBar2.setProgress(0);
                        progressBar2.setMax(lenght);
                    }

                    @Override
                    public void loading(Call call, int readStream, int allStream) {
                        progressBar2.setProgress(readStream);
                    }

                    @Override
                    public void success(Call call, File file) {
                        EasyLog.e("progressBar2下载成功");
                        showToast("progressBar2下载成功");
                        openFile(file);
                    }

                    @Override
                    public void fail(Call call) {
                        showToast("progressBar2下载失败");
                        EasyLog.e("progressBar2下载失败");
                    }
                });
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Call2 != null) {
                    Call2.cancel();
                }
                progressBar2.setProgress(0);
            }
        });
    }

    private void openFile(File file) {
        FileUtils.openFileApi24(mActivity, file);
    }
}
```

```
public interface IUrl {

    @GET("{count}/{page}")
    Observable<Pic> getPic(@Path("page") int page, @Path("count") int count);
}
```
Pic只是json类 可以不理。

### utils工具类 StringActivity、QrcodeActivity、ReflexActivity
这边就放三个比较常用的功能，具体多的功能可以自己看。后续开发有碰到常用的也会更新进去。
```
public class StringActivity extends BaseMvpActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_string;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "我是测试文本";

                EasyLog.d(str + " deleteLastStr " + StringUtils.deleteLastStr(str));
                EasyLog.d(str + " changeLastStr " + StringUtils.changeLastStr(str, "字"));
                EasyLog.d(str + " getFirstStr " + StringUtils.getFirstStr(str));
                EasyLog.d(str + " getLastStr " + StringUtils.getLastStr(str));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                EasyLog.d("time -> " + StringUtils.dataFormat(time));

                long time1 = time - 1000 * 3;
                long time2 = time1 - 1000 * 60;
                long time3 = time2 - 1000 * 60 * 24;
                long time4 = time3 - 1000 * 60 * 60 * 2;
                long time5 = time4 - 1000 * 60 * 60 * 24;

                EasyLog.d("time1 -> " + StringUtils.systemFormat(time1));
                EasyLog.d("time2 -> " + StringUtils.systemFormat(time2));
                EasyLog.d("time3 -> " + StringUtils.systemFormat(time3));
                EasyLog.d("time4 -> " + StringUtils.systemFormat(time4));
                EasyLog.d("time5 -> " + StringUtils.systemFormat(time5));
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyLog.d("你好 isChinese " + StringUtils.isChinese("你好"));
                EasyLog.d("你s好 isChinese " + StringUtils.isChinese("你s好"));
                EasyLog.d("123514 hasChinese " + StringUtils.hasChinese("123514 "));
                EasyLog.d("_=-=-%（）hasChinese " + StringUtils.hasChinese("_=-=-%（）"));
            }
        });
    }
}
```

```
public class QrcodeActivity extends BaseMvpActivity{

    ImageView imageView;
    EditText editText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView() {
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qr = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(qr)) {
                        showToast("字符串为空，无法生成");
                        return;
                    }
                    Bitmap bitmap = QRUtils.str2bitmap(qr);
                    imageView.setImageBitmap(bitmap);
                    editText.setText("");
                    showToast("生成成功");
                } catch (WriterException e) {
                    showToast("生成失败" + e.getMessage());
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qr = QRUtils.bitmap2str(ViewUtils.getImageViewBitmap(imageView));
                    editText.setText(qr);
                    showToast("解析成功");
                } catch (FormatException | ChecksumException | NotFoundException e) {
                    showToast("解析失败" + e.getMessage());
                }
            }
        });
    }
}
```

```
public class ReflexActivity extends BaseMvpActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_reflex;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReflexUtils.printInfo(User.class);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = ReflexUtils.createObj(User.class,
                        new Class[]{String.class, String.class, int.class},
                        new Object[]{"张三", "男", 26});
                if (user != null) {
                    EasyLog.d(user.toString());
                }
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = ReflexUtils.createObj(User.class);
                for (Field field : ReflexUtils.getDeclaredFields(user)) {
                    switch (field.getName()) {
                        case "name":
                            ReflexUtils.setField(user, field, "李四");
                            break;
                        case "sex":
                            ReflexUtils.setField(user, field, "女");
                            break;
                        case "age":
                            ReflexUtils.setField(user, field, 17);
                            break;
                    }
                }
                if (user != null) {
                    EasyLog.d(user.toString());
                }
            }
        });
    }
}
```

