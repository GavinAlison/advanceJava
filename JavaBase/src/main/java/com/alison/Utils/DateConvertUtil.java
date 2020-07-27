package com.alison.Utils;

import java.util.ArrayList;
import java.util.List;

public class DateConvertUtil {

    public static List<Double> ns2minu(long... nsTimes) {
        List<Double> minus = new ArrayList<>();
        for (long nsTime : nsTimes) {
            minus.add((double) nsTime / (1000 * 1000 * 60.0));
        }
        return minus;
    }

    public static void main(String[] args) {
        long[] ss = {1338729
                , 791504
                , 759865
                , 398488
                , 140843};
        System.out.println(ns2minu(ss));
    }
}
