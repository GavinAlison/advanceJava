package com.alison.Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class FlatMapDemo {
    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    public void flatMap1(){
        List<Foo> foos = new ArrayList<>();
        IntStream.range(1, 4)
                .forEach(i -> foos.add(new Foo("Foo" + i)));
        foos.forEach(f ->
                IntStream.range(1, 4)
                        .forEach(i -> f.bars.add(new Bar("bar" + i + "<--" + f.name))));

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));
        System.out.println("-----------");
        IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
                        .forEach(f.bars::add))
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));
    }

    static class Outer {
        Nested nested;
    }

    static class Nested {
        Inner inner;
    }

    static class Inner {
        String foo;
    }

    public void nestedInner() {
        Outer outer = new Outer();
        Optional.of(new Outer())
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);
    }

    public static void main(String[] args) throws Exception {
        FlatMapDemo flatMapDemo = new FlatMapDemo();
        flatMapDemo.nestedInner();
    }
}
