package com.alison;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrDemo {

    public static void main(String[] args) {
        String str = "123456";
        char[] s = str.toCharArray();
        StrDemo strDemo = new StrDemo();
        strDemo.reverseString(s);
        System.out.println(s);
    }

    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left <= right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
            left++;
            right--;
        }
    }

    /**
     * 先处理字符串，将首尾空格都删除；
     * 倒序遍历字符串，当第一次遇到空格时，添加s[i + 1: j]（即添加一个完整单词）；
     * 然后，将直至下一个单词中间的空格跳过，并记录下一个单词尾部j；
     * 继续遍历，直至下一次遇到第一个空格，回到1.步骤；
     * 由于首部没有空格，因此最后需要将第一个单词加入，再return。
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        s = s.trim();
        StringBuffer sb = new StringBuffer();
        int i = s.length() - 1, j = s.length();
        while (i > 0) {
            if (s.charAt(i) == ' ') {
                sb.append(s.substring(i + 1, j));
                sb.append(' ');
                while (s.charAt(i) == ' ') {
                    i--;//ignore extra spaces between words
                }
                j = i + 1;
            }
            i--;
        }
        return sb.append(s.substring(0, j)).toString();
    }

    public int myAtoi(String str) {
        str = str.trim();
        //正则表达式，表示以正号或负号或数字开头，且后面是0个或多个数字
        String pattern = "^[\\+\\-\\d]\\d*$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        String res = "";
        if (m.find()) {
            res = str.substring(m.start(), m.end());
        } else {
            return 0;
        }
        if (res.length() == 1 && (res.equalsIgnoreCase("+") || res.equalsIgnoreCase("-"))) {
            return 0;
        }

        try {
            int i = Integer.parseInt(res);
            return i;
        } catch (Exception e) {
            return res.equalsIgnoreCase("-") ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }

    /**
     * 假想使用插入合格元素的方式获取合格的字符串。
     * 循环遍历整个字符串，将合格的元素放到新的字符串中，如果遇到一个不合格就立马停止循环，忽略剩余元素
     * 时间复杂度为o(n) n为字符串的长度
     */
    public int myAtoi2(String str) {
        //去除掉前后的空格
        String strr = str.trim();
        //存储最终过滤出来的字符串
        String strrr = null;
        //字符串不为空时并且字符串不全是空白字符串时才转换
        if (strr != null && !strr.isEmpty()) {
            char f = strr.charAt(0);
            //判断字符串中的第一个非空格字符是不是一个有效整数字符
            if (f >= '0' && f <= '9' || f == '+' || f == '-') {
                strrr = strr.substring(0, 1); // 把第一位放进去(只能是数字、正负号)
                //这时候循环只要数字，因为正负号只能出现在第一位
                for (int i = 1; i < strr.length(); i++) {
                    if (strr.charAt(i) >= '0' && strr.charAt(i) <= '9') {
                        strrr = strr.substring(0, i + 1);
                    } else {
                        //这是遇到不符合要求的字符，直接忽略剩余元素
                        break;
                    }
                }
            }
        }
        //判断最终字符串是否为空或则只有一个正负号
        if (strrr == null || strrr.equals("+") || strrr.equals("-"))
            //此时strrr是String对象，如果使用==比较则比较的时内存地址
            return 0;
        //最终转换成的数字
        int num = 0;
        //使用异常机制打印结果
        try {
            num = Integer.parseInt(strrr);
        } catch (Exception e) {
            return (strrr.charAt(0) == '-') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return num;
    }
}
