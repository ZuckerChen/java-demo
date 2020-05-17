package algorithm.leetcode.dp;

import java.util.Arrays;
import java.util.List;

/**
 * @author zucker
 * @description
 * @date: 2020/5/14 10:31 AM
 */
public class MinimumTotal {
    public static int minimumTotal(List<List<Integer>> triangle) {
        //dp[i][j] 第i，j个数的最小路径和   子底向上推算
        //dp[i][j] = dp[i][j]+Math.max(dp[i+1][j],dp[i+1][j+1])

        int row = triangle.size();
        int col = triangle.get(row-1).size();

        int dp[][] = new int[row][col];
        List<Integer> endcol = triangle.get(row-1);
        for(int i = 0;i<col;i++){
            dp[row-1][i] = endcol.get(i);
        }

        for(int i = row-2;i>=0;i--){
            for(int j = 0;j<triangle.get(i).size();j++){
                dp[i][j] = triangle.get(i).get(j) + Math.min(dp[i+1][j],dp[i+1][j+1]);
            }

        }

        return dp[0][0];

    }

    public static void main(String[] args) {
        List<Integer> col1 = Arrays.asList(2);
        List<Integer> col2 = Arrays.asList(3,4);
        List<Integer> col3 = Arrays.asList(6,5,7);
        List<Integer> col4 = Arrays.asList(4,1,8,3);
        List<List<Integer>> array = Arrays.asList(col1,col2,col3,col4);
        System.out.println(minimumTotal(array));
    }
}
