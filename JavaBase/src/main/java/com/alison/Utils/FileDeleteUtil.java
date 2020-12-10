package com.alison.Utils;

import org.junit.Test;

import java.io.File;

/**
 * @author 梁慧琴
 * @date 2019/8/15
 */
public class FileDeleteUtil {

    @Test
    public void test (){
        String name = "aaaa_1234352.xlsx";
        File file = new File(name);
        String[] suf = file.getName().split("_");
        int dot = file.getName().indexOf(".");

        System.out.println(file.getName().substring(0,dot));

        String[] s = file.getName().substring(0,dot).split("_");
        System.out.println(Integer.parseInt(s[1]));

    }
}
