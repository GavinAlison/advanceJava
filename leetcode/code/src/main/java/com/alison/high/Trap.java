package com.alison.high;


public class Trap {
    // 思路： 局部节点，求位置i上有多少水滴， 需要左边最大值和右边最大值，形成一个木桶， 此时才可以进行存储水滴
    // min(rmax, lmax) -height[i]
    // 注意 第一个，后最后一个无法形成木桶
    // 时间复杂度： O(n^2)
    public int trap(int[] height) {
        int sum = 0;
        int len = height.length;
        int lmax =0, rmax= 0;
        for(int i = 1 ; i< len-1; i++){
            for(int j = i+1; j<len; j++){
                rmax = height[j] > rmax ? height[j] : rmax;
            }
            for(int j= i-1; j>=0; j--){
                lmax = height[j]> lmax ? height[j]:lmax;
            }
            sum += ( ( lmax > rmax ? rmax : lmax ) - height[i]);
        }
        return sum;
    }
    //优化，使用备忘录优化，先记录所有i上左最大值，右最大值, 存储到一个数组中
    public int trap2(int[] height) {
        int sum = 0;
        int len = height.length;
        int[] lmax = new int[len];
        int[] rmax = new int[len];
        lmax[0]
        for(int i = 1 ; i< len-1; i++){
            for(int j= i-1; j>=0; j--){
                lmax = height[j]> lmax ? height[j]:lmax;
            }
            sum += ( ( lmax > rmax ? rmax : lmax ) - height[i]);
        }
        return sum;
    }
}
