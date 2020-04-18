package com.alison.date;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description 时间格式
 */
@SuppressWarnings("ALL")
public enum DataTimeFormat {
    TimestampM(0, "时间戳(长整型毫秒)", "sss", true, "[\\d]{13}"),
    TimestampS(1, "时间戳（长整型秒）", "ss", true, "[\\d]{10}"),
    DataTimeFormat1(2, "", "yyyy年MM月dd日", true, "[\\d]{4}年[\\d]{2}月[\\d]{2}日"),
    DataTimeFormat2(3, "", "yyyy年MM月", true, "[\\d]{4}年[\\d]{2}月"),
    DataTimeFormat3(4, "", "yyyy-MM", true, "[\\d]{4}-[\\d]{2}"),
    DataTimeFormat4(5, "", "yyyy/MM", true, "[\\d]{4}/[\\d]{2}"),
    DataTimeFormat5(6, "", "yyyy-MM-dd", true, "[\\d]{4}-[\\d]{2}-[\\d]{2}"),
    DataTimeFormat6(7, "", "yyyy/MM/dd", true, "[\\d]{4}/[\\d]{2}/[\\d]{2}"),
    DataTimeFormat7(8, "", "MM/dd/yyyy", true, "[\\d]{2}/[\\d]{2}/[\\d]{4}"),
    DataTimeFormat8(9, "", "MM/dd/yy", true, "[\\d]{2}/[\\d]{2}/[\\d]{2}"),
    DataTimeFormat9(10, "", "yyyyMMdd", true, "[\\d]{8}"),
    DataTimeFormat10(11, "", "yyyyMMddHHmmss", true, "[\\d]{14}"),
    DataTimeFormat11(12, "", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", true, "[\\d]{4}-[\\d]{2}-[\\d]{2}T[\\d]{2}:[\\d]{2}:[\\d]{2}.[\\d]{3}\\+[\\d]{4}"),
    DataTimeFormat12(13, "", "yyyyMMddHHmmss.SSS", false, ""),
    DataTimeFormat13(14, "", "yyyy-MM-dd HH:mm:ss.SSS", true, "[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}.[\\d]{3}"),
    DataTimeFormat14(15, "", "yyyy/MM/dd HH:mm:ss", true, "[\\d]{4}/[\\d]{2}/[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}"),
    DataTimeFormat15(16, "", "yyyy/MM/dd HH:mm", true, "[\\d]{4}/[\\d]{2}/[\\d]{2} [\\d]{2}:[\\d]{2}"),
    DataTimeFormat16(17, "", "yyyy年MM月dd日HH时", true, "[\\d]{4}年[\\d]{2}月[\\d]{2}日[\\d]{2}时"),
    DataTimeFormat17(18, "", "yyyy年MM月dd日HH时mm分", true, "[\\d]{4}年[\\d]{2}月[\\d]{2}日[\\d]{2}时[\\d]{2}分"),
    DataTimeFormat18(19, "", "yyyy年MM月dd日HH时mm分ss秒", true, "[\\d]{4}年[\\d]{2}月[\\d]{2}日[\\d]{2}时[\\d]{2}分[\\d]{2}秒"),
    DataTimeFormat19(20, "", "MM月dd日", true, ""),
    DataTimeFormat20(21, "", "MM/dd", true, ""),
    DataTimeFormat21(22, "月日", "MMdd", true, ""),
    DataTimeFormat22(23, "", "MM-dd", true, ""),
    DataTimeFormat23(24, "", "HH:mm", true, ""),
    DataTimeFormat24(25, "", "HH时mm分", true, ""),
    DataTimeFormat25(26, "", "HH时mm分ss秒", true, ""),
    DataTimeFormat26(27, "", "HH:mm:ss", true, ""),
    DataTimeFormat27(28, "", "yyyy.MM.dd", true, ""),
    DataTimeFormat28(29, "", "yyyy", true, ""),
    DataTimeFormat29(30, "", "yyyy-MM-dd HH:mm:ss", true, "[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}"),
    DataTimeFormat30(31, "", "yyyy-MM-dd'T'HH:mm:ss", true, "[\\d]{4}-[\\d]{2}-[\\d]{2}T[\\d]{2}:[\\d]{2}:[\\d]{2}"),
    DataTimeFormat31(32, "", "yyyy-MM-dd'T'HH:mm:ss.SSS", true, "[\\d]{4}-[\\d]{2}-[\\d]{2}T[\\d]{2}:[\\d]{2}:[\\d]{2}.[\\d]{1,3}"),
    YearMonthFormat(100, "", "yyyyMM", true, "[\\d]{6}"),
    YearFormat(101, "yyyy（年份）", "yyyy", false, ""),
    QuarterFormat(102, "quarter（当年的第几个季度）", "quarter", false, ""),
    MonthFormat(103, "MM（当年月份）", "MM", false, ""),
    DayFormat(104, "dd(当月的日期)", "dd", false, ""),
    HourFormat(105, "HH（当天的小时)", "HH", false, ""),
    MinFormat(106, "mm（当前的分钟", "mm", false, ""),
    WeekOfMonthFormat(107, "week of month(当月的第几个星期)", "weekOfMonth", false, ""),
    WeekFormat1(108, "week(自然周，数字)", "week", false, ""),
    WeekFormat2(109, "星期EE(格式：星期xx)", "星期EE", false, ""),
    WeekFormat3(110, "周EE(格式：周xx)", "周EE", false, ""),
    DayOfYearFormat(111, "day of year(当年内的天数)", "dayOfYear", false, ""),
    WeekOfYearFormat(112, "week of year(全年内第几个星期)", "weekOfYear", false, ""),
    YYYYMMDDHHMM(113, "整型时间分钟", "yyyyMMddHHmm", true, "[\\d]{12}"),
    YYYYMMDDHH(113, "整型时间小时", "yyyyMMddHH", true, "[\\d]{10}");


    private Integer id;
    private String description;
    private String format;
    private boolean isFormat;
    //正则格式
    private String regex;

    DataTimeFormat(Integer id, String description, String format, boolean isFormat, String regex) {
        this.id = id;
        this.description = description;
        this.format = format;
        this.isFormat = isFormat;
        this.regex = regex;
    }

    public boolean match(String timeString) {
        return timeString.matches(regex);
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean getIsFormat() {
        return isFormat;
    }

    public void setIsFormat(boolean format) {
        isFormat = format;
    }
}
