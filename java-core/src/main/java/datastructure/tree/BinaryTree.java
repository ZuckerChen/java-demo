package datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: Zucker
 * @Date: 2020-01-16 16:02
 * @Description
 */
public class BinaryTree {
    public static class TreeNode {
        TreeNode leftLeaf;
        TreeNode rightLeaf;
        int data;

        public TreeNode(int data) {
            this.data = data;
        }

        public TreeNode getLeftLeaf() {
            return leftLeaf;
        }

        public void setLeftLeaf(TreeNode leftLeaf) {
            this.leftLeaf = leftLeaf;
        }

        public TreeNode getRightLeaf() {
            return rightLeaf;
        }

        public void setRightLeaf(TreeNode rightLeaf) {
            this.rightLeaf = rightLeaf;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }


    /**
     * 9
     * 8 7
     * _ 5 3 2
     *
     * @param args
     */
    public static void main(String[] args) {
        TreeNode root = initTreeNode();
        System.out.print("层次遍历：");
        foreachByLayer(root);

        TreeNode root1 = initTreeNode();

        System.out.print("\n 先序遍历（非递归）：");
        foreachByDLR(root1);

        TreeNode root2 = initTreeNode();
        System.out.print("\n 中序遍历（非递归）：");
        foreachByLDR(root2);

        TreeNode root3 = initTreeNode();
        System.out.print("\n 后序遍历（非递归）：");
        foreachByLRD(root3);

        TreeNode root11 = initTreeNode();
        System.out.print("\n 先序遍历（递归）：");
        foreachByDLRWithRecursion(root11);

        TreeNode root22 = initTreeNode();
        System.out.print("\n 中序遍历（递归）：");
        foreachByLDRWithRecursion(root22);

        TreeNode root33 = initTreeNode();
        System.out.print("\n 后序遍历（递归）：");
        foreachByLRDWithRecursion(root33);
    }

    private static TreeNode initTreeNode() {
        TreeNode root = new TreeNode(9);
        TreeNode treeNode1 = new TreeNode(8);
        TreeNode treeNode2 = new TreeNode(7);
        TreeNode treeNode3 = new TreeNode(5);
        TreeNode treeNode4 = new TreeNode(3);
        TreeNode treeNode5 = new TreeNode(2);
        root.setLeftLeaf(treeNode1);
        root.setRightLeaf(treeNode2);
        treeNode1.setRightLeaf(treeNode3);
        treeNode2.setLeftLeaf(treeNode4);
        treeNode2.setRightLeaf(treeNode5);
        return root;
    }

    /**
     * 层次遍历：广度优先、利用队列Queue
     * 核心思想：每次出队一个元素，就将该元素的孩子节点加入队列中，直至队列中元素个数为0时，出队的顺序就是该二叉树的层次遍历结果。
     *
     * @param root
     */
    private static void foreachByLayer(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() > 0) {
            TreeNode treeNode = queue.poll();
            System.out.print(treeNode.data + " ");

            if (null != treeNode.getLeftLeaf()) {
                queue.offer(treeNode.getLeftLeaf());
            }
            if (null != treeNode.getRightLeaf()) {
                queue.offer(treeNode.getRightLeaf());
            }
        }
    }

    /**
     * 先序遍历 D中 L左 D右
     *
     * @param root
     */
    private static void foreachByDLR(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        TreeNode node = root;

        while (node != null || !stack.empty()) {
            while (node != null) {
                System.out.print(node.data + " ");
                stack.push(node);
                node = node.leftLeaf;
            }
            if (!stack.empty()) {
                node = stack.pop();
                node = node.rightLeaf;
            }
        }
    }

    /**
     * 中序遍历
     *
     * @param root
     */
    private static void foreachByLDR(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        TreeNode node = root;
        while (node != null || !stack.empty()) {
            while (node != null) {
                stack.push(node);
                node = node.leftLeaf;
            }
            if (!stack.empty()) {
                node = stack.pop();
                System.out.print(node.data + " ");
                node = node.rightLeaf;
            }
        }
    }

    /**
     * 后续遍历
     *
     * @param root
     */
    private static void foreachByLRD(TreeNode root) {
        Stack<TreeNode> stack1 = new Stack();
        Stack<TreeNode> stack2 = new Stack();
        stack1.push(root);

        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (null != node.leftLeaf) {
                stack1.push(node.leftLeaf);
            }
            if (null != node.rightLeaf) {
                stack1.push(node.rightLeaf);
            }
        }

        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().data + " ");
        }
    }

    /**
     * 先序遍历遍历（递归）
     *
     * @param root
     */
    private static void foreachByDLRWithRecursion(TreeNode root) {
        if (null == root) {
            return;
        }
        System.out.print(root.data + " ");
        foreachByDLRWithRecursion(root.leftLeaf);
        foreachByDLRWithRecursion(root.rightLeaf);
    }

    /**
     * 中序遍历遍历（递归）
     *
     * @param root
     */
    private static void foreachByLDRWithRecursion(TreeNode root) {
        if (null == root) {
            return;
        }
        foreachByLDRWithRecursion(root.leftLeaf);
        System.out.print(root.data + " ");
        foreachByLDRWithRecursion(root.rightLeaf);
    }

    /**
     * 后序遍历（递归）
     *
     * @param root
     */
    private static void foreachByLRDWithRecursion(TreeNode root) {
        if (null == root) {
            return;
        }
        foreachByLRDWithRecursion(root.leftLeaf);
        foreachByLRDWithRecursion(root.rightLeaf);
        System.out.print(root.data + " ");
    }

}
