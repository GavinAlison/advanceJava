package com.third.aviator.util;

import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AviatorTest {

    @Test
    public void testNil(){
        String exp = "a!=nil";
        Expression expression = AviatorEvaluator.compile(exp);
        Map<String, Object> p = Maps.newHashMap();
        p.put("a", 8000.0);
        System.out.println(AviatorUtil.compute(expression, p));
    }

    @Test
    public void testMile(){
        String exp = " 1 + 2 * 3 - 9";
        Expression expression = AviatorEvaluator.compile(exp);
        long startTime = System.nanoTime();
        Map<String, Object> env = Maps.newHashMap();
        for(int i = 0; i< 100_000_000; i++){
            AviatorUtil.compute(expression, env);
        }
        System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
    }


}
