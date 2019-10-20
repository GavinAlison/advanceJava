package com.alison.rpc2.common;

import java.io.IOException;

/**
 * 市面上序列化协议很多，比如jdk自带的，Google的protobuf，kyro、Hessian等，
 * 只要不选择jdk自带的序列化方法，（因为其性能太差，序列化后产生的码流太大），
 * 其他方式其实都可以，这里为了方便起见，选用JSON作为序列化协议，使用fastjson作为JSON框架
 */
public interface Serializer {
    /**
     * java对象转换为二进制
     *
     * @return
     */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * 二进制转换成java对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;

}
