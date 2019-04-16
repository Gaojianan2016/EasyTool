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


    ProgressBar progressBar;
    Call Call;
    private String downloadUrl = "https://nbcache00.baidupcs.com/file/5b0241b203619e261ab533e1ade37ecc?" +
            "bkt=p3-14005b0241b203619e261ab533e1ade37ecc4b31dce90000001e10c8&" +
            "xcode=b7f3c1a2b27179725c856f6118a14ce74184e727eb223b2f7765844d0716d17886d57f9b4ce0d9b2f4837" +
            "ca1cb037c959a7e3ac4ae9d7ad8&fid=4010892182-250528-276997339918545&time=1555395097&" +
            "sign=FDTAXGERLQBHSKf-DCb740ccc5511e5e8fedcff06b081203-xt3pohGnzcjJSsphbc8I3ZLxfLs%3D&to=h5&size=1970376" +
            "&sta_dx=1970376&sta_cs=22&sta_ft=jpg&sta_ct=7&sta_mt=7" +
            "&fm2=MH%2CYangquan%2CAnywhere%2C%2Cfujian%2Cct&ctime=1506264410" +
            "&mtime=1506305590&resv0=cdnback&resv1=0&vuk=4010892182&iv=0&htype=" +
            "&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=14005b0241b203619e261ab533e1ade37ecc4b31dce90000001e10c8&sl=68616270" +
            "&expires=8h&rt=pr&r=491820287&mlogid=2448962651318004643&vbdid=1006636502&fin=Sakura-4-1.jpg" +
            "&fn=Sakura-4-1.jpg&rtype=1&dp-logid=2448962651318004643&dp-callid=0.1.1&hps=1&tsl=200&csl=200" +
            "&csign=TsXSM8%2FB0G822EbBR9TujqFO1lg%3D&so=0&ut=6&uter=4&serv=0&uc=3447588337&ti=e3357e20d22cf848765db854a6a48d646d06d6c711b2a8bd&by=themis";
    private String imgUrl = "http://gank.io/api/data/福利/";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net;
    }

    @Override
    protected void initView() {
        DefaultInterceptor.isDebug = true;
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpManager.getInstance().getAsyn(imgUrl + "10/1", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        EasyLog.e("OkHttpManager访问失败 " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
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
                                EasyLog.e("RetrofitManager访问成功");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                EasyLog.e("RetrofitManager访问失败 " + throwable.getMessage());
                            }
                        });
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadManager.getInstance().download(downloadUrl, new DownLoadManager.OnDownLoadListener() {
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
                        FileUtils.openFile(mActivity, file);
                    }

                    @Override
                    public void fail(Call call) {
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
    }
}
