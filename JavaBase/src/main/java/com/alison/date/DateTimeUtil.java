package com.alison.date;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description
 */


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * 时间工具类
 * Created by liwenchao on 2017/9/8.
 */
@Slf4j
public class DateTimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);


    public static Instant cal(Instant source, ChangeType changeType, Integer count, TimeSpliceUnit timeType) {
        if (ChangeType.ADD == changeType) {
            switch (timeType) {
                case SECOND:
                    return source.plus(count, ChronoUnit.SECONDS);
                case MIN:
                    return source.plus(count, ChronoUnit.MINUTES);
                case HOUR:
                    return source.plus(count, ChronoUnit.HOURS);
                case DAY:
                    return LocalDateTime.ofInstant(source, ZoneId.systemDefault()).plus(count, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant();
                case WEEK:
                    return LocalDateTime.ofInstant(source, ZoneId.systemDefault()).plus(count, ChronoUnit.WEEKS).atZone(ZoneId.systemDefault()).toInstant();
                case MONTH:
                    return LocalDateTime.ofInstant(source, ZoneId.systemDefault()).plus(count, ChronoUnit.MONTHS).atZone(ZoneId.systemDefault()).toInstant();
                default:
                    return source;
            }
        }
        if (ChangeType.CUT == changeType) {
            switch (timeType) {
                case SECOND:
                    return source.minus(count, ChronoUnit.SECONDS);
                case MIN:
                    return source.minus(count, ChronoUnit.MINUTES);
                case HOUR:
                    return source.minus(count, ChronoUnit.HOURS);
                case DAY:
                    return LocalDateTime.ofInstant(source, ZoneId.systemDefault()).minus(count, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant();
                case WEEK:
                    return LocalDateTime.ofInstant(source, ZoneId.systemDefault()).minus(count, ChronoUnit.WEEKS).atZone(ZoneId.systemDefault()).toInstant();
                case MONTH:
                    return LocalDateTime.ofInstant(source, ZoneId.systemDefault()).minus(count, ChronoUnit.MONTHS).atZone(ZoneId.systemDefault()).toInstant();
                default:
                    return source;
            }
        }
        return source;
    }

    public static LocalDate cal(LocalDate source, ChangeType changeType, Integer count, TimeSpliceUnit timeType) {
        if (ChangeType.ADD == changeType) {
            switch (timeType) {
                case DAY:
                    return source.plus(count, ChronoUnit.DAYS);
                case WEEK:
                    return source.plus(count, ChronoUnit.WEEKS);
                case MONTH:
                    return source.plus(count, ChronoUnit.MONTHS);
                default:
                    return source;
            }
        }
        if (ChangeType.CUT == changeType) {
            switch (timeType) {
                case DAY:
                    return source.minus(count, ChronoUnit.DAYS);
                case WEEK:
                    return source.minus(count, ChronoUnit.WEEKS);
                case MONTH:
                    return source.minus(count, ChronoUnit.MONTHS);
                default:
                    return source;
            }
        }
        return source;
    }

    public static LocalTime cal(LocalTime source, ChangeType changeType, Integer count, TimeSpliceUnit timeType) {
        if (ChangeType.ADD == changeType) {
            switch (timeType) {
                case SECOND:
                    return source.plus(count, ChronoUnit.SECONDS);
                case MIN:
                    return source.plus(count, ChronoUnit.MINUTES);
                case HOUR:
                    return source.plus(count, ChronoUnit.HOURS);
                default:
                    return source;
            }
        }
        if (ChangeType.CUT == changeType) {
            switch (timeType) {
                case SECOND:
                    return source.minus(count, ChronoUnit.SECONDS);
                case MIN:
                    return source.minus(count, ChronoUnit.MINUTES);
                case HOUR:
                    return source.minus(count, ChronoUnit.HOURS);
                default:
                    return source;
            }
        }
        return source;
    }

    public static Temporal cal(Temporal source, ChangeType changeType, Integer count, TimeSpliceUnit timeType) {
        if (ChangeType.ADD == changeType) {
            switch (timeType) {
                case SECOND:
                    return source.plus(count, ChronoUnit.SECONDS);
                case MIN:
                    return source.plus(count, ChronoUnit.MINUTES);
                case HOUR:
                    return source.plus(count, ChronoUnit.HOURS);
                case DAY:
                    return source.plus(count, ChronoUnit.DAYS);
                case WEEK:
                    return source.plus(count, ChronoUnit.WEEKS);
                case MONTH:
                    return source.plus(count, ChronoUnit.MONTHS);
                default:
                    return source;
            }
        }
        if (ChangeType.ADD == changeType) {
            switch (timeType) {
                case SECOND:
                    return source.minus(count, ChronoUnit.SECONDS);
                case MIN:
                    return source.minus(count, ChronoUnit.MINUTES);
                case HOUR:
                    return source.minus(count, ChronoUnit.HOURS);
                case DAY:
                    return source.minus(count, ChronoUnit.DAYS);
                case WEEK:
                    return source.minus(count, ChronoUnit.WEEKS);
                case MONTH:
                    return source.minus(count, ChronoUnit.MONTHS);
                default:
                    return source;
            }
        }
        return source;
    }

    public static String format2Target(Temporal change, DataTimeFormat targetFormat) {
        switch (targetFormat) {
            case TimestampS:
                if (change instanceof Instant) {
                    return ((Instant) change).getEpochSecond() + "";
                }
                if (change instanceof LocalDateTime) {
                    return ((LocalDateTime) change).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() + "";
                }
                if (change instanceof LocalDate) {
                    return LocalDateTime.of((LocalDate) change, LocalTime.of(0, 0)).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() + "";
                }
                break;
            case TimestampM:
                if (change instanceof Instant) {
                    return ((Instant) change).toEpochMilli() + "";
                }
                if (change instanceof LocalDateTime) {
                    return ((LocalDateTime) change).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + "";
                }
                if (change instanceof LocalDate) {
                    return LocalDateTime.of((LocalDate) change, LocalTime.of(0, 0)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + "";
                }
                break;
            case DataTimeFormat1:
            case DataTimeFormat2:
            case DataTimeFormat3:
            case DataTimeFormat4:
            case DataTimeFormat5:
            case DataTimeFormat6:
            case DataTimeFormat7:
            case DataTimeFormat8:
            case DataTimeFormat9:
            case DataTimeFormat19:
            case DataTimeFormat20:
            case DataTimeFormat21:
            case DataTimeFormat22:
            case DataTimeFormat27:
            case YearFormat:
            case MonthFormat:
            case YearMonthFormat:
            case DayFormat:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof LocalDateTime) {
                    return ((LocalDateTime) change).toLocalDate().format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof LocalDate) {
                    return ((LocalDate) change).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
            case DataTimeFormat11:
                if (change instanceof Instant) {
                    return ((Instant) change).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof LocalDateTime) {
                    return ((LocalDateTime) change).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof OffsetDateTime) {
                    return ((OffsetDateTime) change).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
            case DataTimeFormat10:
            case DataTimeFormat12:
            case DataTimeFormat13:
            case DataTimeFormat14:
            case DataTimeFormat15:
            case DataTimeFormat16:
            case DataTimeFormat17:
            case DataTimeFormat18:
                //case DataTimeFormat29:
            case DataTimeFormat30:
            case DataTimeFormat31:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof LocalDateTime) {
                    return ((LocalDateTime) change).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof LocalDate) {
                    return LocalDateTime.of((LocalDate) change, LocalTime.MIN).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
            case DataTimeFormat23:
            case DataTimeFormat24:
            case DataTimeFormat25:
            case DataTimeFormat26:
            case HourFormat:
            case MinFormat:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).toLocalTime().format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof LocalDateTime) {
                    return ((LocalDateTime) change).toLocalTime().format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
                if (change instanceof LocalTime) {
                    return ((LocalTime) change).format(DateTimeFormatter.ofPattern(targetFormat.getFormat()));
                }
            case QuarterFormat:
                if (change instanceof Instant) {
                    return DateTimeUtil.getQuarter(LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()));
                }
                if (change instanceof LocalDateTime) {
                    return DateTimeUtil.getQuarter((LocalDateTime) change);
                }
                if (change instanceof LocalDate) {
                    return DateTimeUtil.getQuarter((LocalDate) change);
                }
            case WeekOfMonthFormat:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).get(ChronoField.ALIGNED_WEEK_OF_MONTH) + "";
                }
                if (change instanceof LocalDateTime) {
                    return change.get(ChronoField.ALIGNED_WEEK_OF_MONTH) + "";
                }
                if (change instanceof LocalDate) {
                    return change.get(ChronoField.ALIGNED_WEEK_OF_MONTH) + "";
                }
            case WeekFormat1:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).get(ChronoField.DAY_OF_WEEK) + "";
                }
                if (change instanceof LocalDateTime) {
                    return change.get(ChronoField.DAY_OF_WEEK) + "";
                }
                if (change instanceof LocalDate) {
                    return change.get(ChronoField.DAY_OF_WEEK) + "";
                }
            case WeekFormat2:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
                }
                if (change instanceof LocalDateTime) {
                    return ((LocalDateTime) change).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
                }
                if (change instanceof LocalDate) {
                    return ((LocalDate) change).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
                }
            case WeekFormat3:
                if (change instanceof Instant) {
                    return "周" + DateTimeUtil.getWeek(LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).get(ChronoField.DAY_OF_WEEK)) + "";
                }
                if (change instanceof LocalDateTime) {
                    return "周" + DateTimeUtil.getWeek(change.get(ChronoField.DAY_OF_WEEK)) + "";
                }
                if (change instanceof LocalDate) {
                    return "周" + DateTimeUtil.getWeek(change.get(ChronoField.DAY_OF_WEEK)) + "";
                }
            case DayOfYearFormat:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).get(ChronoField.DAY_OF_YEAR) + "";
                }
                if (change instanceof LocalDateTime) {
                    return change.get(ChronoField.DAY_OF_YEAR) + "";
                }
                if (change instanceof LocalDate) {
                    return change.get(ChronoField.DAY_OF_YEAR) + "";
                }
            case WeekOfYearFormat:
                if (change instanceof Instant) {
                    return LocalDateTime.ofInstant((Instant) change, ZoneId.systemDefault()).get(ChronoField.ALIGNED_WEEK_OF_YEAR) + "";
                }
                if (change instanceof LocalDateTime) {
                    return change.get(ChronoField.ALIGNED_WEEK_OF_YEAR) + "";
                }
                if (change instanceof LocalDate) {
                    return change.get(ChronoField.ALIGNED_WEEK_OF_YEAR) + "";
                }
            default:
        }
        return null;
    }

    public static Date getDate(String time, DataTimeFormat dataTimeFormat) throws Exception {

        if (dataTimeFormat.getId() == 0 || dataTimeFormat.getId() == 1) {
            return new Date(Long.parseLong(time));
        } else if (dataTimeFormat.getId() == 10) {
            // 利用Java8的API 处理yyyyMMdd格式Bug
            LocalDate localDate = LocalDate.parse(time, DateTimeFormatter.ofPattern(dataTimeFormat.getDescription()));
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        } else {
            SimpleDateFormat format = new SimpleDateFormat(dataTimeFormat.getDescription());
            return format.parse(time);
        }

    }


    /**
     * 根据日期获取季度
     *
     * @param localDate
     * @return
     */
    public static String getQuarter(LocalDate localDate) {
        Month month = localDate.getMonth();
        if (Month.JANUARY == month || Month.FEBRUARY == month || Month.MARCH == month) {
            return "1";
        }
        if (Month.APRIL == month || Month.MAY == month || Month.JUNE == month) {
            return "2";
        }
        if (Month.JULY == month || Month.AUGUST == month || Month.SEPTEMBER == month) {
            return "3";
        }
        if (Month.OCTOBER == month || Month.NOVEMBER == month || Month.DECEMBER == month) {
            return "4";
        }
        return "0";
    }

    /**
     * 根据日期获取季度
     *
     * @param localDateTime
     * @return
     */
    public static String getQuarter(LocalDateTime localDateTime) {
        return getQuarter(localDateTime.toLocalDate());
    }
    //获取指定毫秒数的对应星期

    /**
     * @param cweek 周标识数字转中文
     * @return
     */
    public static String getWeek(int cweek) {
        String week = "";
        switch (cweek) {
            case 1:
                week = "一";
                break;
            case 2:
                week = "二";
                break;
            case 3:
                week = "三";
                break;
            case 4:
                week = "四";
                break;
            case 5:
                week = "五";
                break;
            case 6:
                week = "六";
                break;
            case 7:
                week = "日";
                break;
        }
        return week;

    }

    /**
     * 跟指定日期比较
     *
     * @param startDate
     * @param endDate
     * @param compareDateTime
     * @return
     * @throws Exception
     */
    public static boolean compareDateWithDate(Date startDate, Date endDate, Date compareDateTime) {
        boolean result = true;
        if (startDate != null) {
            result = compareDate(startDate, compareDateTime) != 1;
        }
        if (endDate != null) {
            result = result && (compareDate(compareDateTime, endDate) != 1);
        }
        return result;
    }

    /**
     * 当前时间是否在开始时间和结束时间范围内
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static boolean compareDateWithNow(Date startDate, Date endDate) {
        boolean result = true;
        Date now = new Date();
        if (startDate != null) {
            result = compareDate(startDate, now) != 1;
        }
        if (endDate != null) {
            result = result && (compareDate(now, endDate) != 1);
        }
        return result;
    }

    /**
     * 比较两个日期大小
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static int compareDate(Date startDate, Date endDate) {
        int result = 0;
        long t1 = startDate.getTime();
        long t2 = endDate.getTime();
        if (t1 > t2) {
            result = 1;
        } else if (t1 < t2) {
            result = -1;
        } else if (t1 == t2) {
            result = 0;
        }
        return result;
    }


    public static Date getLatestTimeBeforeNow(int timeSpliceNum, TimeSpliceUnit timeSpliceUnit) {
        Instant instant = Instant.now();
        switch (timeSpliceUnit) {
            case SECOND:
                instant = instant.minus(timeSpliceNum, ChronoUnit.SECONDS);
                break;
            case MIN:
                instant = instant.minus(timeSpliceNum, ChronoUnit.MINUTES);
                break;
            case HOUR:
                instant = instant.minus(timeSpliceNum, ChronoUnit.HOURS);
                break;
            case DAY:
                instant = instant.minus(timeSpliceNum, ChronoUnit.DAYS);
                break;
            case WEEK:
                instant = instant.minus(timeSpliceNum, ChronoUnit.WEEKS);
                break;
            case MONTH:
                instant = instant.minus(timeSpliceNum, ChronoUnit.MONTHS);
                break;
            default:
                break;
        }
        return new Date(instant.toEpochMilli());
    }

    /**
     * 转换成时间粒度对应的时间
     *
     * @param targetTime
     * @param timeSpliceUnit
     * @return
     */
    public static String transformToParticleTime(Date targetTime, TimeSpliceUnit timeSpliceUnit) {
        String particleTime = "";
        String pattern = null;
        switch (timeSpliceUnit) {
            case MIN: {
                pattern = "yyyyMMddHHmm";
                break;
            }
            case HOUR: {
                pattern = "yyyyMMddHH";
                break;
            }
            case DAY: {
                pattern = "yyyyMMdd";
                break;
            }
            case WEEK: {
                pattern = "yyyyMMddWW";
                break;
            }
            case MONTH: {
                pattern = "yyyyMM";
                break;
            }
            default:
                break;
        }
        if (pattern != null) {
            particleTime = DateFormatUtils.format(targetTime, pattern);
        }
        return particleTime;
    }

    /***
     * 将时间字符串转化成日期类型
     * @param patten
     * @param date
     * @return
     */
    public static String formatDateToStr(Date date, String patten) {
        String dateStr = "";
        if (StringUtils.isBlank(patten)) {
            patten = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            dateStr = FastDateFormat.getInstance(patten).format(date);
        } catch (Exception e) {
            dateStr = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS").format(date);
        }
//        SimpleDateFormat sdf = new SimpleDateFormat(patten);
//        String dateStr = sdf.format(date);
//        return dateStr;
        return dateStr;
    }

    /**
     * 将日期类型转化成时间字符串
     *
     * @param dateStr
     * @param patten
     * @return
     */
    public static Date formatStrToDate(String dateStr, String patten) {
        if (StringUtils.isBlank(patten)) {
            patten = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = null;
        try {
            date = FastDateFormat.getInstance(patten).parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return date;
    }

    /**
     * 对日期进行计算
     */
    public static LocalDateTime getDateTime(Date value, String math, String num, String type) {

        Instant instant = value.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, zone);

        //无操作符号/无操作数量/无操作单位 视为参数不全不进行后续节点计算
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(math) && num != null && org.apache.commons.lang3.StringUtils.isNotEmpty(type)) {
            Integer i = Integer.parseInt(num);
            if (math.equals(MathType.ADD.getCode())) {
                if (type.equals(TimeType.SECOND.getCode())) {

                    dateTime = dateTime.plusSeconds(i);
                } else if (type.equals(TimeType.MIN.getCode())) {

                    dateTime = dateTime.plusMinutes(i);
                } else if (type.equals(TimeType.HOUR.getCode())) {

                    dateTime = dateTime.plusHours(i);
                } else if (type.equals(TimeType.DAY.getCode())) {

                    dateTime = dateTime.plusDays(i);
                } else if (type.equals(TimeType.MOUTH.getCode())) {

                    dateTime = dateTime.plusMonths(i);
                } else if (type.equals(TimeType.YEAR.getCode())) {

                    dateTime = dateTime.plusYears(i);
                } else {
                    log.warn("error timeType: {}", type);
                }
            } else if (math.equals(MathType.CUT.getCode())) {
                if (type.equals(TimeType.SECOND.getCode())) {

                    dateTime = dateTime.minusSeconds(i);
                } else if (type.equals(TimeType.MIN.getCode())) {

                    dateTime = dateTime.minusMinutes(i);
                } else if (type.equals(TimeType.HOUR.getCode())) {

                    dateTime = dateTime.minusHours(i);
                } else if (type.equals(TimeType.DAY.getCode())) {

                    dateTime = dateTime.minusDays(i);
                } else if (type.equals(TimeType.MOUTH.getCode())) {

                    dateTime = dateTime.minusMonths(i);
                } else if (type.equals(TimeType.YEAR.getCode())) {

                    dateTime = dateTime.minusYears(i);
                } else {
                    log.warn("error timeType: {}", type);
                }
            } else {
                log.warn("error mathType: {}", math);
            }
        }

        return dateTime;
    }


    /**
     * 日期格式按整型格式显示
     * 日：20190801
     * 月：201908
     * 年：2019
     * 时：2019090811
     * 分：201909021135
     * 秒：20190902113529
     * @param dateTime
     * @param timeSliceNumber
     * @return
     */
    public static Integer getBeforeDate(Integer dateTime,Integer timeSliceNumber,TimeSpliceUnit spliceUnit){
        Assert.assertTrue("只可以为月，日期",spliceUnit == TimeSpliceUnit.DAY || spliceUnit == TimeSpliceUnit.MONTH);

        LocalDate localTime = null;
        Integer statTime = null;

        int year = 0;
        int month = 0;
        int date = 0;
        try{
            switch (spliceUnit){
                case DAY:
                    //yyyyMMdd
                    year = dateTime / 10000;
                    month = dateTime/100 - year*100;
                    date = dateTime - (dateTime/100)*100;
                    localTime = LocalDate.of(year,month,date);
                    localTime = localTime.minusDays(timeSliceNumber);
                    statTime = Integer.valueOf(localTime.format(DateTimeFormatter.ofPattern(DataTimeFormat.DataTimeFormat9.getFormat())));
                    break;
                case MONTH:
                    //yyyyMM
                    year = dateTime / 100;
                    month = dateTime - year*100;
                    localTime = LocalDate.of(year,month,1);
                    localTime = localTime.minusMonths(timeSliceNumber);
                    statTime = Integer.valueOf(DateTimeUtil.format2Target(localTime,DataTimeFormat.YearMonthFormat));
                    break;
                default:
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return statTime;
    }

    /**
     * 计算两个时间戳的间隔耗时
     * 返回格式为：xx时xx分xx秒
     * @param endTime
     * @param startTime
     * @return
     */
    public static String consume(long endTime,long startTime){
        long consume = (endTime - startTime);
        long consumeSecond = consume / 1000;
        long hour = consumeSecond / 3600;
        long minute = (consumeSecond - hour * 3600) / 60;
        long second = consumeSecond - hour * 3600 - minute * 60;
        return hour + "时" + minute + "分" + second + "秒";
    }

    /**
     * 返回当前时间距离startTime时间戳的耗时
     * 返回格式为：xx时xx分xx秒
     * @param startTime
     * @return
     */
    public static String consume(long startTime){
        return consume(System.currentTimeMillis(),startTime);
    }

}
