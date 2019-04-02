package com.gjn.easytool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gjn.easytool.logger.EasyLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        User user = new User("李四","女", 18);
        EasyLog.json(user);
    }

    private class User{
        String name;
        String sex;
        int age;

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
    }
}
