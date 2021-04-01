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

    public static int getDegree(int[][] matrix, int v) {
        int degree = 0;
        for (int[] ints : matrix) {
            if (ints[v] == 1) degree++;
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

    public static ArrayList<Integer> checkGraph(int[][] matrix) {
        int[] degreeSequence = getDegreeSequence(matrix);
        int oddCounter = 0;
        ArrayList<Integer> newEdge = new ArrayList<>();
        for (int el : degreeSequence) {
            if (el % 2 == 1) {
                oddCounter++;
                newEdge.add(el);
            }
        }
        if (oddCounter != 0 && oddCounter != 2) {
            throw new UnsupportedOperationException();
        }
        return newEdge;
    }

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
                    if (matrix[i][v] == 1) {
                        matrix[i][v] = matrix[v][i] = 0;
                        stack.push(i);
                        break;
                    }
                }
            }
        }
        return path;
    }

    public static void main(String[] args) {
        int[][] matrix = inputMatrix();
        ArrayList<Integer> newEdge = checkGraph(matrix);

        if (newEdge.size() == 2) {
            matrix[newEdge.get(0)][newEdge.get(1)] = 1;
            matrix[newEdge.get(1)][newEdge.get(0)] = 1;
        }

        ArrayList<Integer> path = findEulerianCycle(matrix);
        System.out.println(path.toString());
    }
}
