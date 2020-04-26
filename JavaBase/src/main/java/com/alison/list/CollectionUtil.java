package com.alison.list;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @Author alison
 * @Date 2020/4/20
 * @Version 1.0
 * @Description
 */
public class CollectionUtil {
    // 根据单个，多个元素创建list
    public List generateList() {
        // 单个list
        String dayOfStartTime = "day_of_start_time";

        return Collections.singletonList(dayOfStartTime);
    }

    public List generateMultiList() {
        List resultList = new ArrayList();
        resultList.addAll(Arrays.asList("month", "week", "day", "hour", "minute", "second"));
        return resultList;
    }

    public List generateMuliLists() {
        return Lists.newArrayList("1", "2");
    }

    public void reduce() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        int sum = list.stream().reduce(0, (a, b) -> a + b);
        sum = list.stream().reduce(0, Integer::sum);
        Optional optional = list.stream().reduce(Integer::sum);
        System.out.println(sum);
    }

    public void maxAndMin() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        Optional<Integer> max = list.stream().reduce(Integer::max);
        Optional<Integer> min = list.stream().reduce(Integer::min);
        long count = list.stream().count();
    }


}
