package task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static int[][] inputMatrix(String filename) {
        int[][] matrix = null;
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            int N = sc.nextInt();
            int M = sc.nextInt();

            matrix = new int[N][M];
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < M; ++j) {
                    matrix[i][j] = sc.nextInt();
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    /**
     * Возвращаем степень заданной вершины.
     */
    public static int getDegree(int[][] matrix, int v) {
        int degree = 0;
        for (int[] row : matrix) {
            degree += row[v];
        }
        return degree;
    }

    /**
     * Возвращает вторую вершину, соедененную с вершиной степени 1.
     */
    public static int getVertex(int[][] matrix, int a) {
        int b = 0;
        for (int i = 0; i < matrix.length && b == 0; ++i) {
            if (matrix[a][i] != 0) {
                b = i;
            }
        }
        return b;
    }

    public static int[] getPruferCode(int[][] matrix) {
        int[] pruferCode = new int[matrix.length - 2];
        for (int i = 0; i < pruferCode.length; ++i) {
            for (int a = 0; a < matrix.length; ++a) {
                if (getDegree(matrix, a) == 1) {
                    int b = getVertex(matrix, a);
                    matrix[a][b] = matrix[b][a] = 0;
                    pruferCode[i] = b;
                    break;
                }
            }
        }
        return pruferCode;
    }

    public static void main(String[] args) throws Exception {
        int[][] matrix = inputMatrix("src/task1/input.txt");
        if (!TreeCheck.isTree(matrix)) {
            System.err.println("Graph is not tree");
            throw new Exception();
        }
        int[] pruferCode = getPruferCode(matrix);
        System.out.println(Arrays.toString(pruferCode));
    }
}
