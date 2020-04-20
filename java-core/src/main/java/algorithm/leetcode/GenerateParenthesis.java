package algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author zucker
 * @description
 * @date: 2020/4/9 10:11 AM
 */
public class GenerateParenthesis {

    public static void main(String[] args) {
        System.out.println((0+2)/2);

        GenerateParenthesis generateParenthesis = new GenerateParenthesis();
        System.out.println("广度优先搜索");
        generateParenthesis.bfs();
        System.out.println("深度优先搜索");
        generateParenthesis.dfs();
    }


    private void dfs() {
        int n = 3;
        List<String> res = new ArrayList<>();
        dfs(n, n, "", res);
        res.forEach(System.out::println);
    }

    private void dfs(int left, int right, String curStr, List<String> res) {
        if (left == 0 && right == 0) {
            res.add(curStr);
            return;
        }

        if (left > 0) {
            dfs(left - 1, right, curStr + "(", res);
        }

        if (right > left) {
            dfs(left, right - 1, curStr + ")", res);
        }
    }


    private void bfs() {
        int n = 3;
        List<String> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node("", n, n));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left == 0 && node.right == 0) {
                res.add(node.res);
                continue;
            }

            if (node.left > 0) {
                queue.offer(new Node(node.res + "(", node.left - 1, node.right));
            }
            if (node.left < node.right) {
                queue.offer(new Node(node.res + ")", node.left, node.right - 1));
            }
        }
        res.forEach(System.out::println);
    }


    class Node {
        String res;
        int left;
        int right;

        public Node(String res, int left, int right) {
            this.res = res;
            this.left = left;
            this.right = right;
        }
    }

}
