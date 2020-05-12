package com.gjn.easytool.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2019/4/11 13:55
 */

public class AnnotationsUtils {

    public static boolean checkAnnotations(Object obj, Class<? extends Annotation> annotationCls){
        Class<?> clazz = obj.getClass();
        clazz.getAnnotations();
        return clazz.isAnnotationPresent(annotationCls);
    }

    public static boolean checkAnnotations(Field field, Class<? extends Annotation> annotationCls){
        field.getAnnotations();
        return field.isAnnotationPresent(annotationCls);
    }

    public static <T extends Annotation> T getAnnotations(Object obj, Class<T> annotationCls){
        if (checkAnnotations(obj, annotationCls)) {
            return obj.getClass().getAnnotation(annotationCls);
        }
        return null;
    }

    public static List<Field> getField(Object obj, Class<? extends Annotation> annotationCls){
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> result = new ArrayList<>();
        for (Field field : fields) {
            if (checkAnnotations(field, annotationCls)) {
                result.add(field);
            }
        }
        return result;
    }
}
