package com.alison.date;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description 函数日期转换方式枚举
 */
public enum ChangeType {

    ADD(0, "增加"),
    KEEP(1, "不变"),
    CUT(2, "减少"),
    ;

    private Integer id;
    private String description;

    ChangeType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
