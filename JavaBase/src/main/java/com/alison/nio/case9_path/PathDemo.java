package com.alison.nio.case9_path;

import java.nio.file.Paths;

/**
 * @Author alison
 * @Date 2019/9/28  9:21
 * @Version 1.0
 * @Description
 */
public class PathDemo {
    private static void obtainPath() {
        System.out.println(Paths.get(".").toAbsolutePath());
        System.out.println(Paths.get("G:\\workspace\\advanceJava\\com\\..").normalize());
    }

    public static void main(String[] args) {
        obtainPath();
    }
}
