package algorithm.leetcode;

/**
 * @author zucker
 * @description 旋转矩阵
 * @date: 2020/4/7 2:14 PM
 */
public class MatrixRotate {
    /**
     * 水平翻转+对角线翻转
     * 时间复杂度O(n²)
     * 空间复杂度O(1)
     */
    public static void rotate(int[][] matrix) {
        int length = matrix.length;

        //水平
        for (int i = 0; i < length / 2; i++) {
            for (int j = 0; j < length; j++) {
                int tmp;
                tmp = matrix[i][j];
                matrix[i][j] = matrix[length - i - 1][j];
                matrix[length - i - 1][j] = tmp;
            }
        }
        //对角线
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                int tmp;
                tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

    }

    /**
     * 对于矩阵中第 i 行的第 j 个元素，在旋转后，它出现在倒数第 i 列的第 j 个位置。
     * 时间复杂度O(n²)
     * 空间复杂度O(n²)
     */
    private static void assistArr(int[][] matrix) {
        int[][] matrix_new = new int[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix_new.length; j++) {
                matrix_new[j][matrix.length - i - 1] = matrix[i][j];
            }
        }
        matrix = matrix_new;
    }

    private static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};

        print(matrix);
        rotate(matrix);
        print(matrix);
    }
}
