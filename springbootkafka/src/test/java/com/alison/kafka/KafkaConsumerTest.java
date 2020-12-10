//package com.alison.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.*;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.Properties;
//
//import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
//
///**
// * @Author alison
// * @Date 2020/4/12
// * @Version 1.0
// * @Description
// */
//@Slf4j
//public class KafkaConsumerTest {
//
//    private static Consumer<String, String> consumer;
//    private final static String TOPIC = "topic2";
//
//    @Before
//    public void before() {
//        Properties props = new Properties();
//        //消费队列线程数 要和 topic 分区的个数保持一致
//        props.put("zookeeper.connect", "localhost:2181");
//        props.put(GROUP_ID_CONFIG, "foo");
//
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // 自动提交时间
//        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest从最早的offset开始拉取，latest:从最近的offset开始消费
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-collector11"); // 发送端id,便于统计
//        props.put(MAX_POLL_RECORDS_CONFIG, "200"); // 每次批量拉取条数
//        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//
//        consumer = new KafkaConsumer<>(props);
//
//        consumer.subscribe(Arrays.asList(TOPIC));
//
//    }
//
//
//    /****
//     *
//     * 测试生产者
//     *
//     *  *
//     *  * offset = 42, key = {"database":"demo","table":"test","pk.id":4},
//     *  value = {"database":"demo","table":"test","type":"insert","ts":1578016448,"xid":63708,
//     *  "commit":true,"data":{"id":4,"name":"吕洞宾"}}
//     *  * @author yxy
//     *  * @date 2018/12/11
//     *  */
//
//    @Test
//    public void testConsumer() throws Exception {
//        while (true) {
//            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(20));
//            if (!poll.isEmpty()) {
//                Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
//                while (iterator.hasNext()) {
//                    ConsumerRecord<String, String> next = iterator.next();
//                    log.debug("======================key:{},value:{}", next.key(), next.value());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }
//    }
//
//    private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();//用于跟踪偏移量的map
//
//    private class HandleRebalance implements ConsumerRebalanceListener {
//        @Override
//        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
//        }
//
//        @Override
//        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
//            //如果发生再均衡，要在即将失去partition所有权时提交偏移量。
//            //注意：
//            //(1)提交的是最近处理过的偏移量，而不是批次中还在处理的最后一个偏移量。因为partition有可能在我们还在处理消息时被撤回。
//            //(2)我们要提交所有分区的偏移量，而不只是即将市区所有权的分区的偏移量。因为提交的偏移量是已经处理过的，所以不会有什么问题。
//            //(3)调用commitSync方法，确保在再均衡发生之前提交偏移量
//            consumer.commitSync(currentOffsets);
//        }
//    }
//
//    public void test2() {
//        try {
//            consumer.subscribe(TOPIC, new HandleRebalance());
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(100);
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.println("topic = %s, partition = %s, offset = %d, customer = % s, country = %s\n ", record.topic(),
//                            record.partition(), record.offset(), record.key(),
//                            record.value());//模拟对消息的处理
//                    //在读取每条消息后，使用期望处理的下一个消息的偏移量更新map里的偏移量。下一次就从这里开始读取消息
//                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1, “no matadata”));
//                }
//                consumer.commitAsync(currentOffsets, null);
//            } catch(WakeupException e){
//                //忽略异常，正在关闭消费者
//            } catch(Exception e){
//                log.error("unexpected error", e);
//            } finally{
//                try {
//                    consumer.commitSync(currentOffsets);
//                } finally {
//                    consumer.close();
//                }
//            }
//        }
//
//    }
