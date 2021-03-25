import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static Matrix inputMatrix() {
        Matrix matrix = null;
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            int N = sc.nextInt();
            int M = sc.nextInt();

            int[][] arr = new int[N][M];
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < M; ++j) {
                    arr[i][j] = sc.nextInt();
                }
            }
            matrix = new Matrix(arr);
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }
    public static void main(String[] args) {
        Matrix matrix = inputMatrix();
        System.out.println(matrix.toString());
    }
}
