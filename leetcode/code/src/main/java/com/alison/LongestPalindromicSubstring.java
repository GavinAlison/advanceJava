package com.alison;

/**
 * @Author alison
 * @Date 2019/6/24  21:19
 * @Version 1.0
 * @Description <br/>
 * 时间复杂度:  O(n^2)
 * 空间复杂度:  O(1)
 */
public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len == 0) {
            return "";
        }
        int longestPalindrome = 1;
        String longestPalindromeStr = s.substring(0, 1);
        for (int i = 0; i < len; i++) {
            String palindromeOdd = centerSpread(s, len, i, i);
            String palindromeEven = centerSpread(s, len, i, i + 1);
            String maxLen = palindromeOdd.length() > palindromeEven.length() ? palindromeOdd : palindromeEven;
            if (maxLen.length() > longestPalindrome) {
                longestPalindrome = maxLen.length();
                longestPalindromeStr = maxLen;
            }
        }
        return longestPalindromeStr;
    }

    private String centerSpread(String s, int len, int left, int right) {
        int l = left;
        int r = right;
        while (l >= 0 && r < len && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        // 这里要特别小心，跳出 while 循环的时候，是第 1 个满足 s.charAt(l) != s.charAt(r) 的时候
        // 所以，不能取 l，不能取 r
        return s.substring(l + 1, r);
    }

    public static void main(String[] args) {
//        String s = "babad";
        String s = "abba";
        LongestPalindromicSubstring l = new LongestPalindromicSubstring();
        String result = l.longestPalindrome(s);
        System.out.println(result);
    }
}
