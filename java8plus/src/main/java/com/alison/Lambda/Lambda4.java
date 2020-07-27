package com.alison.Lambda;

import com.alison.Functional.Converter;

public class Lambda4 {
    static int outerStaticNum;
    int outerNum;
    {
        outerNum = 22;
    }

    void testScopes() {
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            outerNum = 24;
            return String.valueOf(from);
        };

        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }
}
