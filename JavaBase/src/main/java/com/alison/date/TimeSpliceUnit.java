package com.alison.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author alison
 * @Date 2020/4/18
 * @Version 1.0
 * @Description
 * 统计时间单位
 */
public enum TimeSpliceUnit {

    AGGREGATE("汇总","AGGREGATE",6,0,0),
    SECOND("秒","SECOND",0,1,30*60),
    MIN("分","MIN",1,60,6*60),
    HOUR("时","HOUR",2,60*60,24*30),
    DAY("日","DAY",3,24*60*60,30*6),
    WEEK("周","WEEK",4,7*24*60*60,4*6),
    MONTH("月","MONTH",5,30*24*60*60,24);

    private String code;
    private String description;
    private Integer id;
    /**时间长度，秒数 */
    private int timeLength;

    /**最大默认窗口长度 */
    private int maxDefaultWinLength;

    private static final Logger logger = LoggerFactory.getLogger(TimeSpliceUnit.class);

    TimeSpliceUnit(String description, String code, int id,int timeLength,int maxWinLength){
        this.description = description;
        this.code = code;
        this.id=id;
        this.timeLength = timeLength;
        this.maxDefaultWinLength = maxWinLength;
    }

    public String getCode() {
        return code;
    }

    public int getMaxDefaultWinLength() {
        return maxDefaultWinLength;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public int getTimeLength() {
        return timeLength;
    }

    /**
     * 指定时间的当前粒度
     * @param localDateTime
     * @return
     */
    public int getTimeParticle(LocalDateTime localDateTime){
        int particle = 0;
        switch (this) {
            case DAY:
                particle = localDateTime.getDayOfMonth();
                break;
            case WEEK:
                particle = localDateTime.getDayOfWeek().getValue();
                break;
            case MIN:
                particle = localDateTime.getMinute();
                break;
            case HOUR:
                particle = localDateTime.getHour();
                break;
            case MONTH:
                particle = localDateTime.getMonthValue();
                break;
            case SECOND:
                particle = localDateTime.getSecond();
                break;
            default:
                break;
        }

        return particle;
    }


    /**
     * 获取x周期前时间
     * @return
     */
    public Date getBeforeDate(Date date, Integer timeSliceNumber){
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        switch (this){
            case SECOND:
                dateTime = dateTime.minusSeconds(timeSliceNumber);
                break;
            case MIN:
                dateTime = dateTime.minusMinutes(timeSliceNumber);
                break;
            case HOUR:
                dateTime = dateTime.minusHours(timeSliceNumber);
                break;
            case WEEK:
                dateTime = dateTime.minusWeeks(timeSliceNumber);
                break;
            case DAY:
                dateTime = dateTime.minusDays(timeSliceNumber);
                break;
            case MONTH:
                dateTime = dateTime.minusMonths(timeSliceNumber);
                break;
            default:break;
        }

        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定时间片之前的时间位置
     * @param currentDate
     * @param timeSliceNumber
     * @return
     */
    public String getBeforeDateSplice(LocalDateTime currentDate, Integer timeSliceNumber, DateTimeFormatter dateFormat){
        Integer timeSlop = this.timeLength * timeSliceNumber;
        LocalDateTime localDateTime = currentDate.minusSeconds(timeSlop);
        return localDateTime.format(dateFormat);
    }

    private Object objectDate(Date date){
        if(isStatUnit()){
            return date.getTime();
        }else{
            return Integer.valueOf(DateTimeUtil.transformToParticleTime(date,this));
        }
    }

    private Object tsDate(long ts){
        if(isStatUnit()){
            return ts;
        }else{
            String s = DateTimeUtil.transformToParticleTime(new Date(ts),this);
            return Integer.valueOf(s);
        }
    }

    private boolean isStatUnit(){
        return this == MIN || this == SECOND || this == HOUR;
    }
}
