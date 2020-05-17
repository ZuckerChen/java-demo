package algorithm.leetcode.dp;

/**
 * @author zucker
 * @description
 * @date: 2020/5/14 11:29 AM
 */
public class MaximalSquare {
    public static int maximalSquare(char[][] matrix) {
        //dp[i][j] matrix[i][j]的最大边长
        //dp[i][j] = min(dp[i-1][j-1],dp[i-1][j],dp[i][j-1])+1;

        if(matrix.length==0||matrix[0].length==0){
            return 0;
        }

        int row = matrix.length;
        int col = matrix[0].length;
        int dp[][] = new int[row][col];

        int maxans = 0;

        for(int i =0;i<row;i++){
            for(int j = 0;j<col;j++){
                if(matrix[i][j]=='1'){
                    if(i==0 || j==0){
                        dp[i][j]=1;
                    }else{
                        dp[i][j] = Math.min(Math.min(dp[i-1][j-1],dp[i-1][j]),dp[i][j-1])+1;
                    }
                }
                maxans = Math.max(maxans,dp[i][j]);
            }
        }
        return maxans*maxans;
    }

    public static void main(String[] args) {
    char array[][] = {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
        System.out.println(maximalSquare(array));
    }
}
