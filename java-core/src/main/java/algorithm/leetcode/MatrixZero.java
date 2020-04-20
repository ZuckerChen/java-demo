package algorithm.leetcode;

/**
 * @author zucker
 * @description MxN矩阵某个元素为0则所在行与列清零
 * @date: 2020/4/7 3:17 PM
 */
public class MatrixZero {
    private static void print(int[][] matrix) {
        final int row_length = matrix.length;
        final int column_length = matrix[0].length;
        for (int i = 0; i < row_length; i++) {
            for (int j = 0; j < column_length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0, 0, 0, 5}, {4, 3, 7, 8}, {0, 10, 11, 12}, {4, 3, 11, 12}, {0, 0, 11, 12}};
        print(matrix);
        final int row_length = matrix.length;
        final int column_length = matrix[0].length;

        boolean row = false;
        boolean column = false;

        for (int i = 0; i < row_length; i++) {
            for (int j = 0; j < column_length; j++) {
                if (matrix[i][j] == 0) {
                    if (i == 0) {
                        column = true;
                    }
                    if (j == 0) {
                        row = true;
                    }

                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        print(matrix);

        for (int i = 1; i < row_length; i++) {
            for (int j = 1; j < column_length; j++) {
                if (matrix[0][j] == 0 || matrix[i][0] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (row) {
            for (int i = 0; i < row_length; i++) {
                matrix[i][0] = 0;
            }
        }

        if (column) {
            for (int i = 0; i < column_length; i++) {
                matrix[0][i] = 0;
            }
        }

        print(matrix);
    }


}
