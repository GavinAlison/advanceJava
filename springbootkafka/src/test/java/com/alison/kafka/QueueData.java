package com.alison.kafka;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 队列事件数据封装
 *
 *
 * offset = 42, key = {"database":"demo","table":"test","pk.id":4}, value = {"database":"demo","table":"test","type":"insert","ts":1578016448,"xid":63708,"commit":true,"data":{"id":4,"name":"吕洞宾"}}
 * @author yxy
 * @date 2018/12/11
 */
@Getter
@Setter
public class QueueData<E> implements Serializable {

    /**数据库名称 */
    private String database;

    /***
     * 表名称
     *
     */
    private String table;

    /**
     * 操作类型，插入、更新、删除
     */
    private DMLOperateType type;

    /***
     * timestap
     */

    private long ts;

    /***
     * 事物id
     */
    private String xid;


    /***
     * 是否提交
     */
    private boolean commit;


    /** 数据载体 */
    private E data;


    /***
     * update时  承载的旧数据
     */
    private E old;


    /***
     *
     * 更新或删除时  需要指定主键的key 和值
     *
     */

    private Map<String,Object> pkIds;



    /** 计算之后的数据 ，包含衍生字段
     * Map 为 fieldCode
     * Value 值
     *
     * */

    private Map<String,Object> payloadAfter;



    private BinlogErrorInfo binlogErrorInfo;




}
