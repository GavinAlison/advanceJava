package com.alison.kafka;

import lombok.Getter;

/**
 * @description: DML操作类型
 * @author: yuanchangyou
 * @create: 2019-10-28 11:14
 **/
@Getter
public enum DMLOperateType {

    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),


    BOOTSTRAP_START("bootstrap-start"),

    BOOTSTRAP_INSERT("bootstrap-insert"),

    BOOTSTRAP_COMPLETE("bootstrap-complete");

    private String desc;

    public String getDesc() {
        return desc;
    }

    DMLOperateType(String desc) {
        this.desc = desc;
    }

    public static DMLOperateType getOperateTypeByName(String type) {

        for (DMLOperateType ddlOperateType : DMLOperateType.values()) {
            if (type.equals(ddlOperateType.desc)) {
                return ddlOperateType;
            }
        }
        throw new RuntimeException("数据同步，未找到对应的DML操作类型");
    }

}
