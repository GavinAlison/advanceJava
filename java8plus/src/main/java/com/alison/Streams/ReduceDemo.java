package com.alison.Streams;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ReduceDemo {
    List<Person> persons =
            Arrays.asList(
                    new Person("Max", 18),
                    new Person("Peter", 23),
                    new Person("Pamela", 23),
                    new Person("David", 12));

    public void testPerson() {
        persons
                .stream()
                .reduce((p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2)
                .ifPresent(System.out::println);    // Pamela

        Person result =
                persons
                        .stream()
                        .reduce(new Person("", 0), (p1, p2) -> {
                            p1.setAge(p1.getAge() + p2.getAge());
                            p1.setName(p1.getName() + p2.getName());
                            return p1;
                        });

        System.out.format("name=%s; age=%s", result.getName(), result.getAge());
// name=MaxPeterPamelaDavid; age=76

        Integer ageSum = persons
                .stream()
                .reduce(0, (sum, p) -> sum += p.getAge(), (sum1, sum2) -> sum1 + sum2);
        System.out.println(ageSum);  // 76

        Integer ageSum2 = persons
                .stream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                            return sum += p.getAge();
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                            return sum1 + sum2;
                        });
        // accumulator: sum=0; person=Max
// accumulator: sum=18; person=Peter
// accumulator: sum=41; person=Pamela
// accumulator: sum=64; person=David
    }

}
