package task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    public static int[][] inputMatrix() {
        int[][] matrix = null;
        File file = new File("src/task1/input.txt");
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
     * Используем алгоритм посика в ширину для определения связности.
     * isChecked - массив с вершинами, в которых уже проставленны метки.
     * Если не во всех вершинах есть метки - граф не связный.
     */
    private static boolean connectivityCheck(int[][] matrix, int startVertex) {
        boolean isConnectivity = true;
        boolean[] isChecked = new boolean[matrix.length];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.push(startVertex);
        isChecked[startVertex] = true;

        while (!queue.isEmpty()) {
            int v = queue.pop();
            for (int i = 0; i < matrix.length; ++i) {
                if (isChecked[i] && matrix[v][i] > 0) {
                    isChecked[i] = true;
                    queue.push(matrix[v][i]);
                }
            }
        }

        for (int i = 0; i < matrix.length; ++i) {
            if (isChecked[i] = false) {
                isConnectivity = false;
                break;
            }
        }
        return isConnectivity;
    }

    private static int edgeNumber(int[][] matrix) {
        int edgeNumber = 0;
        for (int[] row : matrix) {
            for (int el : row) {
                if (el > 0) edgeNumber++;
            }
        }
        return edgeNumber / 2;
    }

    /**
     * Если граф является свзным и не имеет циклов,
     * т.е количество ребер равно количеству вершин - 1,
     * то граф - дерево.
     */
    private static boolean isTree(boolean isConnectivity, int[][] matrix) {
        return isConnectivity && (edgeNumber(matrix) == matrix.length - 1);
    }

    public static void main(String[] args) {
        int[][] matrix = inputMatrix();
        boolean isConnectivity = connectivityCheck(matrix, 0);
        boolean isTree = isTree(isConnectivity, matrix);

        if (isTree) {
            System.out.println("Graph is a tree");
        } else {
            System.out.println("Graph is NOT tree");
        }
    }
}
