package com.gjn.easytool.ui;

import android.view.View;

import com.gjn.easytool.R;
import com.gjn.easytool.User;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.utils.ReflexUtils;

import java.lang.reflect.Field;

/**
 * @author gjn
 * @time 2019/4/15 16:54
 */

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
