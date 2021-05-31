import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hungarian = Венгрия
 */
public class HungarianAlgorithm {
    private static void prepare(int[][] matrix) {
        // Отнимаем от каждой строки min элемент
        for (int i = 0; i < matrix.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] < min && matrix[i][j] > -1) {
                    min = matrix[i][j];
                }
            }
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] -= min;
            }
        }
        // Отнимаем от каждого столбцв min элемент
        for (int i = 0; i < matrix.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] < min && matrix[j][i] > -1) {
                    min = matrix[j][i];
                }
            }
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] -= min;
            }
        }
    }

    /** G = (V1 + V2, E0)
     * Также приводим матрицу к классическому виду
     */
    private static int[][] makeGraph(int[][] matrix) {
        int[][] g = new int[matrix.length * 2][matrix.length * 2];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 0) {
                    g[i][j + matrix.length] = 1;
                    g[j + matrix.length][i] = 1;
                }
            }
        }

        return g;
    }

    private static int findVertexWithMinWeight(int[][] matrix, List<Integer> vertexesWithFlags) {
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        for (Integer v : vertexesWithFlags) {
            if (v >= matrix.length) {
                Y.add(v - matrix.length);
            } else {
                X.add(v);
            }
        }
        List<Integer> Y2 = new ArrayList<>();   // Y2 = V2 \ Y
        List<Integer> V2 = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            V2.add(i);
        }
        for (Integer v2 : V2) {
            if (!Y.contains(v2)) {
                Y2.add(v2);
            }
        }

        int min = Integer.MAX_VALUE;
        for (Integer x : X) {
            for (Integer y2 : Y2) {
                if (matrix[x][y2] < min && matrix[x][y2] > 0) {
                    min = matrix[x][y2];
                }
            }
        }
        return min;
    }

    private static void step7(int[][] matrix, List<Integer> vertexesWithFlags, int min) {
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        for (Integer v : vertexesWithFlags) {
            if (v >= matrix.length) {
                Y.add(v - matrix.length);
            } else {
                X.add(v);
            }
        }

        for (Integer y : Y) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][y] > -1) {
                    matrix[i][y] += min;
                }
            }
        }

        for (Integer x : X) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[x][i] > -1) {
                    if (matrix[x][i] < min) {
                        matrix[x][i] = 0;
                    } else {
                        matrix[x][i] -= min;
                    }
                }
            }
        }

    }

    private static void step8(int[][] oldMatrix, int[][] matrix, int[][] newGraph, List<Integer> vertexesWithFlags) {
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        for (Integer v : vertexesWithFlags) {
            if (v >= oldMatrix.length) {
                Y.add(v - oldMatrix.length);
            } else {
                X.add(v);
            }
        }

        List<Integer> X2 = new ArrayList<>();
        List<Integer> Y2 = new ArrayList<>();
        // Y2 = V2 \ Y
        // X2 = V1 \ X
        List<Integer> V2 = new ArrayList<>();
        List<Integer> V1 = new ArrayList<>();
        for (int i = 0; i < oldMatrix.length; i++) {
            V2.add(i);
            V1.add(i);
        }
        for (Integer v2 : V2) {
            if (!Y.contains(v2)) {
                Y2.add(v2);
            }
        }
        for (Integer v1 : V1) {
            if (!X.contains(v1)) {
                X2.add(v1);
            }
        }

        for (Integer x : X) {
            for (Integer y2 : Y2) {
                if (oldMatrix[x][y2] != 0 && matrix[x][y2] == 0) {
                    newGraph[x][y2 + oldMatrix.length] = 1;
                }
            }
        }
        for (Integer x2 : X2) {
            for (Integer y : Y) {
                if (matrix[x2][y] > oldMatrix[x2][y]) {
                    newGraph[x2][y + oldMatrix.length] = 0;
                }
            }
        }
    }

    private static int[][] copy(int[][] matrix) {
        int[][] oldMatrix = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                oldMatrix[i][j] = matrix[i][j];
            }
        }
        return oldMatrix;
    }

    private static void printStatus(int[][] newGraph, int[][] matrix) {
        int weight = 0;
        for (int i = newGraph.length / 2; i < newGraph.length; i++) {
            for (int j = 0; j < newGraph.length / 2; j++) {
                if (newGraph[i][j] > 0) {
                    System.out.println((j + 1) + " - " + (i + 1 - matrix.length));
                    weight += matrix[j][i - newGraph.length / 2];
                }
            }
        }
        System.out.println("Weight: " + weight);
    }

    public static List<List<Integer>> hungarianAlgorithm(int[][] matrix) {
        int[][] notUseMatrix = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                notUseMatrix[i][j] = matrix[i][j];
            }
        }

        prepare(matrix);

//        List<List<Integer>> result = new ArrayList<>();
//        result.add(new ArrayList<>());
//        result.add(new ArrayList<>());
        // result[0] = Xp
        // result[1] = Yp

        int[][] newGraph = makeGraph(matrix);

        List<List<Integer>> bipartition = new ArrayList<>();
        bipartition.add(new ArrayList<>());
        bipartition.add(new ArrayList<>());

        while (true) {

            int[][] oldMatrix = copy(matrix);

            List<Integer> vertexesWithFlags = FindMaxBipartition.findMaxBipartition(newGraph, bipartition);
            if (vertexesWithFlags.isEmpty()) {
                break;
            }
            int min = findVertexWithMinWeight(matrix, vertexesWithFlags);
            step7(matrix, vertexesWithFlags, min);
            step8(oldMatrix, matrix, newGraph, vertexesWithFlags);
        }

        printStatus(newGraph, notUseMatrix);
        return bipartition;
    }
}
