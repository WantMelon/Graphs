package task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> inputPruferCode(String filename) {
        ArrayList<Integer> pruferCode = new ArrayList<>();
        File file = new File(filename);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextInt()) {
                pruferCode.add(sc.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return pruferCode;
    }

    /**
     * Находит наименьший элемент множества вершин,
     * не содержащийся в коде Прюфера.
     */
    public static int getMin(List<Integer> vertexSet, List<Integer> pruferCode) {
        int min = Integer.MAX_VALUE;
        for (int vertex : vertexSet) {
            if (!pruferCode.contains(vertex) && vertex < min) {
                min = vertex;
            }
        }
        return min;
    }

    public static int[][] pruferCodeToAdjacencyMatrix
            (List<Integer> pruferCode) {
        List<Integer> vertexSet = new ArrayList<>();
        for (int i = 0; i < pruferCode.size() + 2; ++i) {
            vertexSet.add(i);
        }
        int[][] matrix = new int[vertexSet.size()][vertexSet.size()];

        while (!pruferCode.isEmpty()) {
            int a = getMin(vertexSet, pruferCode);
            vertexSet.remove(Integer.valueOf(a));
            int b = pruferCode.remove(0);
            matrix[a][b] = matrix[b][a] = 1;
        }
        matrix[vertexSet.get(0)][vertexSet.get(1)] = 1;
        matrix[vertexSet.get(1)][vertexSet.get(0)] = 1;
        return matrix;
    }

    public static void main(String[] args) {
        List<Integer> pruferCode = inputPruferCode("src/task2/input.txt");
        int[][] matrix = pruferCodeToAdjacencyMatrix(pruferCode);
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
