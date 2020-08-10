package com.alison.list;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

//    可以看出,原来是再toArray方法里面新建一个数组, <T> T[] toArray(T[] a);这个方法里面有个小技巧:
//    新建的数组的size小于等于list大小的话,list中所有元素都转化为数组中元素,且大小为数组大小.
//            如果size比list元素个数大的话,则补充默认值.
    public void listToArray(){
        List lists = Lists.newArrayList("2", "3");
        String[] strs = (String[]) lists.toArray(new String[2]);
        System.out.println(strs.length);
        System.out.println(Arrays.toString(strs));
    }

//    转换为int[]
    public void intStreamToArray(){
        int[] arr = IntStream.of(1, 2, 3, 4, 5).toArray();
        System.out.println(Arrays.toString(arr));

        Integer[] integers = Stream.of(1, 2, 3, 4, 5).toArray(Integer[]::new);
    }

    public void streamToArray(){
        List list = Lists.newArrayList("2", "3");
        //类似于原来的方式一
        String[] arr1 = (String[]) list.stream().toArray(size -> {
            System.out.println(size);
            return new String[size];
        });
        System.out.println(Arrays.toString(arr1));

        //方法引用,简单明了
        String[] arr2 = (String[]) list.stream().toArray(String[]::new);
        System.out.println(Arrays.toString(arr2));
    }


    public void collectToMap(){
//        指定key-value，value是对象中的某个属性值。
        List<User> userList = Lists.newArrayList(new User(1, "1"), new User(2, "2"));
        //指定key-value，value是对象本身，User->User 是一个返回本身的lambda表达式
        Map<Integer,String> userMap1 = userList.stream().collect(Collectors.toMap(User::getId,User::getName));
        //指定key-value，value是对象本身，Function.identity()是简洁写法，也是返回对象本身
        Map<Integer,User> userMap2 = userList.stream().collect(Collectors.toMap(User::getId,User->User));
        //指定key-value，value是对象本身，Function.identity()是简洁写法，也是返回对象本身，key 冲突的解决办法，这里选择第二个key覆盖第一个key。
        Map<Integer,User> userMap3 = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
//        指定key-value，value是对象本身，Function.identity()是简洁写法，也是返回对象本身，key 冲突的解决办法，这里选择第二个key覆盖第一个key。
        Map<Integer,User> userMap4 = userList.stream().collect(Collectors.toMap(User::getId, Function.identity(),(key1,key2)->key2));
//        重复时将前面的value 和后面的value拼接起来；
        Map<Integer, String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName,(key1 , key2)-> key1+","+key2 ));
//        重复时将重复key的数据组成集合
        Map<Integer, List<String>> map2 = userList.stream().collect(Collectors.toMap(User::getId,
                p ->  {
                    List<String> getNameList = new ArrayList<>();
                    getNameList.add(p.getName());
                    return getNameList;
                },
                (List<String> value1, List<String> value2) -> {
                    value1.addAll(value2);
                    return value1;
                }
        ));

        System.out.println(map);
    }
    @Getter
    @Setter
    @AllArgsConstructor
    class User{
        Integer id;
        String name;
    }
}
