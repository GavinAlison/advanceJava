//package com.alison;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
///**
// * @Author alison
// * @Date 2020/4/12
// * @Version 1.0
// * @Description
// */
//
//@RestController
//@RequestMapping("test")
//public class KafkaTestController {
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    @GetMapping(value = "/producer")
//    public R consume(@RequestBody String body) throws IOException {
//        kafkaTemplate.send("result", body);
//        return R.ok();
//    }
//}
