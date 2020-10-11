package com.alison;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author alison
 * @Date 2019/6/13  22:31
 * @Version 1.0
 */
public class LonggestSubstring {


    // 最容易想到的一种算法，也是效率最低的一种算法
    //
    //通过两次遍历得到所有可能的 子字符串 列表
    //将每个字符串传入一个函数检测是否包含重复字符，如果不包含则更新最长子串的长度
    public int lengthOfLongestSubstring01(String s) {
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (isUnique(s, i, j)) {
                    max = Math.max(max, j - i);
                }
            }
        }
        return max;
    }

    private boolean isUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            char c1 = s.charAt(i);
            if (set.contains(c1)) { // 字符已存在，本字符串不符合条件
                return false;
            }
            set.add(c1);  // 添加字符
        }
        return true;
    }
    // 时间复杂度： O(n^3)
    // i循环，j循环，isUnquie中的循环，3次循还嵌套
    // 空间复杂度： O(min(n,m))
    // isUnique函数中定义了一个数组来存储不重复的子串字符，长度为k,k的长度取决于字符串s的大小n以及 字符串ss包含的不重复字符数大小m

    // 优化
//    滑动窗口法

    // abcabcdd
    // 不断移动i,j , 存储不重复的数据, set
    // 我们使用 HashSet 将字符存储在当前窗口 [i,j)（最初 j=i）中。 然后我们向右侧滑动索引 j，
    // 如果它不在 HashSet 中，我们会继续滑动 j。直到 s[j] 已经存在于 HashSet 中。
    // 此时，我们找到的没有重复字符的最长子字符串将会以索引 i 开头。如果我们对所有的 i 这样做，就可以得到答案。
    public int lengthOfLongestSubstring002(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            // 不包含其中，添加到set中， j++, 求出字串的最大值，避免后期无法求出字符长度
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                j++;
                // 不断求出字串的长度最大
                // 每次用出现过的窗口大小来更新结果res
                ans = Math.max(ans, j - i);
            } else {
                // 包含其中， 移除该字符,因为之前已经求出最长字符长度，所以在这里不用担心字符丢失，
                // i->j
                set.remove(s.charAt(i));
                i++;
            }
        }
        return ans;
    }
    // 时间复杂度： O(2n)
    // 空间复杂度： O(min(n,m))

    // 优化： 滑动窗口
    // map, key=char, value=i+1
    // [i,j] 不断移动，保持无重复最大字串
    //
    // 求无重复字符的最长子串长度
    // aaaaaa
    // abcds
    // qwewekw
    public int lengthOfLonggestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int res = 0;
        int start = 0, end = 0;
        int len = s.length();
        while (end < len) {
            Character alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                // 包含，将start移动到存在的字符下标+1
                // 为什么  i = Math.max(i, map[char]) 不能直接是 i = map[char]?
                //  ii 的位置比map[char]大的情况下如果直接赋值会导致 ii 往前面走，
                //  会导致返回的子串长度大于实际的子串长度
                // abba
                start = Math.max(start, map.get(alpha));
            }
            // 不包含，就存储字串与 位置下标+1, 永远是最新的下标+1
            map.put(alpha, end + 1);
            //我们维护一个结果res，每次用出现过的窗口大小来更新结果res
            res = Math.max(res, end - start + 1);
            end++;
        }
        return res;
    }

    // 求无重复字符的最长子串
    public String longgestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int res = 0;
        int start = 0, end = 0;
        for (; end < s.length(); end++) {
            Character alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(start, map.get(alpha));
            }
            map.put(alpha, end + 1);
            res = Math.max(res, end - start + 1);
        }
        System.out.println(map);
        System.out.println(start + "-" + end);
        return s.substring(start, end);
    }

    // 求最长子序列
    public int lengthOfStr(String str) {
        Set<Character> charSet = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            Character cha = str.charAt(i);
            if (!charSet.contains(cha)) {
                charSet.add(cha);
            }
        }
        System.out.println(charSet);
        return charSet.size();
    }

    public static void main(String[] args) {
        LonggestSubstring l = new LonggestSubstring();
        String str = "abcabcbb";
//        String str = "bbbb";
//        String str = "pwwkew";
//        int res = l.lengthOfLonggestSubstring(str);
//        System.out.println(res);
        System.out.println(l.lengthOfLonggestSubstring(str));
        System.out.println(l.longgestSubstring(str));
    }

}
