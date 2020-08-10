package com.alison.collection;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSort {

    //若一个类实现了Comparable接口，就意味着“该类支持排序”。 假设“有一个List列表(或数组)，里面的元素是实现了Comparable接口的类”，则该List列表(或数组)可以通过 Collections.sort（或 Arrays.sort）进行排序。
    @Test
    public void test02() {
        List<String> list = Lists.newArrayList("beijing", "shanghai", "hangzhou");
        Collections.sort(list);
        System.out.println(list);
    }
//    第二种：Comparator比较器接口。
//    我们若需要控制某个类的次序，而该类本身不支持排序(即没有实现Comparable接口)；我们可以建立一个“比较器”来进行排序。
//    这个“比较器”只需要实现Comparator接口即可。

    @Test
    public void test01() {
        List<String> list = Lists.newArrayList("beijing", "shanghai", "hangzhou");
        Collections.sort(list, new Comparator<String>() {
            public int compare(String str1, String str2) {
                /*
                 * 升序排的话就是第一个参数.compareTo(第二个参数);
                 * 降序排的话就是第二个参数.compareTo(第一个参数);
                 */
                // 按首字母升序排
                // return str1.compareTo(str2);
                // 按第二个字母升序排
                char c1 = str1.charAt(1);
                char c2 = str2.charAt(1);
                return c1 - c2;
            }
        });
        System.out.println(list);

    }
}
