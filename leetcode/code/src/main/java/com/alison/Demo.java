package com.alison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: huangyong
 * @date: 2019/6/27
 * @version: 0.1
 **/
public class Demo {
    public static void main(String[] args) {
        String s = "ADOBECODEBANCA";
        String t = "ABC";
        t = minWindow(s, t);
        System.out.println(t);
    }

    public static String minWindow(String s, String t) {
        int start = 0, min_len = Integer.MAX_VALUE;
        int left = 0, right = 0;
        Map<Character, Integer> needs = new HashMap();
        Map<Character, Integer> window = new HashMap();
        for (char c : t.toCharArray()) {
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }
        int match = 0;
        String res = "";
        while (right < s.length()) {
            char c1 = s.charAt(right);
            if (needs.containsKey(c1)) {
                window.put(c1, window.getOrDefault(c1, 0) + 1);
                if (window.get(c1).compareTo(needs.get(c1)) == 0) {
                    match++;
                }
            }
            right++;
            while (match == needs.size()) {
                if (min_len > right - left) {
                    min_len = right - left;
                    res = s.substring(left, right);
                }
                char c2 = s.charAt(left);
                if (needs.containsKey(c2)) {
                    window.put(c2, window.get(c2).intValue() - 1);
                    if (window.get(c2).compareTo(needs.get(c2)) < 0) {
                        match--;
                    }
                }
                left++;
            }
        }
        return res;
    }

    public static String minWindow2(String s, String t) {
        int start = 0, min_len = Integer.MAX_VALUE;


        return null;
    }

    public static List<Integer> findAnagrams(String s, String t) {
        List<Integer> res = new ArrayList<>();
        int left = 0, right = 0;
        Map<Character, Integer> needs = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : t.toCharArray()) {
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }
        int match = 0;
        while (right < s.length()) {
            char c1 = s.charAt(right);
            if (needs.containsKey(c1)) {
                window.put(c1, window.getOrDefault(c1, 0) + 1);
                if (window.get(c1).intValue() == needs.get(c1).intValue()) {
                    match++;
                }
            }
            right++;
            while (match == needs.size()) {
                if (right - left == t.length()) {
                    res.add(left);
                }
                char c2 = s.charAt(left);
                if (needs.containsKey(c2)) {
                    window.put(c2, window.get(c2) - 1);
                    if (window.get(c2).intValue() < needs.get(c2).intValue()) {
                        match--;
                    }
                }
                left++;
            }
        }
        return res;
    }

    public static int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();
        int res = 0;
        while (right < s.length()) {
            char c1 = s.charAt(right);
            window.put(c1, window.getOrDefault(c1, 0) + 1);
            right++;
            // 收缩指针
            while (window.get(c1) > 1) {
                char c2 = s.charAt(left);
                window.put(c2, window.get(c2) - 1);
                left++;
            }
            res = Math.max(res, right - left);
        }
        return res;
    }

    public static int lengthOfLongestSubstring2(String s) {
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();
        int ans = 0, len = s.length();
        while (right < len) {
            Character alpha = s.charAt(right);
            if (window.containsKey(alpha)) {
                left = Math.max(window.get(alpha), left);
            }
            window.put(alpha, right + 1);
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
