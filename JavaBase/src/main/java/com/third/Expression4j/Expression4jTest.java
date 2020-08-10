package com.third.Expression4j;

import com.google.common.collect.Lists;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class Expression4jTest {

    @Test
    public void test01() {
        System.out.println(Expression4jUtil.compute("sin(x)", Collections.singletonMap("x", Math.toRadians(30))));
        Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        double result = e.evaluate();
        System.out.println(result);
    }

    @Test
    public void test012() {
        System.out.println(Expression4jUtil.compute("sin(x)", Collections.singletonMap("x", Math.toRadians(30))));
        Expression e = new ExpressionBuilder("b385 * 2 + b37 * 5 - floor( 1.5 * 5 )")
                .variables("b385", "b37")
                .build()
                .setVariable("b385", 4)
                .setVariable("b37", 3);
        double result = e.evaluate();
        System.out.println(result);
    }

    @Test
    public void test02() throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        Expression e = new ExpressionBuilder("3log(y)/(x+1)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        Future<Double> future = e.evaluateAsync(exec);
        double result = future.get();
        System.out.println(result);
    }

    public void test03() {
        double result = new ExpressionBuilder("2cos(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", 0.5d)
                .setVariable("y", 0.25d)
                .evaluate();
        assertEquals(2d * Math.cos(0.5d * 0.25d), result, 0d);
    }

    @Test
    public void test04() {
        String expr = "pi+π+e+φ";
        double expected = 2 * Math.PI + Math.E + 1.61803398874d;
        Expression e = new ExpressionBuilder(expr).build();
        assertEquals(expected, e.evaluate(), 0d);
        System.out.println(e.evaluate());
    }

    @Test
    public void test05() {
        String expr = "7.2973525698e-3";
        double expected = Double.parseDouble(expr);
        Expression e = new ExpressionBuilder(expr).build();
        assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void test06() {
        net.objecthunter.exp4j.function.Function logb = new net.objecthunter.exp4j.function.Function("logb", 2) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(args[1]);
            }
        };
        double result = new ExpressionBuilder("logb(8, 2, 3,6,8)")
                .function(logb)
                .build()
                .evaluate();
        double expected = 3;
        assertEquals(expected, result, 0d);
    }

    @Test
    public void test16() {
        net.objecthunter.exp4j.function.Function logb = new net.objecthunter.exp4j.function.Function("max") {
            @Override
            public double apply(double... args) {
                return Arrays.stream(args).sorted().max().getAsDouble();
            }
        };
        List<Double> doubleList = Lists.newArrayList(8D, 2D, 3D,6D,19D);
        double result = new ExpressionBuilder("max(8, 2, 3,6,19)")
                .function(new net.objecthunter.exp4j.function.Function("max", doubleList.size()) {
                    @Override
                    public double apply(double... args) {
                        return Arrays.stream(args).sorted().max().getAsDouble();
                    }
                })
                .build()
                .evaluate();
        double expected = 19;
        assertEquals(expected, result, 0d);
    }

    @Test
    public void test07() {
        net.objecthunter.exp4j.function.Function avg = new net.objecthunter.exp4j.function.Function("avg", 4) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        double result = new ExpressionBuilder("avg(1,2,3,4)")
                .function(avg)
                .build()
                .evaluate();

        double expected = 2.5d;
        assertEquals(expected, result, 0d);
    }

    //Validate an expression
    @Test
    public void test11(){
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate();
        assertFalse(res.isValid());
        assertEquals(1, res.getErrors().size());

        e.setVariable("x",1d);
        res = e.validate();
        assertTrue(res.isValid());
    }
    //Validate an expression before variables have been set, i.e. skip checking if all variables have been set.
    @Test
    public void test12(){
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate(false);
        assertTrue(res.isValid());
        assertNull(res.getErrors());
    }

}
