package com.alison;


public class StringUtils {

    public static String camlcase(String text) {
        char[] chars = text.toCharArray();
        StringBuffer sb = new StringBuffer(String.valueOf(chars[0]));
        for (int i = 1; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                sb.append("_");
            }
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    public static String originText(String text) {
        return text;
    }

    public static void main(String[] args) {
        System.out.println(camlcase("pmHtModel"));
    }
}
