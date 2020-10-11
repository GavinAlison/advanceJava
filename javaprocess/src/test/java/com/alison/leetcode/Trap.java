package com.alison.leetcode;

import org.junit.Test;

public class Trap {
    // 思路： 局部节点，求位置i上有多少水滴， 需要左边最大值和右边最大值，形成一个木桶， 此时才可以进行存储水滴
    // min(rmax, lmax) -height[i]
    // 注意 第一个，后最后一个无法形成木桶
    // 时间复杂度： O(n^2)
    public int trap(int[] height) {
        int sum = 0;
        int len = height.length;
        for (int i = 1; i < len - 1; i++) {
            int lmax = 0, rmax = 0;
            for (int j = i + 1; j < len; j++) {
                rmax = height[j] > rmax ? height[j] : rmax;
            }
            for (int j = i - 1; j >= 0; j--) {
                lmax = height[j] > lmax ? height[j] : lmax;
            }
            sum += ((lmax > rmax ? rmax : lmax) - height[i]);
        }
        return sum;
    }

    //优化，使用备忘录优化，先记录所有i上左最大值，右最大值, 存储到一个数组中
    // 从左到右，计算出lmax的最大值， 排除0， len-1
    // 从右到左，计算出rmax的最大值，排除0, len-1
    // 取出min_height, 这个值必须大于height[i],才有意义
    // 时间复杂度：O(n), 空间复杂度： O(2N)
    public int trap2(int[] height) {
        int sum = 0;
        int len = height.length;
        int[] lmax = new int[len];
        int[] rmax = new int[len];
        lmax[0] = height[0];
        rmax[len - 1] = height[len - 1];
        // 从左到右，计算出, 排除0
        for (int i = 1; i < len - 1; i++) {
            lmax[i] = Math.max(lmax[i], height[i]);
        }
        // 从右往左, 排除len-1
        for (int i = len - 2; i >= 1; i--) {
            rmax[i] = Math.max(rmax[i], height[i]);
        }
        for (int i = 1; i < len - 1; i++) {
            int min_heigh = Math.min(lmax[i], rmax[i]);
            if (min_heigh > height[i]) {
                sum += min_heigh - height[i];
            }
        }
        return sum;
    }

    // 快慢指针, 核心思想和之前一模一样, 差别是： l_max[i] 和 r_max[i] 代表的是 height[0..i] 和 height[i..end] 的最高柱子高度。
    // 但是双指针解法中，l_max 和 r_max 代表的是 height[0..left] 和 height[right..end] 的最高柱子高度
    public int trap3(int[] height) {
        int sum = 0;
        int len = height.length;
        int lmax = height[0];
        int rmax = height[len - 1];
        int left = 1;
        int right = len - 2;
        while (left <= right) {
            lmax = Math.max(lmax, height[left]);
            rmax = Math.max(rmax, height[right]);
            if (lmax < rmax) {
                if (lmax > height[left]) {
                    sum += lmax - height[left];
                }
                left++;
            } else {
                if (rmax > height[right]) {
                    sum += rmax - height[right];
                }
                right--;
            }
        }
        return sum;
    }

    @Test
    public void test() {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        Trap s = new Trap();
        System.out.println(s.trap3(height));
    }
}

