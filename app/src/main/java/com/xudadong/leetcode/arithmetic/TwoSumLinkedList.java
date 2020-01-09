package com.xudadong.leetcode.arithmetic;

import com.xudadong.leetcode.contract.RegularModel;

import java.io.Serializable;
import java.util.Stack;

/**
 * 单链表相加
 * <p>
 * Created by didi on 2019-09-25.
 */

public class TwoSumLinkedList extends RegularModel<TwoSumLinkedList.ListNode[], TwoSumLinkedList.ListNode> {

    @Override
    public String getTitle() {
        return "单链表相加";
    }

    @Override
    public String getDesc() {
        return "给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。\n" +
                "如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。\n" +
                "您可以假设除了数字0之外，这两个数都不会以0开头。\n" +
                "输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)\n" +
                "输出：7 -> 0 -> 8\n" +
                "原因：342 + 465 = 807";
    }

    @Override
    public ListNode[] getInput() {
        ListNode[] input = new ListNode[2];
        input[0] = new ListNode(3).addHeadNode(new ListNode(4)).addHeadNode(new ListNode(2));
        input[1] = new ListNode(4).addHeadNode(new ListNode(6)).addHeadNode(new ListNode(5));
        return input;
    }

    @Override
    public ListNode execute(ListNode[] input) {
        return addTwoNumbers2(input[0], input[1]);
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        int sum;
        boolean isCarry = false;
        Stack<Integer> stack = new Stack<>();

        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + (isCarry ? 1 : 0);
            if (sum >= 10) {
                stack.push(sum % 10);
                isCarry = true;
            } else {
                stack.push(sum);
                isCarry = false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        if (l1 == null) {
            while (l2 != null) {
                sum = l2.val + (isCarry ? 1 : 0);
                if (sum >= 10) {
                    stack.push(sum % 10);
                    isCarry = true;
                } else {
                    stack.push(sum);
                    isCarry = false;
                }
                l2 = l2.next;
            }
        } else {
            while (l1 != null) {
                sum = l1.val + (isCarry ? 1 : 0);
                if (sum >= 10) {
                    stack.push(sum % 10);
                    isCarry = true;
                } else {
                    stack.push(sum);
                    isCarry = false;
                }
                l1 = l1.next;
            }
        }
        if (isCarry) {
            stack.push(1);
        }
        ListNode result = null;
        while (!stack.empty()) {
            int num = stack.pop();
            if (result == null) {
                result = new ListNode(num);
            } else {
                ListNode head = new ListNode(num);
                head.next = result;
                result = head;
            }
        }
        return result;
    }

    private ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode listNode = new ListNode(0);
        int sum = 0;
        ListNode cur = listNode;
        while (l1 != null || l2 != null) {
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            cur.next = new ListNode(sum % 10);
            sum /= 10;
            cur = cur.next;
        }
        if(sum >= 1) {
            cur.next = new ListNode(sum);
        }
        return listNode.next;
    }

    @Override
    public String getResult(ListNode result) {
        StringBuffer sb = new StringBuffer();
        while (result != null) {
            sb.append(result.val).append(" -> ");
            result = result.next;
        }
        sb.append("NULL");
        return sb.toString();
    }

    static class ListNode implements Serializable {
        int val;
        ListNode next;
        public ListNode(int x) {
            val = x;
        }

        ListNode addHeadNode(ListNode head) {
            head.next = this;
            return head;
        }
    }
}
