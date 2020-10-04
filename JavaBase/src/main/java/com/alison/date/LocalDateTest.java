package com.alison.date;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTest {

    @Test
    public void test() {

        String val = "1599560532000";
        ZoneId zone = ZoneId.systemDefault();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDate localDate = LocalDate.parse(val, dateTimeFormatter);
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        System.out.println(Date.from(instant));
    }
}
