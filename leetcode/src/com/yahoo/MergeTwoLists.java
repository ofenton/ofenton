package com.yahoo;

/**
 * Created on 3/29/15.
 */
public class MergeTwoLists {

    MergeTwoLists() {
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        public String toString() {
            return "{" + val + ":" + next + "}";
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode current = null;

        // init head & current
        if (l1 == null && l2 == null) { return head; }
        if (l1 == null) { return l2; }
        if (l2 == null) { return l1; }

        while (l1 != null || l2 != null) {

            if (l1 == null) {
                if (current == null) {
                    head = l2;
                    break;
                } else {
                    current.next = l2;
                    break;
                }
            }

            if (l2 == null) {
                if (current == null) {
                    head = l1;
                    break;
                } else {
                    current.next = l1;
                    break;
                }
            }

            // Else
            if (l2.val < l1.val) {
                ListNode next = l2;
                if (current == null) {
                    head = next;
                    current = head;
                    l2 = l2.next;
                } else {
                    current.next = l2;
                    current = current.next;
                    l2 = l2.next;
                }
            } else {
                ListNode next = l1;
                if (current == null) {
                    head = next;
                    current = head;
                    l1 = l1.next;
                } else {
                    current.next = l1;
                    current = current.next;
                    l1 = l1.next;
                }
            }
        }

        return head;
    }

    public static void main(String args[]) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(1);
        System.out.println(new MergeTwoLists().mergeTwoLists(l1, l2));
    }
}
