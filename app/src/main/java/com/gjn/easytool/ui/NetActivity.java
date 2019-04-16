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
    private String downloadUrl = "https://github.com/Gaojianan2016/EasyTool/blob/master/资源/app.apk";
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
