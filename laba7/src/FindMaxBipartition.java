import java.util.*;

public class FindMaxBipartition {
    private FindMaxBipartition() {
    }

    private static List<Integer> bfs(int[][] matrix, int startVertex, List<Integer> V) {
        List<Integer> p = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            p.add(null);
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
                    p.set(i, v);
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (Integer finishVertex : V) {
            if (isChecked[finishVertex]) {
                for (int v = finishVertex; v != startVertex; v = p.get(v)) {
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

    public static List<List<Integer>> findMaxBipartition(int[][] matrix) {
        List<List<Integer>> bipartition = new ArrayList<>();
        bipartition.add(new ArrayList<>());
        bipartition.add(new ArrayList<>());
        // bipartition[0] = Xp
        // bipartition[1] = Yp

        List<Integer> V1 = new ArrayList<>();
        List<Integer> V2 = new ArrayList<>();
        Checking.isBipartition(matrix, V1, V2);

        for (Integer v2 : V2) {
            if (matrix[V1.get(0)][v2] > 0) {
                bipartition.get(0).add(V1.get(0));
                bipartition.get(1).add(v2);
                break;
            }
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
                break;
            }
            bipartition.get(0).add(path.get(0));
            bipartition.get(1).add(path.get(path.size() - 1));
            for (int i = 0; i < path.size() - 1; ++i) {
                matrix[path.get(i)][path.get(i + 1)] = 1;
                matrix[path.get(i + 1)][path.get(i)] = 0;
            }
            for (int i = 0; i < path.size() - 2; ++i) {
                matrix[path.get(i + 1)][path.get(i)] = 0;
            }
        }

        return bipartition;
    }
}
