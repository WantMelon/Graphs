import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int[][] inputMatrix(String filename) {
        int[][] matrix = null;
        File file = new File(filename);
        try (Scanner sc = new Scanner(file)) {
            int n = sc.nextInt();
            int m = sc.nextInt();

            matrix = new int[n][m];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    matrix[i][j] = sc.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static int[] sequentialPainting(int[][] matrix) {
        // Для каждой вершины присваиваем множество допустимых цветов
        List<List<Integer>> colorSet = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            List<Integer> color = new ArrayList<>();
            for (int j = 0; j < matrix.length; j++) {
                color.add(j + 1);
            }
            colorSet.add(color);
        }

        int[] colors = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            // Присваиваем вершине минимальный цвет
            colors[i] = colorSet.get(i).get(0);
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] > 0) {
                    // Для смежных вершин удаляем цвет из множества цветов
                    colorSet.get(j).remove(colorSet.get(i).get(0));
                }
            }
        }

        return colors;
    }

    public static int[] greedyPainting(int[][] matrix) {
        int[] colors = new int[matrix.length];
        boolean[] used = new boolean[matrix.length];

        for (int i = 0; i < colors.length; i++) {
            // Независимое множество
            List<Integer> set = new ArrayList<>();
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 0) {
                    set.add(j);
                    used[j] = true;
                }
            }
        }

        return colors;
    }

    public static void main(String[] args) {
        int[][] matrix = inputMatrix("src/input.txt");

        System.out.println("Sequential painting: " + Arrays.toString(sequentialPainting(matrix)));
    }
}
