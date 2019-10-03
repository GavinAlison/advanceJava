package java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List 集合现在已经添加了 sort 方法。而且 Java 编译器能够根据类型推断机制判断出参数类型，这样，你连入参的类型都可以省略啦，怎么样，是不是感觉很强大呢！
 */
public class LambdaDemo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("lisa", "tom", "wentefa", "alice");
        List<String> namesTmp = names;
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(names);
        // method-2
        namesTmp.sort((a, b) -> a.compareTo(b));
        System.out.println(namesTmp);
    }


    @FunctionalInterface
    interface Converter<F, T> {
        T conver(F from);
    }

    public void FelisNigripes() {
        final int num = 1;
        Converter<Integer, String> stringConverter = (from) -> {
            String value = String.valueOf(from + num);
//            num = 3;
            return value;
        };
    }
    static class Lambda4 {
        // 静态变量
        static int outerStaticNum;
        // 成员变量
        int outerNum;

        void testScopes() {
            Converter<Integer, String> stringConverter1 = (from) -> {
                // 对成员变量赋值
                outerNum = 23;
                return String.valueOf(from);
            };

            Converter<Integer, String> stringConverter2 = (from) -> {
                // 对静态变量赋值
                outerStaticNum = 72;
                return String.valueOf(from);
            };
        }
    }

}
