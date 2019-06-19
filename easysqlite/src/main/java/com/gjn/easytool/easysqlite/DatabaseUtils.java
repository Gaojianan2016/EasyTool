package com.gjn.easytool.easysqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.gjn.easytool.utils.ReflexUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2019/6/19 14:31
 */

public class DatabaseUtils {

    public static String createTableSql(String tableName, Class clz) {
        StringBuilder builder = new StringBuilder("create table if not exists ");
        builder.append(tableName).append("(").append("_ID integer primary key autoincrement not null , ");
        for (Field field : clz.getDeclaredFields()) {
            if (!field.getName().contains("_ID")) {
                builder.append(field.getName()).append(" ")
                        .append(typeJava2Sqlite(field.getType()))
                        .append(" , ");
            }
        }
        builder.delete(builder.length() - 3, builder.length()).append(")");
        return builder.toString();
    }

    public static String dropTableSql(String tableName) {
        return "drop table if exists " + tableName;
    }

    public static String renameTableSql(String tableName, String tableName2){
        return "alter table " + tableName + " rename to " + tableName2;
    }

    public static List<String> getSameTableBeans(Class clz1, Class clz2){
        List<String> beans = new ArrayList<>();
        for (Field f1 : clz1.getDeclaredFields()) {
            for (Field f2 : clz2.getDeclaredFields()) {
                if (f1.getName().equals(f2.getName())) {
                    beans.add(f1.getName());
                }
            }
        }
        return beans;
    }

    public static String copyTableInsertSql(String tableName1, String tableName2, Class clz1, Class clz2){
        List<String> beans = getSameTableBeans(clz1, clz2);
        StringBuilder builder = new StringBuilder("insert into ");
        builder.append(tableName1).append("(");
        for (String bean : beans) {
            builder.append(bean).append(", ");
        }
        builder.delete(builder.length() - 2, builder.length())
                .append(") select ");
        for (String bean : beans) {
            builder.append(bean).append(", ");
        }
        builder.delete(builder.length() - 2, builder.length())
                .append(" from ").append(tableName2);
        return builder.toString();
    }

    public static ContentValues getContentValues(Object obj){
        Class clz = obj.getClass();
        ContentValues values = new ContentValues();
        for (Field field : clz.getDeclaredFields()) {
            if (!field.getName().contains("_ID")) {
                putValues(obj, field, values);
            }
        }
        return values;
    }

    public static <T> T getNewInstance(Class<T> clz, Cursor cursor) throws Exception {
        T t = clz.newInstance();
        for (Field field : clz.getDeclaredFields()) {
            setValues(t, field, cursor);
        }
        return t;
    }

    public static String typeJava2Sqlite(Class<?> type) {
        String result;
        switch (type.getSimpleName().toLowerCase()) {
            case "int":
            case "integer":
            case "long":
                result = "INTEGER";
                break;
            case "double":
            case "float":
                result = "REAL";
                break;
            case "string":
            case "boolean":
            default:
                result = "TEXT";
        }
        return result;
    }


    public static <T> void setValues(T t, Field field, Cursor cursor) {
        int column = cursor.getColumnIndex(field.getName());
        if (column >= 0) {
            switch (field.getType().getSimpleName().toLowerCase()) {
                case "string":
                    ReflexUtils.setValue(t, field, cursor.getString(column));
                    break;
                case "int":
                case "integer":
                    ReflexUtils.setValue(t, field, cursor.getInt(column));
                    break;
                case "boolean":
                    if ("1".equals(cursor.getString(cursor.getColumnIndex(field.getName())))) {
                        ReflexUtils.setValue(t, field, true);
                    } else {
                        ReflexUtils.setValue(t, field, false);
                    }
                    break;
                case "float":
                    ReflexUtils.setValue(t, field, cursor.getFloat(column));
                    break;
                case "double":
                    ReflexUtils.setValue(t, field, cursor.getDouble(column));
                    break;
                case "long":
                    ReflexUtils.setValue(t, field, cursor.getLong(column));
                    break;
                default:
            }
        }
    }

    public static void putValues(Object obj, Field field, ContentValues values) {
        switch (field.getType().getSimpleName().toLowerCase()) {
            case "string":
                values.put(field.getName(), (String) ReflexUtils.getValue(obj, field));
                break;
            case "int":
            case "integer":
                values.put(field.getName(), (int) ReflexUtils.getValue(obj, field));
                break;
            case "boolean":
                values.put(field.getName(), (boolean) ReflexUtils.getValue(obj, field));
                break;
            case "float":
                values.put(field.getName(), (float) ReflexUtils.getValue(obj, field));
                break;
            case "double":
                values.put(field.getName(), (double) ReflexUtils.getValue(obj, field));
                break;
            case "long":
                values.put(field.getName(), (long) ReflexUtils.getValue(obj, field));
                break;
            default:
        }
    }

}
