package com.alison.kafka;

import org.junit.Test;

import java.time.LocalDateTime;

public class Demo {
    @Test
    public void test22(){
        String reason = "ERROR: value too long for type character varying(1)\n" +
                "  在位置：COPY stg_event_var_ccreditikifhtrjf, line 1, column field_value: \"0.05\"";
        System.out.println(reason.indexOf("\n"));
        System.out.println(reason.replace("\n", "$$"));
        System.out.println(LocalDateTime.now());
    }
}
