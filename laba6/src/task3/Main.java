package task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int getNextVertex(int[][] matrix, boolean[] used, int v) {
        for (int i = 0; i < matrix.length; ++i) {
            if (!used[i] && matrix[v][i] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Используем поиск в глубину для составления бинарного кода.
     *
     * @param matrix Исходная матрицы
     * @param used   Массив вершин, в которых мы уже побывали
     * @param cur    Текущая вершина
     */
    public static void getBinaryCode(List<Integer> binaryCode,
                                     int[][] matrix, boolean[] used,
                                     int cur) {
        used[cur] = true;
        int next = getNextVertex(matrix, used, cur);

        while (next != -1) {
            binaryCode.add(1);
            getBinaryCode(binaryCode, matrix, used, next);
            binaryCode.add(0);
            next = getNextVertex(matrix, used, cur);
        }

    }

    public static void main(String[] args) {
        int[][] matrix = task1.Main.inputMatrix("src/task3/input.txt");
        System.out.print("Root: ");
        int root = new Scanner(System.in).nextInt();
        boolean[] used = new boolean[matrix.length];
        List<Integer> binaryCode = new ArrayList<>();
        getBinaryCode(binaryCode, matrix, used, root);

        System.out.println(binaryCode);
    }
}
