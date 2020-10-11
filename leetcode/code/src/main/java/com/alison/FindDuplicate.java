package com.alison;

public class FindDuplicate {
    public int findDuplicate(int[] nums) {
        int len = nums.length;
        int left = 1;
        int right = len - 1;
        while (left < right) {
            // int mid = left + (right - left + 1) / 2;
            int mid = (left + right + 1) >>> 1;
            int counter = 0;
            for (int num : nums) {
                if (num < mid) {
                    counter += 1;
                }
            }
            if (counter >= mid) {
                // 如果小于 4 的个数等于 4 或者更多
                // 那么重复的数一定位于 1、2、3
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        return left;
    }

    public int searchInsert(int[] nums, int target) {
        int len = nums.length;
        if (nums[len - 1] < target) {
            return len;
        }
        int left = 0;
        int right = len - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            // 等于的情况最简单，我们应该放在第 1 个分支进行判断
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                // 题目要我们返回大于或者等于目标值的第 1 个数的索引
                // 此时 mid 一定不是所求的左边界，
                left = mid + 1;
            } else {
                // 既然不会等于，此时 nums[mid] > target
                // mid 也一定不是所求的右边界
                right = mid - 1;
            }
        }
        return left;
    }


    public int findDuplicateDouble(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];
            if (slow == fast) break;
        }
        fast = nums[0];
        while (fast != slow) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
    // index:           0  1  2  3  4
    // value:           1, 3, 4, 2, 2
    // init:        slow=1 , fast=1
    // init1:       slow=3 , fast=2
    // init2:       slow=2 , fast=2
    //              fast=1
    //              slow=4, fast=3
    //              slow=2, fast=2
    //   2
//    ------------------
// index:            0,1,2,3,4,5,6,7,8,9
    // value:           [2,5,9,6,9,3,8,9,7,1]
    // init:        slow=2 , fast=2
    // init1:       slow=9 , fast=1
    // init2:       slow=1 , fast=3
    // init3:       slow=5 , fast=8
    // init4:       slow=3 , fast=9
    // init5:       slow=6 , fast=5
    // init5:       slow=8 , fast=6
    // init5:       slow=7 , fast=7
    //              fast=2
    //              slow=9, fast=9
    //   9
    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 2, 2};
        nums = new int[]{3, 1, 3, 4, 2, 5};
        int[] numss = {1, 3, 4, 5, 7, 8, 9, 10};
        FindDuplicate findDuplicate = new FindDuplicate();
//        int res = findDuplicate.findDuplicate(nums);
//        System.out.println(res);
        findDuplicate.searchInsert(numss, 6);


    }

}
