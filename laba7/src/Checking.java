import java.util.*;

public class Checking {
    private Checking() {}

    public static boolean isBipartition(int[][] matrix) {
        int[] flags = bfs(matrix);
        List<Integer> evenVertexes = new ArrayList<>();
        List<Integer> oddVertexes = new ArrayList<>();

        for (int i = 0; i < flags.length; i++) {
            if (flags[i] % 2 == 0) {
                evenVertexes.add(i);
            } else {
                oddVertexes.add(i);
            }
        }

        for (Integer evenVertex1 : evenVertexes) {
            for (Integer evenVertex2 : evenVertexes) {
                if (matrix[evenVertex1][evenVertex2] > 0) {
                    return false;
                }
            }
        }

        for (Integer oddVertex1 : oddVertexes) {
            for (Integer oddVertex2 : oddVertexes) {
                if (matrix[oddVertex1][oddVertex2] > 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int[] bfs(int[][] m) {
        int v = 0;
        //Breadth-First search
        int[] flags = new int[m.length];
        Arrays.fill(flags, -1);
        flags[v] = 0;
        //Массив с метками. i = вершина, flags[i] = метка.
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < flags.length; ++j) {
                //Идем по массиву flags
                if (flags[j] == i) {
                    //Находим крайнюю метку, номер которой равен i
                    for (int k = 0; k < m.length; ++k) {
                        //Для всех смежных ей вершин, с не проставленными метками, ставим метку i+1
                        if (m[j][k] == 1 && flags[k] == -1) flags[k] = i + 1;
                    }
                }
            }
        }
        return flags;
    }

}
