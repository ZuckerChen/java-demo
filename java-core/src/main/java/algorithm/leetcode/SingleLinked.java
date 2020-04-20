package algorithm.leetcode;

import java.util.Stack;

/**
 * @author zucker
 * @description 单链表
 *
 * @date: 2020/4/7 5:28 PM
 */
public class SingleLinked {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
//        testCombineSortLinked();
//        testSwapPairs();
        testAddTwoNumbers();
    }

    /**
     * 1-4-5
     * 1-3-4-9
     */
    private static void testAddTwoNumbers() {
        SingleLinked singleLinked = new SingleLinked();
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(4);
        node1.next.next = new ListNode(5);
        ListNode node2 = new ListNode(1);
        node2.next = new ListNode(3);
        node2.next.next = new ListNode(4);
        node2.next.next.next = new ListNode(9);


        ListNode res = singleLinked.addTwoNumbers(node1, node2);

        System.out.println("合并后的链表:");
        while (res != null) {
            System.out.print(res.val + "->");
            res = res.next;
        }

    }


    /**
     * 两个数相加
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> stack1 = new Stack();
        Stack<Integer> stack2 = new Stack();

        while (null != l1) {
            stack1.add(l1.val);
            l1 = l1.next;
        }

        while (null != l2) {
            stack2.add(l2.val);
            l2 = l2.next;
        }

        ListNode res = null;

        int count = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || count > 0) {
            int sum = (stack1.isEmpty() ? 0 : stack1.pop()) + (stack2.isEmpty() ? 0 : stack2.pop()) + count;
            ListNode tmp = new ListNode(sum % 10);
            tmp.next = res;
            res = tmp;
            count = sum / 10;
        }
        return res;
    }


    public ListNode removeDuplicateNodes(ListNode head) {


        return null;
    }

    /**
     * k个一组翻转链表
     * 示例：
     * <p>
     * 给你这个链表：1->2->3->4->5
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     * 要求：
     * 你的算法只能使用常数的额外空间。
     * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        return null;
    }

    /**
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 给定 1->2->3->4, 你应该返回 2->1->4->3.
     */
    public ListNode swapPairs(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        //交换
        head.next = swapPairs(next.next);
        next.next = head;
        //现在head是第二个节点
        return next;
    }


    /**
     * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
     * 分治法，归并；
     * 时间复杂度nlog2n
     */
    public ListNode combineSortLinked(ListNode[] lists) {
        return divide(0, lists.length - 1, lists);
    }

    private ListNode divide(int left, int right, ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        int mid = (left + right) / 2;

        if (left == right) {
            return lists[left];
        }

        if (left < mid) {
            ListNode leftNode = divide(left, mid, lists);
            ListNode rightNode = divide(mid + 1, right, lists);
            return combine(leftNode, rightNode);
        } else {
            return combine(lists[left], lists[right]);
        }

    }

    private ListNode combine(ListNode leftNode, ListNode rightNode) {
        if (leftNode == null) {
            return rightNode;
        }
        if (rightNode == null) {
            return leftNode;
        }

        ListNode new_node = null;
        ListNode tail = null;
        while (leftNode != null || rightNode != null) {
            if (leftNode == null) {
                tail.next = rightNode;
                break;
            }
            if (rightNode == null) {
                tail.next = leftNode;
                break;
            }

            if (leftNode.val <= rightNode.val) {
                if (new_node == null) {
                    new_node = new ListNode(leftNode.val);
                    tail = new_node;
                } else {
                    tail.next = new ListNode(leftNode.val);
                    tail = tail.next;
                }

                leftNode = leftNode.next;
            } else {
                if (new_node == null) {
                    new_node = new ListNode(rightNode.val);
                    tail = new_node;
                } else {
                    tail.next = new ListNode(rightNode.val);
                    tail = tail.next;
                }

                rightNode = rightNode.next;
            }
        }
        return new_node;
    }

    private static void testCombineSortLinked() {
        SingleLinked singleLinked = new SingleLinked();
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(4);
        node1.next.next = new ListNode(5);
        ListNode node2 = new ListNode(1);
        node2.next = new ListNode(3);
        node2.next.next = new ListNode(4);
        ListNode node3 = new ListNode(2);
        node3.next = new ListNode(6);
        ListNode[] lists = new ListNode[]{node1, node2, node3};

        System.out.println("子链表:");
        for (ListNode list : lists) {
            while (list != null) {
                System.out.print(list.val + "->");
                list = list.next;
            }

            System.out.println();
        }

        ListNode res = singleLinked.combineSortLinked(lists);
        System.out.println("合并后的链表:");
        while (res != null) {
            System.out.print(res.val + "->");
            res = res.next;
        }
    }

    private static void testSwapPairs() {
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(2);
        node1.next.next = new ListNode(3);
        node1.next.next.next = new ListNode(4);
        System.out.println("交换前的链表:[1,2,3,4]");

        SingleLinked singleLinked = new SingleLinked();

        ListNode head = node1.next;
        singleLinked.swapPairs(node1);

        System.out.println("交换后的链表:");
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
    }
}
