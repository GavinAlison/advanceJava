package com.alison.Lambda;

import com.alison.Functional.Converter;
import com.alison.Interfaces.Formula;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.StrictMath.sqrt;

public class LambdaDemo {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });
        Collections.sort(names, (String a, String b) -> b.compareTo(a));
        Collections.sort(names, String::compareTo);
        names.sort(String::compareTo);

        // scope
        final int num =1;
        Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);
        stringConverter.convert(2);
        int nums =1;
        stringConverter =  (from) -> String.valueOf(from + nums); // Immutable
//        nums =3;

//        Accessing Default Interface Methods
        Formula formula = (a) -> sqrt(a * 100);


    }

}
