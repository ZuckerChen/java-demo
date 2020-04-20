package algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zucker
 * @description 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，
 * * 它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。
 * * 例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。
 * * 请问该机器人能够到达多少个格子？
 * @date: 2020/4/8 3:04 PM
 */
public class RobotSportRange {

    public static void main(String[] args) {
        RobotSportRange robot = new RobotSportRange();
        int i = robot.movingCount_DFS(10, 20, 18);
        System.out.println(i);
    }

    /**
     * 深度优先搜索
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    int movingCount_DFS(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        return dfs(0, 0, m, n, k, visited);
    }

    int dfs(int i, int j, int m, int n, int k, boolean[][] visited) {
        if (i < 0 || i >= m || j < 0 || j >= n || k < getSum(i) + getSum(j) || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        return 1 + dfs(i + 1, j, m, n, k, visited) + dfs(i, j + 1, m, n, k, visited);
    }

    int getSum(int x) {
        int res = 0;
        while (x > 0) {
            res += x % 10;
            x /= 10;
        }
        return res;
    }


    /**
     * 广度优先搜索
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    int movingCount_BFS(int m, int n, int k) {
        //存已经扫描过的坐标
        boolean[][] visited = new boolean[m][n];
        //BFS一般用队列保存待搜索目标
        Queue<Point> queue = new LinkedList();

        int res = 0;

        queue.offer(new Point(0, 0));

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int x = point.x;
            int y = point.y;

            if (x >= 0 && x < m && y >= 0 && y < n && getSum(x) + getSum(y) <= k && !visited[x][y]) {
                res ++;
                visited[x][y] = true;
                queue.offer(new Point(x + 1, y));
                queue.offer(new Point(x, y + 1));
            }
        }


        return res;
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}
