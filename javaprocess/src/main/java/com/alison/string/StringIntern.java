package com.alison.string;

/**
 * @Author alison
 * @Date 2020/2/29  17:49
 * @Version 1.0
 * @Description
 */
public class StringIntern {


    public static void main(String[] args) {

    }


    public void tern() {
        String str = "kvill";
        String copyStr = "kvill";
        String appendStr = "kv" + "ill";
        System.out.println(str == copyStr);
        System.out.println(str == appendStr);

        String newStr = new String("kvill");
        String contactNewStr = "kv" + new String("ill");
        System.out.println(str == newStr);
        System.out.println(str == contactNewStr);
        System.out.println(newStr == contactNewStr);


        str.intern();
        copyStr = copyStr.intern(); //把常量池中“kvill”的引用赋给copyStr
        System.out.println(str == copyStr);
        System.out.println(str == copyStr.intern());
        System.out.println(str == appendStr);
    }
}
