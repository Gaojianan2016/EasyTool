package com.gjn.easytool.ui;

import android.view.View;
import android.widget.ProgressBar;

import com.gjn.easytool.IUrl;
import com.gjn.easytool.Pic;
import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.easynet.DefaultInterceptor;
import com.gjn.easytool.easynet.DownLoadManager;
import com.gjn.easytool.easynet.OkHttpManager;
import com.gjn.easytool.easynet.RetrofitManager;
import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author gjn
 * @time 2019/4/15 16:54
 */

public class NetActivity extends BaseMvpActivity {


    ProgressBar progressBar, progressBar2;
    Call Call, Call2;
    private String downloadUrl = "https://nbcache00.baidupcs.com/file/5b0241b203619e261ab533e1ade37ecc?" +
            "xcode=f5960cf0ee6db70bc464ecbe44733c914184e727eb223b2ff9b4b9c940cc3becfe98232e84e1" +
            "5e39c14560a708d7579e6563ff7f8f8f53f8&fid=4010892182-250528-276997339918545" +
            "&time=1558407709&sign=FDTAXGERLQHSKf-DCb740ccc5511e5e8fedcff06b081203-1A6axtLUYvdU%2BmIsDI5q7DxF2sg%3D&to=h5" +
            "&size=1970376&sta_dx=1970376&sta_cs=26&sta_ft=jpg&sta_ct=7" +
            "&sta_mt=7&fm2=MH%2CYangquan%2CAnywhere%2C%2Cfujian%2Cct&ctime=1506264410&mtime=1506305590" +
            "&resv0=cdnback&resv1=0&vuk=4010892182&iv=0&htype=&newver=1&newfm=1&secfm=1&flow_ver=3" +
            "&pkey=en-2f0c334d5aef34c1ec98ca2afd50abdee3c6b9cf73e6ff6e9018c9812a741113ceaca294dfa12d335a8" +
            "9221865bf252474b48b54ed4c4769305a5e1275657320&sl=68616270&expires=8h&rt=sh&r=835097279" +
            "&mlogid=3257654546322023582&vbdid=1006636502&fin=Sakura-4-1.jpg&fn=Sakura-4-1.jpg&rtype=1" +
            "&dp-logid=3257654546322023582&dp-callid=0.1.1&hps=1&tsl=200" +
            "&csl=200&csign=TsXSM8%2FB0G822EbBR9TujqFO1lg%3D&so=0&ut=6&uter=4&serv=0&uc=1865268231" +
            "&ti=51702cd94a4865eeac1bf991765abdefe6ec8e7d0ed32ff2&by=themis";
    String downloadUrl2 = "https://qdct01.baidupcs.com/file/f316b146424b588c5c3ea4bb66a82888?fid=4010892182-250528-" +
            "43146222621473&time=1558411102&sign=FDTAXGERLQHSKfW-DCb740ccc5511e5e8fedcff06b081203-KJy90qmRSCxK%" +
            "2B77ipaosW%2BKeCsw%3D&to=90&size=2963098&sta_dx=2963098&sta_cs=1&sta_ft=mp3&sta_ct=7&sta_mt=7&fm2=M" +
            "H%2CYangquan%2CAnywhere%2C%2Cfujian%2Cct&ctime=1443629943&mtime=1444037479&resv0=cdnback&resv1=0&vuk" +
            "=4010892182&iv=0&htype=&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=en-e800ba91deec611fb8571ba01bb1f476" +
            "3e8183316ebfcb60399e84d73ab5a2d56a3bd877088312c457fc7b33e05c75a425c9db15c13aad69305a5e1275657320&" +
            "sl=79364174&expires=8h&rt=sh&r=486243323&mlogid=3258565253623899201&vbdid=1006636502&fin=%E5%BD%93%" +
            "E4%B8%89%E5%9B%BD%E6%9D%80%E9%81%87%E5%88%B0+nobody.mp3&fn=%E5%BD%93%E4%B8%89%E5%9B%BD%E6%9D%80%E9" +
            "%81%87%E5%88%B0+nobody.mp3&rtype=1&dp-logid=3258565253623899201&dp-callid=0.1.1&hps=1&tsl=100&csl=10" +
            "0&csign=V%2FRatQeohLeEIlkasXXAlttvt5E%3D&so=0&ut=6&uter=4&serv=0&uc=1865268231&ti=54c943154d8629" +
            "039dc8841dfa715187771908c69ffb8cd2&by=themis";
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
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("OkHttpManager访问失败");
                            }
                        });
                        EasyLog.e("OkHttpManager访问失败 " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
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
                        FileUtils.openFile(mActivity, file);
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
                        FileUtils.openFile(mActivity, file);
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
}
