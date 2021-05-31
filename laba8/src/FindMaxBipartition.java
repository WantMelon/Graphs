import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindMaxBipartition {
    private FindMaxBipartition() {
    }

    /**
     * Возвращает вершины в которым были метки
     */
    private static List<Integer> getVertexesWithFlags(int[][] matrix, List<Integer> U) {
        List<Integer> XY = new ArrayList<>();
        boolean[] isChecked = new boolean[matrix.length];
        for (Integer startVertex : U) {
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            queue.push(startVertex);
            isChecked[startVertex] = true;

            while (!queue.isEmpty()) {
                int v = queue.pop();
                for (int i = 0; i < matrix.length; ++i) {
                    //int to = matrix[v][i];
                    if (!isChecked[i] && matrix[v][i] > 0) {
                        isChecked[i] = true;
                        queue.push(i);
                    }
                }
            }
        }

        for (int i = 0; i < isChecked.length; i++) {
            if (isChecked[i]) {
                XY.add(i);
            }
        }
        return XY;
    }

    private static List<Integer> bfs(int[][] matrix, int startVertex, List<Integer> V) {
        List<Integer> flags = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            flags.add(null);
        }
        boolean[] isChecked = new boolean[matrix.length];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.push(startVertex);
        isChecked[startVertex] = true;

        while (!queue.isEmpty()) {
            int v = queue.pop();
            for (int i = 0; i < matrix.length; ++i) {
                //int to = matrix[v][i];
                if (!isChecked[i] && matrix[v][i] > 0) {
                    isChecked[i] = true;
                    queue.push(i);
                    flags.set(i, v);
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (Integer finishVertex : V) {
            if (isChecked[finishVertex]) {
                for (int v = finishVertex; v != startVertex; v = flags.get(v)) {
                    path.add(v);
                }
                path.add(startVertex);
                Collections.reverse(path);
                return path;
            }
        }
        return path;
    }

    /**
     * Ищет путь из множества U в V
     *
     * @param U V1\Xp
     * @param V V2\Yp
     * @return путь
     */
    private static List<Integer> getPath(int[][] matrix, List<Integer> U, List<Integer> V) {
        List<Integer> path = new ArrayList<>();

        for (Integer startVertex : U) {
            path = bfs(matrix, startVertex, V);
            if (!path.isEmpty()) {
                return path;
            }
        }

        return path;
    }

    private static boolean isEnd(List<List<Integer>> bipartition, List<Integer> V1, List<Integer> V2) {
        int v1Size = V1.size();
        int v2Size = V2.size();
        for (Integer x : bipartition.get(0)) {
            if (V1.contains(x)) {
                v1Size--;
            }
        }
        for (Integer y : bipartition.get(1)) {
            if (V2.contains(y)) {
                v2Size--;
            }
        }
        if (v1Size == 0 && v2Size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true при V1 \ Xp = V2 \ Yp = 0, false иначе
     */
    public static List<Integer> findMaxBipartition(int[][] matrix, List<List<Integer>> bipartition) {
        // bipartition[0] = Xp
        // bipartition[1] = Yp

        List<Integer> V1 = new ArrayList<>();
        List<Integer> V2 = new ArrayList<>();
        for (int i = 0; i < matrix.length / 2; i++) {
            V1.add(i);
            V2.add(i + matrix.length / 2);
        }

        for (Integer v2 : V2) {
            if (matrix[V1.get(0)][v2] > 0) {
                bipartition.get(0).add(V1.get(0));
                bipartition.get(1).add(v2);
                break;
            }
        }

        if (isEnd(bipartition, V1, V2)) {
            return new ArrayList<>();
        }

        // Делаем орграф
        for (Integer v1 : V1) {
            for (Integer v2 : V2) {
                // Если врешины смежны
                if (matrix[v1][v2] > 0) {
                    // Если ребро содержится в паросочетании, делаем дугу (v2,v1).
                    // Иначе (v1,v2)
                    if (bipartition.get(0).contains(v1) && bipartition.get(1).contains(v2)) {
                        matrix[v1][v2] = 0;
                    } else {
                        matrix[v2][v1] = 0;
                    }
                }
            }
        }

        while (true) {
            List<Integer> U = new ArrayList<>();
            List<Integer> V = new ArrayList<>();
            for (Integer v1 : V1) {
                if (!bipartition.get(0).contains(v1)) {
                    U.add(v1);
                }
            }
            for (Integer v2 : V2) {
                if (!bipartition.get(1).contains(v2)) {
                    V.add(v2);
                }
            }

            List<Integer> path = getPath(matrix, U, V);
            if (path.isEmpty()) {
                return getVertexesWithFlags(matrix, U);
            }
            bipartition.get(0).add(path.get(0));
            bipartition.get(1).add(path.get(path.size() - 1));
            for (int i = 0; i < path.size() - 1; i += 2) {
                matrix[path.get(i)][path.get(i + 1)] = 0;
                matrix[path.get(i + 1)][path.get(i)] = 1;
            }
            for (int i = 1; i < path.size() - 2; i += 2) {
                matrix[path.get(i)][path.get(i + 1)] = 0;
                matrix[path.get(i + 1)][path.get(i)] = 1;
            }

            if (isEnd(bipartition, V1, V2)) {
                return new ArrayList<>();
            }
        }
    }
}
