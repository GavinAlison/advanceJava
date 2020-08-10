package com.alison.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;

public class JsonTest {

    public static void main(String[] args) {
        String respJson = "";
        JSONObject.parseObject(respJson, new TypeReference<DataResponse<Map<String, Object>>>() {
        });
        String userJson = "";
        ArrayList<User> userList =
                JSONObject.parseObject(userJson, new TypeReference<ArrayList<User>>() {
                });
    }

    class User {

    }

    class DataResponse<T> {
        private RespStatus status;
        private T data;
        private long consume;
        private long responseTimestamp = Instant.now().toEpochMilli();
        private String reason;
        private Integer code;
    }


    @AllArgsConstructor
    @Getter
    enum RespStatus {
        ERROR_NONE_ADAPTER(4000, "数据适配器不存在"),
        ERROR_NO_AUTH(3100, "授权失败"),
        ERROR(3000, "失败"),
        ERROR_TIMEOUT(3200, "访问超时"),
        DATA_MISS(2000, "无命中，字段不存在但数据存在"),
        DATA_EMPTY(2100, "数据为空"),
        DATA_FREE(1200, "无费用数据"),
        DATA_CACHE(1100, "缓存查询"),
        DECISION_ASYNC(2200, "决策异步"),
        SUCCESS(1000, "查询成功");
        private int code;
        private String msg;
    }
}