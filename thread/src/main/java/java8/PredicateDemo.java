package java8;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class PredicateDemo {
    //    Predicate 是一个可以指定入参类型，并返回 boolean 值的函数式接口。
    //    它内部提供了一些带有默认实现的方法，可以 被用来组合一个复杂的逻辑判断（and, or, negate）：
    public static void main(String[] args) {
        //    Predicate 断言
        Predicate<String> predicate = (s) -> s.length() > 0;
        System.out.println(predicate.test("foo"));
        System.out.println(predicate.negate().test("foo"));

        Predicate<Boolean> nonnull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
//        function

        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("123");
    }
}
