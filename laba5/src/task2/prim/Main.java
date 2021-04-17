package task2.prim;

import java.util.Arrays;

public class Main {
    /**
     * Ищем минимальное ребро e(i, j) среди обведенных строк и
     * не зачеркнутых столбцов. После обводим строки i, j и
     * зачеркиваем столбцы i, j. Повторяем n-1 раз.
     */
    public static int[][] primAlgorithm(int[][] matrix) {
        int[][] tree = new int[matrix.length][matrix.length];
        boolean[] availableRows = new boolean[matrix.length];
        boolean[] availableColumns = new boolean[matrix.length];
        Arrays.fill(availableColumns, true);

        int[] minEdge = new int[2];
        int minEdgeWeight = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                if (matrix[i][j] < minEdgeWeight
                        && matrix[i][j] > 0) {
                    minEdge[0] = i;
                    minEdge[1] = j;
                    minEdgeWeight = matrix[i][j];
                }
            }
        }
        availableRows[minEdge[0]] = availableRows[minEdge[1]] = true;
        availableColumns[minEdge[0]] = availableColumns[minEdge[1]] = false;
        tree[minEdge[0]][minEdge[1]]
                = tree[minEdge[1]][minEdge[0]]
                = minEdgeWeight;

        for (int k = 0; k < matrix.length - 2; ++k) {
            minEdgeWeight = Integer.MAX_VALUE;
            for (int i = 0; i < matrix.length; ++i) {
                for (int j = 0; j < matrix.length; ++j) {
                    if (matrix[i][j] < minEdgeWeight
                            && matrix[i][j] > 0
                            && availableRows[i]
                            && availableColumns[j]) {
                        minEdgeWeight = matrix[i][j];
                        minEdge[0] = i;
                        minEdge[1] = j;
                    }
                }
            }
            availableRows[minEdge[0]] = availableRows[minEdge[1]] = true;
            availableColumns[minEdge[0]] = availableColumns[minEdge[1]] = false;
            tree[minEdge[0]][minEdge[1]]
                    = tree[minEdge[1]][minEdge[0]]
                    = minEdgeWeight;
        }

        return tree;
    }

    public static void main(String[] args) {
        int[][] matrix = task1.Main.inputMatrix("src/task2/input(lecture).txt");
        int[][] tree = primAlgorithm(matrix);

        for (int[] rows : tree) {
            System.out.println(Arrays.toString(rows));
        }
    }
}
