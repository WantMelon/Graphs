import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static Matrix inputMatrix() {
        Matrix matrix = null;
        File file = new File("task3/input.txt");
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

    public static Matrix sign(Matrix matrix) {
        int[][] newMatrix = new int[matrix.getRows()][matrix.getColumns()];

        for (int i = 0; i < matrix.getRows(); ++i) {
            for (int j = 0; j < matrix.getColumns(); ++j) {
                if (matrix.get(i, j) > 0) {
                    newMatrix[i][j] = 1;
                } else if (matrix.get(i, j) == 0) {
                    newMatrix[i][j] = 0;
                } else if (matrix.get(i, j) < 0) {
                    newMatrix[i][j] = -1;
                }
            }
        }
        return new Matrix(newMatrix);
    }

    public static Matrix T_Matrix(Matrix matrix) {
        Matrix result = new Matrix(matrix.getRows());
        for (int i = 1; i < matrix.getRows(); ++i) {
            result = result.add(matrix.pow(i));
        }
        result = result.add(Matrix.eye(matrix.getRows()));
        result = sign(result);
        return result;
    }

    public static Matrix S_Matrix(Matrix t_matrix) {
        return t_matrix.transposeMatrix().elementByElementMul(t_matrix);
    }

    public static void printResult(Matrix s_matrix) {
        int p = 1;
    }

    public static void main(String[] args) {
        Matrix matrix = inputMatrix();
        System.out.println(S_Matrix(T_Matrix(matrix)).toString());
    }
}
