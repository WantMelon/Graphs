public class Matrix {
    private final int rows;
    private final int columns;
    int[][] matrix;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new int[rows][columns];
    }

    public Matrix(int size) {
        this.rows = this.columns = size;
        matrix = new int[rows][columns];
    }

    public Matrix(int[][] matrix) {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        this.matrix = matrix;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                string.append(matrix[i][j]).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public Matrix clone() {
        return new Matrix(this.matrix);
    }

    public int[][] toArray() {
        return matrix;
    }

    public int get(int row, int col)
            throws UnsupportedOperationException {
        if (row > this.rows || col > this.columns) {
            throw new UnsupportedOperationException();
        }
        return matrix[row][col];
    }

    public void put(int row, int col, int element)
            throws UnsupportedOperationException {
        if (row > this.rows || col > this.columns) {
            throw new UnsupportedOperationException();
        }
        matrix[row][col] = element;
    }

    public Matrix mul(Matrix anotherMatrix)
            throws UnsupportedOperationException {
        if (anotherMatrix.getRows() != columns) {
            throw new UnsupportedOperationException();
        }

        int[][] result = new int[anotherMatrix.getRows()][columns];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                for (int k = 0; k < rows; ++k) {
                    result[i][j] += matrix[i][k] * anotherMatrix.get(k, j);
                }
            }
        }
        return new Matrix(result);
    }

    public Matrix pow(int n) {
        Matrix result = this.clone();
        for (int i = 0; i < n; ++i) {
            result = mul(result);
        }
        return result;
    }

    public Matrix transposeMatrix() {
        int[][] result = new int[columns][rows];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                result[j][i] = this.get(i, j);
            }
        }
        return new Matrix(result);
    }

    public Matrix add(Matrix anotherMatrix)
            throws UnsupportedOperationException {
        if (anotherMatrix.getRows() != rows
                || anotherMatrix.getColumns() != columns) {
            throw new UnsupportedOperationException();
        }

        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                result[i][j] = matrix[i][j] + anotherMatrix.get(i, j);
            }
        }
        return new Matrix(result);
    }

    public static Matrix eye(int size) {
        int[][] newMatrix = new int[size][size];
        for (int i = 0; i < size; ++i) {
            newMatrix[i][i] = 1;
        }
        return new Matrix(newMatrix);
    }

    public Matrix elementByElementMul(Matrix matrix)
            throws UnsupportedOperationException {
        if (matrix.getRows() != rows
                || matrix.getColumns() != columns) {
            throw new UnsupportedOperationException();
        }

        int[][] result = new int[rows][columns];
        for (int i = 0; i < matrix.getRows(); ++i) {
            for (int j = 0; j < matrix.getColumns(); ++j) {
                result[i][j] = this.matrix[i][j] * matrix.get(i, j);
            }
        }
        return new Matrix(result);
    }
}
