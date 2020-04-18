//package com.alison;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author alison
// * @Date 2020/4/12
// * @Version 1.0
// * @Description
// */
//@SpringBootApplication
//public class kafkaApplication   {
//    public static Logger logger = LoggerFactory.getLogger(kafkaApplication.class);
//
//    public static void main(String[] args) {
//        SpringApplication.run(kafkaApplication.class);
//    }
//
////
////    @Bean
////    public ProducerFactory<Integer, String> producerFactory() {
////        return new DefaultKafkaProducerFactory<>(producerConfigs());
////    }
////
////    @Bean
////    public Map<String, Object> producerConfigs() {
////        Map<String, Object> props = new HashMap<>();
////        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
////        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
////        return props;
////    }
////
////    @Bean
////    public KafkaTemplate<Integer, String> kafkaTemplate() {
////        return new KafkaTemplate<Integer, String>(producerFactory());
////    }
////    @Autowired
////    private KafkaTemplate<String, String> template;
//
//    private final CountDownLatch latch = new CountDownLatch(3);
//
////    @Override
////    public void run(String... args) throws Exception {
////        this.template.send("topic1", "foo1");
////        this.template.send("topic1", "foo2");
////        this.template.send("topic1", "foo3");
////        latch.await(60, TimeUnit.SECONDS);
////        logger.info("All received");
////    }
//
//    @KafkaListener(topics = "test_ddl", groupId = "foo")
//    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
//        logger.info(cr.toString());
////        latch.countDown();
//    }
//}
