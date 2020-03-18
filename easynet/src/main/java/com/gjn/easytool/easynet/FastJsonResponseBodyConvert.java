package com.gjn.easytool.easynet;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Creator: Gjn
 * Time: 2020/3/18 16:01
 * Eamil: 732879625@qq.com
 **/
final class FastJsonResponseBodyConvert<T> implements Converter<ResponseBody, T> {

    private Type type;

    public FastJsonResponseBodyConvert(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return JSON.parseObject(value.string(), type);
    }
}
