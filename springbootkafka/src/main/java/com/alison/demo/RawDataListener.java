package com.alison.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author alison
 * @Date 2020/4/12
 * @Version 1.0
 * @Description
 */
@Component
@Slf4j
public class RawDataListener {

    /**
     * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)
     * @param record
     * @throws IOException
     */
    @KafkaListener(topics = {"${kafka.consumer.topic:test_ddl}"})
    public void listen(ConsumerRecord<?, ?> record) throws IOException {
        String value = (String) record.value();
        String topic = record.topic();
        if("result".equals(topic)){
            log.info("接收到的信息为："+value);
        }

    }

}
