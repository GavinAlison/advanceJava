package com.alison.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: data-sych-server
 * @description: BinLog错误日志信息
 * @author: yuanchangyou
 * @create: 2020-03-15 00:48
 **/
@Slf4j
@Setter
@Getter
@AllArgsConstructor
public class BinlogErrorInfo {

    /***
     * 是否已经转发
     */
    boolean isTransmit;


    String binlogKey;


    String binlogValue;


    /***
     * 错误信息
     */
    String errorStrack;

}
