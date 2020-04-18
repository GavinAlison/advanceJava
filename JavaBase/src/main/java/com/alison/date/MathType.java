package com.alison.date;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description
 */
public enum MathType {
    ADD("+"),
    CUT("-");


    private String code;

    MathType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
