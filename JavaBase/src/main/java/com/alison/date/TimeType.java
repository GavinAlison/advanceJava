package com.alison.date;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description
 */
public enum  TimeType {
    SECOND("s"),
    MIN("m"),
    HOUR("h"),
    DAY("d"),
    MOUTH("M"),
    YEAR("y");

    private String code;

    TimeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
