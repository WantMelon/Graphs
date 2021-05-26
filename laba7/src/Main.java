import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static int[][] inputMatrix(String filename) {
        int[][] matrix = null;
        File file = new File(filename);
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

    public static void main(String[] args) {
        int[][] matrix = inputMatrix("src/input.txt");
        boolean isBipartition = Checking.isBipartition(matrix);
        System.out.println(isBipartition);
    }
}
