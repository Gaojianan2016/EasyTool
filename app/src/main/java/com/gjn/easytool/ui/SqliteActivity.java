package com.gjn.easytool.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gjn.easytool.R;
import com.gjn.easytool.User;
import com.gjn.easytool.UserData;
import com.gjn.easytool.UserData2;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.easysqlite.DatabaseManager;
import com.gjn.easytool.easysqlite.DatabaseUtils;
import com.gjn.easytool.utils.ListUtils;
import com.gjn.easytool.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * @author gjn
 * @time 2019/6/19 14:03
 */

public class SqliteActivity extends BaseMvpActivity {

    EditText et1, et2, et3;
    TextView tv1, tv2, tv3;

    DatabaseManager databaseManager;

    private final static String TABLE_NAME = "table1";
    private final static String TABLE_VERSION = "table_version";
    private final static String IS_UPDATA = "IS_UPDATA";

    int version;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sqlite;
    }

    @Override
    protected void initView() {
        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView);

        version = SharedPreferencesUtil.getInt(TABLE_VERSION);
        boolean isupdata = SharedPreferencesUtil.getBoolean(IS_UPDATA);

        if (!isupdata) {
            if (version > 1) {
                Log.e("-s-", "升级版本");
                //第二版
                databaseManager = new DatabaseManager(this, "test", version);
                databaseManager.copyTable(TABLE_NAME, UserData.class, UserData2.class);
            }else {
                Log.e("-s-", "初始版本");
                //第一版
                databaseManager = new DatabaseManager(this, "test", version);
                databaseManager.createTable(TABLE_NAME, UserData.class);
            }
            Log.e("-s-", "修改完毕");
            SharedPreferencesUtil.setBoolean(IS_UPDATA, true);
        }else {
            Log.e("-s-", "未修改");
        }
    }

    @Override
    protected void initData() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (version == 1 || version == 0) {
                    databaseManager.createTable(TABLE_NAME, UserData.class);
                }else {
                    databaseManager.createTable(TABLE_NAME, UserData2.class);
                }
                showToast("新建");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (version == 1 || version == 0) {
                    showToast("升级表");
                    SharedPreferencesUtil.setInt(TABLE_VERSION, 2);
                    SharedPreferencesUtil.setBoolean(IS_UPDATA, false);
                }else {
                    showToast("当前已经是最新sql表");
                }

            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("删除");
                databaseManager.dropTable(TABLE_NAME);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et1.getText().toString().trim();
                String ageStr = et2.getText().toString().trim();
                int age = 0;
                String sex = et3.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    showToast("请输入姓名");
                    return;
                }

                if (!TextUtils.isEmpty(ageStr)) {
                    age = Integer.parseInt(ageStr);
                }
                if (databaseManager.getWritable().getVersion() > 1) {
                    UserData2 userData = new UserData2();
                    userData.setName(name);
                    userData.setAge(age);
                    userData.setSex(sex);
                    databaseManager.insert(TABLE_NAME, userData);
                }else {
                    UserData userData = new UserData();
                    userData.setName(name);
                    userData.setAge(age);
                    userData.setSex(sex);
                    databaseManager.insert(TABLE_NAME, userData);
                }
                showToast("insert");
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et1.getText().toString().trim();
                String ageStr = et2.getText().toString().trim();
                int age = 0;
                String sex = et3.getText().toString().trim();
                if (databaseManager.getWritable().getVersion() > 1) {
                    List<UserData2> userDatas = databaseManager.query(TABLE_NAME, UserData2.class, "name=?", new String[]{name});
                    if (!ListUtils.isEmpty(userDatas)) {
                        UserData2 userData = userDatas.get(0);
                        Log.e("-s-", userData.toString());
                        databaseManager.delete(TABLE_NAME, userData);
                        showToast("delete");
                    }
                }else {
                    List<UserData> userDatas = databaseManager.query(TABLE_NAME, UserData.class, "name=?", new String[]{name});
                    if (!ListUtils.isEmpty(userDatas)) {
                        UserData userData = userDatas.get(0);
                        Log.e("-s-", userData.toString());
                        databaseManager.delete(TABLE_NAME, userData);
                        showToast("delete");
                    }
                }
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et1.getText().toString().trim();
                String ageStr = et2.getText().toString().trim();
                int age = 0;
                String sex = et3.getText().toString().trim();
                if (!TextUtils.isEmpty(ageStr)) {
                    age = Integer.parseInt(ageStr);
                }

                if (databaseManager.getWritable().getVersion() > 1) {
                    List<UserData2> userDatas = databaseManager.query(TABLE_NAME, UserData2.class, "name=?", new String[]{name});
                    if (!ListUtils.isEmpty(userDatas)) {
                        UserData2 userData = userDatas.get(0);
                        userData.setAge(age);
                        userData.setSex(sex);
                        Log.e("-s-", userData.toString());
                        databaseManager.updata(TABLE_NAME, userData);
                        showToast("updata");
                    }
                }else {
                    List<UserData> userDatas = databaseManager.query(TABLE_NAME, UserData.class, "name=?", new String[]{name});
                    if (!ListUtils.isEmpty(userDatas)) {
                        UserData userData = userDatas.get(0);
                        userData.setAge(age);
                        userData.setSex(sex);
                        Log.e("-s-", userData.toString());
                        databaseManager.updata(TABLE_NAME, userData);
                        showToast("updata");
                    }
                }
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et1.getText().toString().trim();

                if (databaseManager.getWritable().getVersion() > 1) {
                    List<UserData2> all = databaseManager.queryAll(TABLE_NAME, UserData2.class);
                    StringBuilder builder = new StringBuilder("log\n");
                    for (UserData2 userData : all) {
                        builder.append(userData.toString()).append("\n");
                    }
                    tv1.setText(builder.toString());

                    List<UserData2> userDatas = databaseManager.query(TABLE_NAME, UserData2.class, "name=?", new String[]{name});
                    if (!ListUtils.isEmpty(userDatas)) {
                        UserData2 userData = userDatas.get(0);
                        Log.e("-s-", userData.toString());
                        tv2.setText(userData.get_ID()+"");
                        et1.setText(userData.getName());
                        et2.setText(userData.getAge()+"");
                        et3.setText(userData.getSex());
                        tv3.setText(userData.isRich()?"富裕":"贫穷");
                        showToast("query");
                    }else {
                        tv2.setText("id");
                        et1.setText("");
                        et2.setText("");
                        et3.setText("");
                        tv3.setText("");
                    }
                }else {
                    List<UserData> all = databaseManager.queryAll(TABLE_NAME, UserData.class);
                    StringBuilder builder = new StringBuilder("log\n");
                    for (UserData userData : all) {
                        builder.append(userData.toString()).append("\n");
                    }
                    tv1.setText(builder.toString());

                    List<UserData> userDatas = databaseManager.query(TABLE_NAME, UserData.class, "name=?", new String[]{name});
                    if (!ListUtils.isEmpty(userDatas)) {
                        UserData userData = userDatas.get(0);
                        Log.e("-s-", userData.toString());
                        tv2.setText(userData.get_ID()+"");
                        et1.setText(userData.getName());
                        et2.setText(userData.getAge()+"");
                        et3.setText(userData.getSex());
                        showToast("query");
                    }else {
                        tv2.setText("id");
                        et1.setText("");
                        et2.setText("");
                        et3.setText("");
                    }
                }
            }
        });
    }
}
