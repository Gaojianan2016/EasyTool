package com.gjn.easytool.utils;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author gjn
 * @time 2019/4/3 16:05
 */

public class ReflexUtils {
    private static final String TAG = "ReflexUtils";

    public static void printInfo(Class clz) {
        Log.e(TAG, "=========================基础=========================");
        Log.e(TAG, "名称 = " + clz.getName());
        Log.e(TAG, "简称 = " + clz.getSimpleName());
        Log.e(TAG, "冠名 = " + clz.getCanonicalName());
        Log.e(TAG, "修饰符 = " + Modifier.toString(clz.getModifiers()));
        Log.e(TAG, "父类 = " + clz.getSuperclass());
        Log.e(TAG, "=========================接口=========================");
        for (Class c : clz.getInterfaces()) {
            Log.e(TAG, "" + c);
        }
        Log.e(TAG, "======================注解(继承)======================");
        for (Annotation annotation : clz.getAnnotations()) {
            Log.e(TAG, "" + annotation);
        }
        Log.e(TAG, "======================注解(当前)======================");
        for (Annotation annotation : clz.getDeclaredAnnotations()) {
            Log.e(TAG, "" + annotation);
        }
        Log.e(TAG, "====================构造函数(继承)====================");
        for (Constructor constructor : clz.getConstructors()) {
            Log.e(TAG, "" + constructor);
        }
        Log.e(TAG, "====================构造函数(当前)====================");
        for (Constructor constructor : clz.getDeclaredConstructors()) {
            Log.e(TAG, "" + constructor);
        }
        Log.e(TAG, "======================方法(继承)======================");
        for (Method method : clz.getMethods()) {
            Log.e(TAG, "" + method);
        }
        Log.e(TAG, "======================方法(当前)======================");
        for (Method method : clz.getDeclaredMethods()) {
            Log.e(TAG, "" + method);
        }
        Log.e(TAG, "======================参数(继承)======================");
        for (Field field : clz.getFields()) {
            Log.e(TAG, "" + field);
        }
        Log.e(TAG, "======================参数(当前)======================");
        for (Field field : clz.getDeclaredFields()) {
            Log.e(TAG, "" + field);
        }
    }

    public static <T> Class<T> getClass(String name) {
        Class<T> clz = null;
        try {
            clz = (Class<T>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clz;
    }

    public static <T> T createObj(String name) {
        Class<T> clz = getClass(name);
        return createObj(clz, null, null);
    }

    public static <T> T createObj(String name, Class<?>[] parameterTypes, Object[] initargs) {
        Class<T> clz = getClass(name);
        return createObj(clz, parameterTypes, initargs);
    }

    public static <T> T createObj(Class<T> clz) {
        return createObj(clz, null, null);
    }

    public static <T> T createObj(Class<T> clz, Class<?>[] parameterTypes, Object[] initargs) {
        if (clz == null) {
            Log.e(TAG, "class is null.");
            return null;
        }
        if (!isStaticPublic(clz)) {
            Log.e(TAG, "class is not public.");
            return null;
        }
        try {
            Constructor constructor = clz.getConstructor(parameterTypes);
            T t = (T) constructor.newInstance(initargs);
            return t;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "找不到方法", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "非法访问", e);
        } catch (InstantiationException e) {
            Log.e(TAG, "实例化失败", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "调用异常", e);
        }
        return null;
    }

    public static List<Field> getDeclaredFields(Object o){
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return null;
        }
        return Arrays.asList(o.getClass().getDeclaredFields());
    }

    public static Object doDeclaredMethod(Object o, String name) {
        return doDeclaredMethod(o, name, null, null);
    }

    public static Object doDeclaredMethod(Object o, String name, Class<?>[] parameterTypes, Object[] args) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return null;
        }
        try {
            Method method = o.getClass().getDeclaredMethod(name, parameterTypes);
            if (!isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            return method.invoke(o, args);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "找不到方法", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "非法访问", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "调用异常", e);
        }
        return null;
    }

    public static void setDeclaredField(Object o, String name, Object value) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return;
        }
        try {
            Field field = o.getClass().getDeclaredField(name);
            setField(o, field, value);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "找不到参数", e);
        }
    }

    public static void setField(Object o, String name, Object value) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return;
        }
        try {
            Field field = o.getClass().getField(name);
            setField(o, field, value);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "找不到参数", e);
        }
    }

    public static void setField(Object o, Field field, Object value) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return;
        }
        try {
            if (!isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            field.set(o, value);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "非法访问", e);
        }
    }

    public static Object getJsonValue(Object o, Field field){
        String methodName;
        if (field.getType().getSimpleName().toLowerCase().equals("boolean")) {
            methodName = StringUtils.gsMethodName("is", field.getName());
        } else {
            methodName = StringUtils.gsMethodName("get", field.getName());
        }
        return doDeclaredMethod(o, methodName);
    }

    public static void setJsonValue(Object o, Field field, Object value){
        doDeclaredMethod(o, StringUtils.gsMethodName("set", field.getName()),
                new Class[]{field.getType()}, new Object[]{value});
    }

    public static Object getValue(Object o, Field field){
        String methodName;
        if (field.getType().getSimpleName().toLowerCase().equals("boolean")) {
            methodName = StringUtils.gsMethodName("is", field.getName()
                    .replace("is", ""));
        } else {
            methodName = StringUtils.gsMethodName("get", field.getName());
        }
        return doDeclaredMethod(o, methodName);
    }

    public static void setValue(Object o, Field field, Object value){
        String methodName;
        if (field.getType().getSimpleName().toLowerCase().equals("boolean")) {
            methodName = StringUtils.gsMethodName("set",
                    field.getName().replace("is", ""));
        }else {
            methodName = StringUtils.gsMethodName("set", field.getName());
        }
        doDeclaredMethod(o, methodName, new Class[]{field.getType()}, new Object[]{value});
    }

    public static boolean isPublic(int modifiers) {
        return Modifier.toString(modifiers).contains("public");
    }

    public static boolean isStaticPublic(int modifiers) {
        return Modifier.toString(modifiers).contains("public static");
    }

    //判断Class是否能被实例化
    public static boolean isStaticPublic(Class clz) {
        if (clz.getName().contains("$")) {
            return isStaticPublic(clz.getModifiers());
        } else {
            return isPublic(clz.getModifiers());
        }
    }
}
