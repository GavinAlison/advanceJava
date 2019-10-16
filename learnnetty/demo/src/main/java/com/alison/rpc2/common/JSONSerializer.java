package com.alison.rpc2.common;

import com.alibaba.fastjson.JSON;

public class JSONSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
