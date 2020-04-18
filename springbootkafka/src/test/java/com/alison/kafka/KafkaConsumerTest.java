package com.alison.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

/**
 * @Author alison
 * @Date 2020/4/12
 * @Version 1.0
 * @Description
 */
@Slf4j
public class KafkaConsumerTest {

    private static Consumer<String, String> consumer  ;
    private final static String TOPIC = "test_ddl";

    @Before
    public void  before() {
        Properties props =  new Properties();
        //消费队列线程数 要和 topic 分区的个数保持一致
        props.put("zookeeper.connect", "localhost:2181");
        props.put(GROUP_ID_CONFIG,"foo");

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // 自动提交时间
        props.put(AUTO_OFFSET_RESET_CONFIG,"earliest"); // earliest从最早的offset开始拉取，latest:从最近的offset开始消费
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-collector11"); // 发送端id,便于统计
        props.put(MAX_POLL_RECORDS_CONFIG,"200"); // 每次批量拉取条数
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(TOPIC));

    }


    /****
     *
     * 测试生产者
     *
     *  *
     *  * offset = 42, key = {"database":"demo","table":"test","pk.id":4},
     *  value = {"database":"demo","table":"test","type":"insert","ts":1578016448,"xid":63708,
     *  "commit":true,"data":{"id":4,"name":"吕洞宾"}}
     *  * @author yxy
     *  * @date 2018/12/11
     *  */

    @Test
    public void testConsumer() throws Exception {
        while(true){
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(20));
            if(!poll.isEmpty()){
                Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
                while(iterator.hasNext()){
                    ConsumerRecord<String, String> next = iterator.next();
                    log.debug("======================key:{},value:{}",next.key(),next.value());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

}
