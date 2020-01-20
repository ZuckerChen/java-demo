package datastructure.tree;

/**
 * Description:
 *
 * @author zucker
 * @version 1.0 2018/5/30 下午4:42
 */
public class RedBlackTree {
    final private static String red = "RED";
    final private static String black = "BLACK";

    public static class Node<T> {
        private T data;
        private String color;

        private Node<T> lchild;
        private Node<T> rchild;

        private boolean leaf = false;

        public Node(T data, String color) {
            this.data = data;
            this.color = color;
        }

        public T getData() {
            return data;
        }

        public String getColor() {
            return color;
        }

        public Node<T> getLchild() {
            return lchild;
        }

        public Node<T> getRchild() {
            return rchild;
        }

        public boolean isRed() {
            return red.equalsIgnoreCase(color);
        }

        public boolean isBlack() {
            return black.equalsIgnoreCase(color);
        }
    }

    private Node<String> root;
    private int nodeNum;

    public void setRoot(String data) {
        root = new Node(data, black);
        nodeNum++;
    }

    public void addLeftChild(Node currentNode, String data) {
        currentNode.lchild = new Node(data, currentNode.isRed() ? black : red);
        nodeNum++;
    }

    public void addRightChild(Node currentNode, String data) {
        currentNode.rchild = new Node(data, currentNode.isRed() ? black : red);
        nodeNum++;
    }


    public void insert(String data){
        //TODO

        insert_fix();
    }

    /**
     * 旋转，着色
     */
    public void insert_fix(){

    }


}
