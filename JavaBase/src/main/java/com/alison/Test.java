package com.alison;

public class Test {

    public static void main(String[] args) {
        String a = "ab";
        // 在编译期已经常量折叠为
        // ，javac会进行bai常量折叠，全字面量字符串相加是可以折叠为一个字面常量，而且是进入常量池的。
        // 这个问题涉及到了字符串常量池和字符串拼接。
        String b = "a" + "b";
        String c = "a";
        String cc = c + "b";
        System.out.println(cc == a);
        System.out.println(cc == b);
        System.out.println(a == b);
        System.out.println(a.hashCode() + "-" + b.hashCode() + "-" + cc.hashCode());
        System.out.println(cc.equals(a));
    }
}
