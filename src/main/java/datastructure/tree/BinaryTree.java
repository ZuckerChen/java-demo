package datastructure.tree;

import java.util.LinkedList;

/**
 * Description:
 *
 * @version 1.0 2018/5/30 上午11:41 by zucker
 */
public class BinaryTree<Stirng> {
    public static class Node<T>{
        private T data;
        private Node<T> lchild;
        private Node<T> rchild;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public Node<T> getLchild() {
            return lchild;
        }

        public Node<T> getRchild() {
            return rchild;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", lchild=" + lchild +
                    ", rchild=" + rchild +
                    '}';
        }
    }

    private Node<String> root;
    private int nodeNum;

    public void setRoot(String data) {
        root = new Node<>(data);
        nodeNum++;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public void addRightChild(String data,Node<String> parent){
        parent.rchild=new Node<>(data);
        nodeNum++;
    }

    public void addLeftChild(String data,Node<String> parent){
        parent.lchild=new Node<>(data);
        nodeNum++;
    }

    /**
     * 前序遍历--递归
     * @param node
     */
    public void preOrder(Node node){
        if(null == node){
            return;
        }
        System.out.print(node.getData()+" ");
        preOrder(node.getLchild());
        preOrder(node.getRchild());
    }

    /**
     * 前序遍历--非递归
     */
    public void preOrder2(Node<String> root) {
        // 用栈保存已经访问过的结点，便于返回到父结点
        LinkedList<Node<String>> stack = new LinkedList<>();
        // 当前结点不为空，或者为空但有可以返回的父结点（可以进行pop操作）都可以进入循环
        while (root != null || !stack.isEmpty()) {
            // 只要当前结点，就打印，同时入栈
            while (root != null) {
                stack.push(root);
                System.out.print(root.getData()+" ");
                root = root.lchild;
            }
            // 上面while终止说明当前结点为空；返回到父结点并处理它的右子树。由于root和stack总有一个不为空，因此在循环里不会有stack为空
            // 返回到父结点。由于左孩子为空返回时已经弹出过父结点了，所以若是由于右孩子为空返回，会一次性返回多层
            root = stack.pop();
            // 开始右子树的大循环（第一个while)
            root = root.rchild;
        }
    }

    /**
     * 中序遍历-递归
     * @param node
     */
    public void midOrder(Node node){
        if(null == node){
            return;
        }
        midOrder(node.getLchild());
        System.out.print(node.getData()+" ");
        midOrder(node.getRchild());
    }

    /**
     * 中序遍历--非递归
     */
    public void midOrder2(Node<String> root) {
        LinkedList<Node<String>> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.lchild;
            }

            // 和前序遍历唯一不同的是，前序遍历是入栈时打印，中序遍历是出栈时返回到父结点才打印
            // 和前序遍历一样，由于左孩子为空返回时已经弹出过父结点了，所以若是由于右孩子为空返回，会一次性返回多层
            root = stack.pop();
            System.out.print(root.getData()+" ");
            root = root.rchild;
        }
    }


    /**
     * 后序遍历--递归
     * @param node
     */
    public void postOrder(Node node){
        if(null == node){
            return;
        }
        postOrder(node.getLchild());
        postOrder(node.getRchild());
        System.out.print(node.getData()+" ");
    }

    /**
     * 后序遍历--非递归
     */
    public void postOrder2(Node<String> root) {
        LinkedList<Node<String>> stack = new LinkedList<>();
        // 存放结点被访问的信息，1表示只访问过左孩子，2表示右孩子也访问过了（此时可以打印了）
        LinkedList<Integer> visitedState = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.lchild;
                // 上句访问过左孩子了，放入1
                visitedState.push(1);
            }
            // 这个while和下面的if不可交换执行顺序，否则变成了中序遍历
            // 用while而不是if是因为：结点已经访问过它的两个孩子了，先不打印而处于等待状态。随即判断若它的右孩子不为空，则仍会被push进去，待右孩子处理完后按照递归思想应该返回到等待中父结点，由于父结点访问状态已经是2，直接打印
            while (!stack.isEmpty() && visitedState.peek() == 2) {
                visitedState.pop();
                // 这里不能root = stack.pop()然后在打印root，因为如果这样的话，最后一个元素弹出赋值给root，而这个root不为空，一直while循环不会跳出
                System.out.print(stack.pop().getData()+" ");
            }

            if (!stack.isEmpty()) {
                // 注意先取出来而不删除，等到访问状态为2才能删除
                root = stack.peek();
                root = root.rchild;
                // 上句访问过右孩子了，应该更新访问状态到2
                visitedState.pop(); // 弹出1，压入2
                visitedState.push(2);
            }
        }
    }


    public Node<String> root(){
        return root;
    }

    public static void main(String[] args) {
        BinaryTree<String> tree = new BinaryTree<>();
        tree.setRoot("A");
        Node<String> root = tree.root();
        tree.addLeftChild("B", root);
        tree.addRightChild("C", root);
        tree.addLeftChild("D", root.getLchild());

        tree.addLeftChild("E", root.getRchild());
        tree.addRightChild("F", root.getRchild());
        tree.addLeftChild("G", root.getLchild().getLchild());
        tree.addRightChild("H", root.getLchild().getLchild());
        tree.addRightChild("I", root.getRchild().getLchild());

        System.out.println("前序遍历：");
        tree.preOrder(root);
        System.out.println("\n中序遍历：");
        tree.midOrder(root);
        System.out.println("\n后序遍历：");
        tree.postOrder(root);

    }

}
