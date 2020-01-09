package com.xudadong.leetcode.arithmetic;

import com.xudadong.leetcode.contract.RegularModel;

import java.io.Serializable;

/**
 * 单链表反转
 * <p>
 * Created by didi on 2019-07-10.
 */
public class ReverseLinkedList extends RegularModel<ReverseLinkedList.Node, ReverseLinkedList.Node> {

    @Override
    public String getTitle() {
        return "单链表反转";
    }

    @Override
    public String getDesc() {
        return "输入: 1->2->3->4->5->NULL，输出: 5->4->3->2->1->NULL";
    }

    @Override
    public Node getInput() {
        Node[] nodeArray = new Node[5];
        Node<Integer> prev = new Node<>(1, null);
        for (int i = prev.value; i <= nodeArray.length; i++) {
            Node<Integer> next = new Node<>(i + 1, null);
            if (i < nodeArray.length) {
                prev.next = next;
            }
            nodeArray[i - 1] = prev;
            prev = next;
        }
        return nodeArray[0];
    }

    //方案1：迭代，时间复杂度：O(n)，空间复杂度：O(1)
    @Override
    public ReverseLinkedList.Node execute(Node head) {
        Node prev = null;
        Node curr = head;

        while (curr != null) {
            Node tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
        }
        return prev;
    }

    //方案2：递归，时间复杂度：O(n)，空间复杂度：O(n)
    @Override
    public ReverseLinkedList.Node execute2(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node temp = execute2(head.next);
        head.next.next = head;
        head.next = null;
        return temp;
    }

    @Override
    public String getResult(Node result) {
        StringBuffer sb = new StringBuffer();
        while (result != null) {
            sb.append(result.value + "->");
            result = result.next;
        }
        sb.append("NULL");
        return sb.toString();
    }

    @Override
    public Complexity getTimeComplexity() {
        return Complexity.oN;
    }

    @Override
    public Complexity getSpaceComplexity() {
        return Complexity.o1;
    }

    static class Node<T extends Serializable> implements Serializable {
        Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        T value;
        Node next;
    }
}
