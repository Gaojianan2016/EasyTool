package com.gjn.easytool.easynet;

import com.alibaba.fastjson.serializer.SerializeConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Creator: Gjn
 * Time: 2020/3/18 15:54
 * Eamil: 732879625@qq.com
 **/
public final class FastJsonConverterFactory extends Converter.Factory {

    public static FastJsonConverterFactory create() {
        return create(SerializeConfig.getGlobalInstance());
    }

    public static FastJsonConverterFactory create(SerializeConfig serializeConfig) {
        if (serializeConfig == null) throw new NullPointerException("serializeConfig == null");
        return new FastJsonConverterFactory(serializeConfig);
    }

    private final SerializeConfig serializeConfig;

    private FastJsonConverterFactory(SerializeConfig serializeConfig) {
        this.serializeConfig = serializeConfig;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
        Retrofit retrofit) {
        return new FastJsonResponseBodyConvert<>(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
        Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestBodyConverter<>(serializeConfig);
    }
}
