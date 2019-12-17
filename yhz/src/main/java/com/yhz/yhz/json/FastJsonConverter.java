package com.yhz.yhz.json;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

/**
 * @description: FastJsonConverter (kalle转换器)
 * @author: Y.hz
 * @time: 2019/12/17 16:26
 */
public class FastJsonConverter implements Converter {

    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
        S succeedData = JSON.parseObject(response.body().toString(),succeed);
        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .build();
    }
}
