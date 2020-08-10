package com.alison.regex;

import lombok.experimental.UtilityClass;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTest {


    public static void main(String[] args) {
        System.out.println(RegularTest.isNumeric("sd21"));
        System.out.println(RegularTest.isNumeric("2113 23"));
        System.out.println(RegularTest.isNumeric(""));
        System.out.println(RegularTest.isNumeric("12#12"));
        System.out.println(RegularTest.isNumeric("-11212"));
        System.out.println(RegularTest.isNumeric("11212"));
        System.out.println(RegularTest.isNumeric("11212SDS"));
    }

    public static boolean isNumeric(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
//        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
//        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");//这个也行
        Pattern pattern = Pattern.compile("^\\d+$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    @Test
    public void test01(){
        String str ="GP_DataSource.branch_rate_info.1";
        System.out.println(str.replaceAll("\\.\\d+$", ""));
    }
}
