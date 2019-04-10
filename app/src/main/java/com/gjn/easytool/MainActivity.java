package com.gjn.easytool;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjn.easytool.dialoger.EasyDialogManager;
import com.gjn.easytool.dialoger.base.BaseDialogFragment;
import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.toaster.EasyToast;
import com.gjn.easytool.utils.QRUtils;
import com.gjn.easytool.utils.ReflexUtils;
import com.gjn.easytool.utils.StringUtils;
import com.gjn.easytool.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    String json = "{\"code\": 0,\"data\": {\"content\": [" +
            "      {\n" +
            "\t\"id\": 1252," +
            "        \"createdDate\": 1554113462000," +
            "        \"startedDate\": 1554169796000," +
            "        \"deliveredDate\": 1554170241000," +
            "        \"size\": 1" +
            "      }," +
            "      {" +
            "        \"id\": 1202," +
            "        \"createdDate\": 1554105072000," +
            "        \"startedDate\": 1554105333000," +
            "        \"deliveredDate\": 1554105336000," +
            "        \"size\": 3" +
            "      }," +
            "      {" +
            "        \"id\": 1105," +
            "        \"createdDate\": 1554090949000," +
            "        \"startedDate\": 1554094482000," +
            "        \"deliveredDate\": 1554105049000," +
            "        \"size\": 1" +
            "      }," +
            "      {" +
            "        \"id\": 852," +
            "        \"createdDate\": 1553840413000," +
            "        \"startedDate\": 1554085455000," +
            "        \"deliveredDate\": 1554085459000," +
            "        \"size\": 1" +
            "      }," +
            "      {" +
            "        \"id\": 802," +
            "        \"createdDate\": 1553674650000," +
            "        \"startedDate\": 1553839790000," +
            "        \"deliveredDate\": 1553839795000," +
            "        \"size\": 2" +
            "      }," +
            "      {" +
            "        \"id\": 753," +
            "        \"createdDate\": 1553653298000," +
            "        \"startedDate\": 1553654597000," +
            "        \"deliveredDate\": 1553654611000," +
            "        \"size\": 2" +
            "      }," +
            "      {" +
            "        \"id\": 752," +
            "        \"createdDate\": 1553651370000," +
            "        \"startedDate\": 1553652693000," +
            "        \"deliveredDate\": 1553652744000," +
            "        \"size\": 3" +
            "      }," +
            "      {" +
            "        \"id\": 654," +
            "        \"createdDate\": 1553584350000," +
            "        \"startedDate\": 1553584722000," +
            "        \"deliveredDate\": 1553584765000," +
            "        \"size\": 1" +
            "      }" +
            "    ]," +
            "    \"total\": 16," +
            "    \"pageNumber\": 1," +
            "    \"pageSize\": 10," +
            "    \"totalPages\": 2" +
            "  }" +
            "}";
    private Activity activity;
    private ImageView ivQr;
    private EasyDialogManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        ivQr = findViewById(R.id.iv_qr);
        manager = new EasyDialogManager(this);
        click();
    }

    private void click() {
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyLog.json(json);
                try {
                    JSONObject object = new JSONObject();
                    object.put("name", "张三");
                    JSONObject index = new JSONObject();
                    index.put("sex", "男");
                    index.put("phone", "17745645645");
                    object.put("index", index);
                    object.put("married", true);
                    EasyLog.json(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                User user = new User("李四", "女", 18);
                EasyLog.json(user);
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.show(activity, "点击Toast", Toast.LENGTH_SHORT);
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyToast.showInfo(activity, "我是提示");
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "1234_";
                EasyLog.e(StringUtils.changeLastStr(str, "8455"));
                EasyLog.e(StringUtils.deleteLastStr(str));
                EasyLog.e(StringUtils.getFirstStr(str));
                EasyLog.e(StringUtils.getLastStr(str));
                long l = 154578454;
                EasyLog.e(StringUtils.getSizeStr(l));
                double d = 771.45154;
                EasyLog.e(StringUtils.doubleFormat(d));
                EasyLog.e(StringUtils.doubleFormat(d, 3));
                long time = System.currentTimeMillis();
                EasyLog.e(StringUtils.dataFormat(time));

                String s = "你好";
                String s1 = "asdasd";
                String s2 = "151‘’asd";
                EasyLog.e(s + " -> " + StringUtils.hasChinese(s));
                EasyLog.e(s1 + " -> " + StringUtils.hasChinese(s1));
                EasyLog.e(s2 + " -> " + StringUtils.hasChinese(s2));

                EasyLog.e(s + " -> " + StringUtils.isChinese(s));
                EasyLog.e(s1 + " -> " + StringUtils.isChinese(s1));
                EasyLog.e(s2 + " -> " + StringUtils.isChinese(s2));

                long time1 = time - 3000;
                long time2 = time - (1000 * 40);
                long time3 = time - (1000 * 60 * 8);
                long time4 = time - (1000 * 60 * 60 * 3);
                long time5 = time4 - (60000 * 60 * 18);
                long time6 = time5 - (60000 * 60 * 24);
                long time7 = time6 - (60000 * 60 * 8);
                EasyLog.e(StringUtils.systemFormat(time1));
                EasyLog.e(StringUtils.systemFormat(time2));
                EasyLog.e(StringUtils.systemFormat(time3));
                EasyLog.e(StringUtils.systemFormat(time4));
                EasyLog.e(StringUtils.systemFormat(time5));
                EasyLog.e(StringUtils.systemFormat(time6));
                EasyLog.e(StringUtils.systemFormat(time7));
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = QRUtils.str2bitmap("sn_20190404153301");
                    ivQr.setImageBitmap(bitmap);
                    EasyToast.show(activity, "生成成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ViewUtils.getImageViewBitmap(ivQr);
                try {
                    String code = QRUtils.bitmap2str(bitmap);
                    EasyLog.e(code);
                    EasyToast.show(activity, "解析成功\n" + code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ReflexUtils.printInfo(User.class);
//                EasyLog.e("-----------------------");
                User user = ReflexUtils.createObj(User.class,
                        new Class[]{String.class, String.class, int.class},
                        new Object[]{"王五", "女", 30});
                if (user != null) {
                    EasyLog.e(user.toString());
                }
                EasyLog.e("-----------------------");
                user = ReflexUtils.createObj(User.class);
                for (Field field : ReflexUtils.getDeclaredFields(user)) {
                    switch (field.getName()) {
                        case "name":
                            ReflexUtils.setValue(user, field, "名称");
                            break;
                        case "sex":
                            ReflexUtils.setValue(user, field, "性别");
                            break;
                        case "age":
                            ReflexUtils.setValue(user, field, 18);
                            break;
                        case "m":
                            ReflexUtils.setValue(user, field, true);
                            break;
                        case "f":
                            ReflexUtils.setValue(user, field, 13.5f);
                            break;
                        case "d":
                            ReflexUtils.setValue(user, field, 66.6);
                            break;
                        default:
                    }
                }
                if (user != null) {
                    EasyLog.e(user.toString());
                }
            }
        });
        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.showAndroidDialog("提示", "默认Dialog", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EasyToast.show(activity, "默认Dialog");
                    }
                });

                manager.showEasyNormalDialog("EasyNormalDialog", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EasyToast.show(activity, "EasyNormalDialog");
                    }
                });

                manager.showEasyOneBtnDialog("EasyOneBtnDialog", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EasyToast.show(activity, "EasyOneBtnDialog");
                    }
                });

                manager.showEasyDelayDialog("EasyDelayDialog", 3, "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EasyToast.show(activity, "EasyDelayDialog");
                    }
                });

                manager.showEasyInputDialog("EasyInputDialog", "提交", 10, new EasyDialogManager.EasyInputListener() {
                    @Override
                    public void confirm(View v, Editable msg, int maxSize) {
                        EasyToast.show(activity, "EasyInputDialog");
                    }
                });

                manager.showSmallLoadingDialog();
                manager.showMiddleLoadingDialog();
                manager.showLargeLoadingDialog();
                manager.showMiddleLoadingDialog();
                manager.showSmallLoadingDialog();

            }
        });
        findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (BaseDialogFragment dialogFragment : manager.getFragments()) {
                    Log.e("-s-", "d = " + dialogFragment);
                }
            }
        });
    }

    public static class User {
        String name;
        String sex;
        int age;
        boolean m;
        float f;
        double d;

        public User() {
        }

        public User(String name, String sex, int age) {
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isM() {
            return m;
        }

        public void setM(boolean m) {
            this.m = m;
        }

        public float getF() {
            return f;
        }

        public void setF(float f) {
            this.f = f;
        }

        public double getD() {
            return d;
        }

        public void setD(double d) {
            this.d = d;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    ", m=" + m +
                    ", f=" + f +
                    ", d=" + d +
                    '}';
        }
    }
}
