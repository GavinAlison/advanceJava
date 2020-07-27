package com.alison;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JosephusProblem {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        ListOperations listOperations = redisTemplate.opsForList();
        //构造数据
        for (int i = 1; i <= 41; i++) {
            listOperations.leftPush("josephus", String.valueOf(i));
        }
        int index = 1;
        while (listOperations.size("josephus") > 0) {
            //当数到3时，弹出
            if (index == 3) {
                System.out.println(listOperations.range("josephus", 0, -1));
                System.out.println("当前被杀的人是：" + listOperations.rightPop("josephus"));
                index = 0;
            } else {
                listOperations.rightPopAndLeftPush("josephus", "josephus");
            }
            index++;
        }
    }

}
