package java8;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 什么是 Stream 流？
 * <p>
 * 简单来说，我们可以使用 java.util.Stream 对一个包含一个或多个元素的集合做各种操作。
 * 这些操作可能是 中间操作 亦或是 终端操作。 终端操作会返回一个结果，而中间操作会返回一个 Stream 流。
 * <p>
 * 需要注意的是，你只能对实现了 java.util.Collection 接口的类做流的操作。
 * <p>
 * Map 不支持 Stream 流。
 * <p>
 * Stream 流支持同步执行，也支持并发执行。
 */
public class StreamDemo {
    public static void main(String[] args) {
        // Filter
        List<String> strCollection = new ArrayList<>();
        strCollection.add("dd1");
        strCollection.add("dd2");
        strCollection.add("dd3");
        strCollection.add("dd4");
        strCollection.add("dd5");
        strCollection.add("ad5");
        strCollection.add("cd5");
        strCollection.add("bd5");
        strCollection.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);
        System.out.println("----sorted");
        // Sorted
        strCollection.stream().sorted()
                .filter((s) -> s.startsWith("d"))
                .forEach(System.out::println);
        // Map
        strCollection.stream().map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);
        // Match
        boolean anyStartWith = strCollection.stream()
                .anyMatch((s) -> s.startsWith("d"));
        System.out.println(anyStartWith);
        // Count
        boolean allStartWith = strCollection.stream()
                .allMatch((s) -> s.startsWith("d"));
        System.out.println(allStartWith);
        // Reduce
        Optional<String> reduced = strCollection.stream().sorted()
                .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
        System.out.println("---------------黑足猫: 猫中夜视第一-----------------");
        // stream
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);
//        Collect(收集)
//        Collect（收集）是一种是十分有用的最终操作，它可以把stream中的元素转换成另外一种形式，比如；list，set，map。Collect使用Collector作为参数，Collector包含四种不同的操作：supplier（初始构造器）, accumulator（累加器）, combiner（组合器）， finisher（终结者）
        StreamDemo s = new StreamDemo();
        List<Person> filtered =
                s.persons
                        .stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());

        System.out.println(filtered);    // [Peter, Pamela]
        Map<Integer, List<Person>> personsByAge = s.persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));
        personsByAge
                .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
//        计算所有用户的年纪
        Double averageAge = s.persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));

        System.out.println(averageAge);     // 19.0

        String phrase = s.persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));
//        join collector的三个参数分别表示：连接符，字符串前缀，字符串后缀（可选）
//        将一个stream转换为map，我们必须指定map的key和value如何映射。要注意的是key的值必须是唯一性的，否则会抛出IllegalStateException，但是可以通过使用合并函数（可选）绕过这个IllegalStateException异常：
        Map<Integer, String> map = s.persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));

        System.out.println(map);
//        通过Collector.of()方法创建了一个新的collector，我们必须给这个collector提供四种功能：supplier, accumulator, combiner,finisher.
        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher

        String names = s.persons
                .stream()
                .collect(personNameCollector);

        System.out.println(names);  // MAX | PETER | PAMELA | DAVID
    }


    class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    List<Person> persons =
            Arrays.asList(
                    new Person("Max", 18),
                    new Person("Peter", 23),
                    new Person("Pamela", 23),
                    new Person("David", 12));
}
