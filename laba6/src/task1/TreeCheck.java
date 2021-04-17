package task1;

import java.util.ArrayDeque;

public class TreeCheck {
    private TreeCheck() {}

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
    public static boolean isTree(int[][] matrix) {
        boolean isConnectivity = connectivityCheck(matrix, 0);
        return isConnectivity && (edgeNumber(matrix) == matrix.length - 1);
    }
}
