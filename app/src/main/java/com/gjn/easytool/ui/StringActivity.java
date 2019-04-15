package com.gjn.easytool.ui;

import android.view.View;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.utils.StringUtils;

/**
 * @author gjn
 * @time 2019/4/15 16:54
 */

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
