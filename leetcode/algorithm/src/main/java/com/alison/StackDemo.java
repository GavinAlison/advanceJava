package com.alison;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Author alison
 * @Date 2019/8/7  22:39
 * @Version 1.0
 * @Description
 */
public class StackDemo {

    public boolean isValid(String s) {
        Map<Character, Character> mapping = new HashMap<>();
        mapping.put(']', '[');
        mapping.put(')', '(');
        mapping.put('}', '{');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (mapping.containsKey(c)) {
                char topElement = stack.isEmpty() ? '#' : stack.pop();
                if (mapping.get(c).compareTo(topElement) != 0)
                    return false;
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }


    public int longestValidParentheses(String s) {
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

    public int longestValidParenthesses(String s) {
        int maxlen = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 2; j <= s.length(); j += 2) {
                if (isValidTwo(s.substring(i, j))) {
                    maxlen = Math.max(maxlen, j - i);
                }
            }
        }
        return maxlen;
    }

    public boolean isValidTwo(String s) {
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push('(');
            } else if (!stack.empty() && stack.peek() == '(') {
                stack.pop();
            } else {
                return false;
            }
        }
        return true;
    }

    public int longestValidParentheses3(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }


    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack();
        String operation = "+-*/";
        for (int i = 0; i < tokens.length; i++) {
            if (!operation.contains(tokens[i])) {
                stack.push(Integer.valueOf(tokens[i]));
            } else {
                int v1 = (Integer) stack.pop();
                int v2 = (Integer) stack.pop();
                if (tokens[i].equals("+")) {
                    stack.push(v1 + v2);
                }
                if (tokens[i].equals("-")) {
                    stack.push(v1 - v2);
                }
                if (tokens[i].equals("*")) {
                    stack.push(v1 * v2);
                }
                if (tokens[i].equals("/")) {
                    stack.push(v1 / v2);
                }
            }
        }
        return (Integer) stack.pop();
    }

    public static void main(String[] args) {
        String s = "[][][][][]";
        StackDemo stackDemo = new StackDemo();
        System.out.println(stackDemo.isValid(s));
        String[] arr = {"2","1","+","3","*"};
        stackDemo.evalRPN(arr);

    }
}
