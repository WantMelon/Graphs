package task2.kruskal;

import java.util.Arrays;

public class Main {
    /**
     * При соеденинении ребра,
     * добавляем все вершины из двух компонет связности вершин ребра
     * в одну компоненту связности.
     */
    public static void changeConnectivity(int[] connectivity, int x, int y) {
        int from = connectivity[x];
        int to = connectivity[y];
        for (int i = 0; i < connectivity.length; ++i) {
            if (connectivity[i] == from) {
                connectivity[i] = to;
            }
        }
    }

    /**
     * Алгоритм поиска минимального оставного дерева.
     * Ищет ребро минимального веса, если оно не образует цикл,
     * т.е вершины в разных компонентах связности,
     * то добавляет ребро в итоговое дерево.
     */
    public static int[][] kruskalAlgorithm(int[][] matrix) {
        int[][] tree = new int[matrix.length][matrix.length];
        int[] connectivity = new int[matrix.length];
        for (int i = 0; i < matrix.length; ++i) {
            connectivity[i] = i;
        }

        for (int k = 0; k < matrix.length - 1; ++k) {
            int minEdgeWeight = Integer.MAX_VALUE;
            int[] minEdge = new int[2];
            for (int i = 0; i < matrix.length; ++i) {
                for (int j = 0; j < matrix.length; ++j) {
                    if ((0 < matrix[i][j])
                            && (matrix[i][j] < minEdgeWeight)
                            && (connectivity[i] != connectivity[j])) {
                        minEdge[0] = i;
                        minEdge[1] = j;
                        minEdgeWeight = matrix[i][j];
                    }
                }
            }
            changeConnectivity(connectivity, minEdge[0], minEdge[1]);

            tree[minEdge[0]][minEdge[1]] = tree[minEdge[1]][minEdge[0]]
                    = minEdgeWeight;
        }
        return tree;
    }

    public static void main(String[] args) {
        int[][] matrix = task1.Main.inputMatrix("src/task2/input(lecture).txt");
        int[][] tree = kruskalAlgorithm(matrix);

        for (int[] row : tree) {
            System.out.println(Arrays.toString(row));
        }
    }
}
