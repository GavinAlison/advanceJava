package com.alison.rpc;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClassInfo   implements Serializable {
    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] types;

    /**
     * 参数列表
     */
    private Object[] objects;
}
