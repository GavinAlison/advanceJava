package com.alison.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description
 */
public class DateUtils {
    // java8 时间转换
    // 1. 时间戳转str,
    // 2. 时间戳转日期
    // 3. str 转 时间
    // 4. str 转 时间戳
    // 5. str 获取年，月， 日，时， 分，秒，毫秒
    // 6. 日期添加 秒， 分，时，周，年，月，日
    // 7. 比较两个日期
    // 8. 日期 获取年，月，日，时，分，秒，毫秒


    // 将LocalDateTime转为自定义的时间格式的字符串
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    // 将long类型的timestamp转为LocalDateTime
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    //    将LocalDateTime转为long类型的timestamp
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    //将某时间字符串转为自定义时间格式的LocalDateTime
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    // 1. 时间戳转str,
    public static String timestamp2str(long timestamp, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).format(df);
    }

    //    2. 时间戳转日期
    public static Date timstamp2Date(long timestamp) {
        return new Date(timestamp);
    }

    // 3. str 转 时间
    public static Date str2Date(String time, String format) {
        return Date.from(LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format)).toInstant(ZoneOffset.ofHours(8)));
    }

    //4. str 转 时间戳
    public static long str2long(String time, String format) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format)).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    //    5. str 获取年，月， 日，时， 分，秒，毫秒
    public static long str2Mon(String time, String format, ChronoField field) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format)).get(field);
    }

    //    6. 日期添加 秒， 分，时，周，年，月，日
    public static LocalDateTime datePlus(long timestamp, int num, TemporalUnit unit) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).plus(num, unit);
    }

    //    7. 比较两个日期
    public static boolean diff(long timestamp, long endTimestamp) {
        LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTimestamp), ZoneId.systemDefault());
        return startTime.isBefore(endTime);
    }

    //    8. 日期 获取年，月，日，时，分，秒，毫秒
    public static long str2Mon(long timestamp, String format, ChronoField field) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).get(field);
    }

    public static void main(String[] args) {
        System.out.println(getTimestampOfDateTime(LocalDateTime.now()));
        System.out.println(parseStringToDateTime("2020-04-18 22:00:10", "yyyy-MM-dd HH:mm:ss"));
    }
}
