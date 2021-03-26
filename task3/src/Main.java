import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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

    public static boolean isContains(boolean[] arr, boolean bool) {
        for (boolean x : arr) {
            if (x == bool) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<ArrayList<Integer>> getComponents(Matrix s_matrix) {
        boolean[] isCrossOut = new boolean[s_matrix.getRows()];
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        int p = 0;
        for (int i = 0; isContains(isCrossOut, false); ++i) {
            if (!isCrossOut[i]) {
                components.add(new ArrayList<>());
                for (int j = 0; j < s_matrix.getRows(); ++j) {
                    if (!isCrossOut[j] && s_matrix.get(i, j) == 1) {
                        components.get(p).add(j);
                        isCrossOut[j] = true;
                    }
                }
                p++;
            }
        }
        return components;
    }

    public static void printComponents(ArrayList<ArrayList<Integer>> components,
                                       Matrix matrix) {
        for (ArrayList<Integer> arr : components) {
            System.out.print("Vertexes: ");
            for (Integer element : arr) {
                System.out.print(element + " ");
            }
            System.out.println("\nMatrix:");
            printMatrixComponent(arr, matrix);
            System.out.println();
        }
    }

    public static void printMatrixComponent(ArrayList<Integer> arr,
                                            Matrix matrix) {
        for (Integer i: arr) {
            for (Integer j: arr) {
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Matrix matrix = inputMatrix();
        ArrayList<ArrayList<Integer>> components
                = getComponents(S_Matrix(T_Matrix(matrix)));
        printComponents(components, matrix);
    }
}
