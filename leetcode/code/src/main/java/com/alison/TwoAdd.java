package com.alison;

public class TwoAdd {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode getTwoNumber(ListNode l1, ListNode l2) {
        //preNode
        ListNode dumyHead = new ListNode(0);
        ListNode p1 = l1, p2 = l2, curr = dumyHead;
        int carry = 0;
        while (p1 != null || p2 != null) {
            int x = p1 == null ? 0 : p1.val;
            int y = p2 == null ? 0 : p2.val;
            int sum = x + y + carry;
            curr.next = new ListNode(sum % 10);
            carry = sum / 10;
            curr = curr.next;
            if (p1 != null) p1 = p1.next;
            if (p2 != null) p2 = p2.next;
        }
        if (carry == 1)
            curr.next = new ListNode(1);
        return dumyHead.next;
    }

    public ListNode getTwoNumber2(ListNode l1, ListNode l2) {
        ListNode curr = new ListNode(0);
        ListNode dump = curr;
        while (l1 != null || l2 != null) {
            int sum = l1.val + l2.val;
            curr.next = new ListNode(sum);
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            curr = curr.next;
        }
        curr = dump.next;
        while (curr != null) {
            if (curr.val > 10)
                curr.val = curr.val % 10;
            if (curr.next == null)
                curr.next = new ListNode(curr.val / 10);
            else
                curr.next.val += 1;
            curr = curr.next;
        }
        return dump.next;
    }

}
