package com.alison.Method;

import com.alison.Functional.Converter;

public class MethodDemod {

    public static void main(String[] args) {
        // reference a static method
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);
        // reference object methods
        Something something = new Something();
        Converter<String, String> strConverter = something::startsWith;
        String strConverted = strConverter.convert("Java");
        System.out.println(converted);    // "J"

        // how the :: keyword works for constructors
        PersonFactory<Person> personPersonFactory = Person::new;
        Person person = personPersonFactory.create("Petter", "Parker");


    }

    static class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

}
