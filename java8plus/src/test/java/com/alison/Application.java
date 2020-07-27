package com.alison;


import com.alison.Interfaces.Formula;
import org.junit.Test;

public class Application {

    @Test
    public void test() {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };
        formula.calculate(100);
        formula.sqrt(16);
    }

}
