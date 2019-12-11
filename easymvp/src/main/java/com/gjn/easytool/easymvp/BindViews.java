package com.gjn.easytool.easymvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creator: Gjn
 * Time: 2019/12/11 11:33
 * Eamil: 732879625@qq.com
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindViews {
    Class<?>[] value();
}
