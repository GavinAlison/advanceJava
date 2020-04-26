package com.alison.compare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author alison
 * @Date 2020/4/19
 * @Version 1.0
 * @Description
 */
public class CompareDemo {

    public void compare() {
        List<Integer>  list = new ArrayList<>();
        list.sort(Comparator.comparing(Integer::intValue));
    }
}
