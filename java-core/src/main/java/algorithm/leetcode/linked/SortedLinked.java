package algorithm.leetcode.linked;

/**
 * @author zucker
 * @description
 * @date: 2020/5/13 11:05 AM
 */
public class SortedLinked {


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public static ListNode sortList(ListNode head) {
        if(head ==null || head.next ==null){
            return head;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while(fast !=null && fast.next !=null){
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode mid = slow.next;
        slow.next = null;

        ListNode left  = sortList(head);
        ListNode right = sortList(mid);

        return merge(left,right);
    }

    private static ListNode merge(ListNode left, ListNode right){
        ListNode head = new ListNode(-1);
        ListNode tail  = head;

        while(left !=null||right !=null){
            if(left == null){
                tail.next = right;
                break;
            }
            if(right == null){
                tail.next = left;
                break;
            }
            if(left.val<right.val){
                tail.next = new ListNode(left.val);
                left = left.next;
            }else{
                tail.next = new ListNode(right.val);
                right = right.next;
            }
            tail = tail.next;
        }
        return head.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);

        ListNode res = sortList(head);

        while (res !=null){
            System.out.println(res.val+"->");
            res = res.next;
        }

    }

}
