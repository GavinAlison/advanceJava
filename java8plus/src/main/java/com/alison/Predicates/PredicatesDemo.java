package com.alison.Predicates;

import com.alison.Interfaces.Formula;

import java.util.Objects;
import java.util.function.Predicate;

public class PredicatesDemo {
    public static void main(String[] args) {
        Predicate<String> predicate = (s) -> s.length() > 0;

        predicate.test("foo");              // true
        predicate.negate().test("foo");     // false

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        Predicate<String> lte = s -> s.length() <= 0;
        System.out.println("" + lte.equals(""));

        Formula formula = (a) -> (a + 1.0);
        System.out.println(formula.calculate(9));
    }

    public static boolean getZero(String s) {
        return s.length() > 0;
    }
}
