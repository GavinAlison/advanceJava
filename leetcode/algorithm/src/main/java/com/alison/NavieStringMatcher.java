package com.alison;

import java.util.Arrays;

/**
 * 朴素匹配算法
 * 被搜索的字符串称为主串，待搜索的字符串称为模式串。朴素模式匹配算法的基本思想：
 * <p>
 * 对主串的每一个字符作为子串开头，与模式串进行匹配。对主串做大循环，每个字符开头做模式串长度的小循环，直到匹配成功或全部遍历完成为止。
 */
public class NavieStringMatcher {
    public int simple(String s, String t) {
        char[] S = s.toCharArray();
        char[] T = t.toCharArray();
        int lenS = S.length;
        int lenT = T.length;
        int i, j;//i遍历S,j遍历T
        for (i = 0; i <= lenS - lenT; i++) {
            for (j = 0; j < lenT; j++) {
                if (S[i + j] != T[j])
                    break;
            }
            if (j == lenT)
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        String T = "欢迎访问fjssharpsword博客，致力于大数据应用解决方案提供！";
        String P = "大数据应用解决方案";
        NavieStringMatcher nsm = new NavieStringMatcher();
        int index = nsm.stringMatcher(T, P);
        System.out.println("有效位移是： " + index);
    }

    /**
     * @param T 主字符串
     * @param P 模式字符串
     * @return s 有效位移
     * @author fjssharpsword
     */
    public int stringMatcher(String T, String P) {
        int iTLen = T.length();
        int iPLen = P.length();
        // 从主串开始比较
        for (int i = 0; i < iTLen; i++) {
            int k = i; // k指向主串下一个位置
            for (int j = 0; j < iPLen; j++) {
                if (T.charAt(k) != P.charAt(j)) {
                    break;
                } else {
                    k++;// 指向主串下一个位置
                    if (j == iPLen - 1) {
                        return i;
                    }
                }
            }
        }
        return -1;  // 匹配不成功，返回-1
    }

    //最精简的KMP
    //平均时间复杂度为O(m+n) 空间复杂度为O(n)
    public int kmp(String s, String t) {
        char[] S = s.toCharArray();
        char[] T = t.toCharArray();
        int lenS = S.length;
        int lenT = T.length;
        int[] next = getNext(t);
        System.out.println(t + "的next数组为" + Arrays.toString(next));
        //i不断往前递进1, j根据next数组移动
        for (int i = 0, j = 0; i < lenS; ++i) {
            while (j > 0 && S[i] != T[j])
                // 失去匹配时 j移动到next[j-1]值的位置
                j = next[j - 1];
            if (S[i] == T[j]) {
                j++;
            }
            if (j == lenT) {
                //返回索引
                return i - j + 1;
            }
        }
        return -1;
    }

    // 求T串的next数组
    // 动态规划法 求[0,i]字符串的最长公共前后缀
    public int[] getNext(String t) {
        char[] T = t.toCharArray();
        int len = T.length;
        int[] next = new int[len];
        // next[i]=k表示第[0,i]的字符串的最长公共前后缀长度为k
        next[0] = 0;
        // i 遍历[1,len-1]
        for (int i = 1; i < len; i++) {
            //这个条件记住
            //"abcabc" next[4]=2 if(T[5]=T[next[4]]) next[5]=next[4]+1
            if (T[i] == T[next[i - 1]]) {
                next[i] = next[i - 1] + 1;
            }
        }
        return next;
    }
}
