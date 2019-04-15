package com.gjn.easytool.ui;

import android.view.View;

import com.gjn.easytool.R;
import com.gjn.easytool.User;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.logger.EasyLog;

import org.json.JSONException;
import org.json.JSONObject;

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
