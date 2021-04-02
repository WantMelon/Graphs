import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static int[][] inputMatrix() {
        int[][] matrix = null;
        File file = new File("input.txt");
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
        for (int[] ints : matrix) {
            degree += ints[v];
        }
        return degree;
    }

    public static int[] getDegreeSequence(int[][] matrix) {
        int[] degreeSequence = new int[matrix.length];
        for (int i = 0; i < degreeSequence.length; ++i) {
            degreeSequence[i] = getDegree(matrix, i);
        }
        return degreeSequence;
    }

    /**
     * Проверка существвует ли эйлеров цикл(цепь).
     * Если существует цепь, но не существует цикл возвращаем
     * вершины которые следует обьеденить.
     */
    public static ArrayList<Integer> checkGraph(int[][] matrix) {
        int[] degreeSequence = getDegreeSequence(matrix);
        int oddCounter = 0;
        ArrayList<Integer> newEdge = new ArrayList<>();
        for (int i = 0; i < degreeSequence.length; ++i) {
            if (degreeSequence[i] % 2 == 1) {
                oddCounter++;
                newEdge.add(i);
            }
        }
        if (oddCounter != 0 && oddCounter != 2) {
            throw new UnsupportedOperationException();
        }
        return newEdge;
    }

    /**
     * Алгоритм нахождения эйлерова цикла
     */
    public static ArrayList<Integer> findEulerianCycle(int[][] matrix) {
        ArrayList<Integer> path = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        while(!stack.empty()) {
            Integer v = stack.peek();
            if (getDegree(matrix, v) == 0) {
                path.add(v);
                stack.pop();
            } else {
                for (int i = 0; i < matrix.length; ++i) {
                    if (matrix[i][v] >= 1) {
                        matrix[i][v]--;
                        matrix[v][i]--;
                        stack.push(i);
                        break;
                    }
                }
            }
        }
        return path;
    }

    /**
     * Удаляем мнимое ребро.
     */
    public static ArrayList<Integer> deleteNewEdge(ArrayList<Integer> path,
                                                   ArrayList<Integer> newEdge) {
        ArrayList<Integer> result = new ArrayList<>();
        int pivot;
        for (int i = 0; i < path.size(); ++i) {

        }
        return result;
    }

    public static void main(String[] args) {
        int[][] matrix = inputMatrix();
        ArrayList<Integer> newEdge = checkGraph(matrix);

        if (newEdge.size() == 2) {
            matrix[newEdge.get(0)][newEdge.get(1)]++;
            matrix[newEdge.get(1)][newEdge.get(0)]++;
        }

        ArrayList<Integer> path = findEulerianCycle(matrix);
        for (Integer el : path) {
            System.out.print(el + 1 + " ");
        }

        if (newEdge.size() == 2) {
            path = deleteNewEdge(path, newEdge);
        }
    }
}
