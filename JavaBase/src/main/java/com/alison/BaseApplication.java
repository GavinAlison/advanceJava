package com.alison;

import java.util.stream.Stream;

public class BaseApplication {
    public static void main(String[] args) {
        System.out.println(Stream.of("1,2,3,4").max((o1,o2)->o1.compareTo(o2)).get());
    }
}
