package com.alison;


/**
 * @author: huangyong
 * @date: 2019/6/27
 * @version: 0.1
 **/
public class NodeReverse {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
    // 遍历列表， 将当前节点的next存储上一个节点，由于节点没有引用上一个节点，因此需要事先存储其前一个元素，在更改引用之前，
    // 还需要临时节点来存储下一个节点， 返回新的引用
    public ListNode reverseNode(ListNode node) {
        ListNode prev = null;
        ListNode curr = node;
        while (curr != null) {
            ListNode nextTmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTmp;
        }
        return prev;
    }

}
