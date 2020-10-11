package com.alison;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author alison
 * @Date 2019/8/3  23:43
 * @Version 1.0
 * @Description
 */
public class ListDemo {

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            this.val = x;
        }

        ListNode(Integer[] nums) {
            ListNode currNode = this;
            currNode.val = nums[0];
            for (int i = 1; i < nums.length; i++) {
                currNode.next = new ListNode(nums[i]);
                currNode = currNode.next;
            }
        }
    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTmp;
        }
        return prev;
    }

    public ListNode mergeTwoList(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoList(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoList(l1, l2.next);
            return l2;
        }
    }

    public static ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode slow = head, fast = head;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        if (len == 0)
            return null;
        return mergeKLists(lists, 0, len - 1);
    }

    // 分治算法
    // 还可以用-优先队列PriorityQueue
    // 还可以用-最小堆实现
    public ListNode mergeKLists(ListNode[] lists, int left, int right) {
        if (left == right)
            return lists[0];
        int mid = (right - left) / 2;
        ListNode l1 = mergeKLists(lists, left, mid);
        ListNode l2 = mergeKLists(lists, mid + 1, right);
        return mergeTwoList(l1, l2);
    }

    // 优先队列PriorityQueue
    public static ListNode mergeKLists2(ListNode[] lists) {
        int len = lists.length;
        int sumLen = 0;
        if (len == 0)
            return null;
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(item -> item.val));
        for (ListNode list : lists) {
            if (list != null)
                priorityQueue.add(list);
        }
        ListNode dummyNode = new ListNode(-1);
        ListNode curr = dummyNode;
        while (!priorityQueue.isEmpty()) {
            ListNode node = priorityQueue.poll();
            // 指向刚出队的ListNode，ListNode 开头是一个Node(0)
            curr.next = node;
            // 指向实际数值
            curr = curr.next;
            if (curr.next != null) {
                priorityQueue.add(curr.next);
            }
        }
        return dummyNode.next;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    public static void main(String[] args) {
        Integer[] nums1 = {1, 4, 5};
        Integer[] nums2 = {1, 3, 4};
        Integer[] nums3 = {2, 6};
        ListNode head1 = new ListNode(nums1);
        ListNode head2 = new ListNode(nums2);
        ListNode head3 = new ListNode(nums3);
        ListNode[] lists = new ListNode[3];
        lists[0] = head1;
        lists[1] = head2;
        lists[2] = head3;
        ListDemo listDemo = new ListDemo();
        ListNode mergeKLists = listDemo.mergeKLists2(lists);
        System.out.println(mergeKLists);
    }

}
