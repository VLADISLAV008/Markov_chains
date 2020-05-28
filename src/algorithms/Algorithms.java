package algorithms;

public class Algorithms {
    public static double[][] binaryExponentiationSquareMatrix(double[][] matrix, int power) {
        double[][] result = copyMatrix(matrix);
        power--;
        double[][] currentMatrix = copyMatrix(matrix);
        while (power != 0) {
            if (power % 2 == 0) {
                currentMatrix = multiplySquareMatrices(currentMatrix, currentMatrix);
                power /= 2;
            } else {
                result = multiplySquareMatrices(result, currentMatrix);
                power--;
            }
        }
        return result;
    }

    private static double[][] multiplySquareMatrices(double[][] matrix1, double[][] matrix2) {
        double[][] result = new double[matrix1.length][matrix1.length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                for (int r = 0; r < matrix1.length; r++) {
                    result[i][j] += matrix1[i][r] * matrix2[r][j];
                }
            }
        }
        return result;
    }

    private static double[][] copyMatrix(double[][] matrix) {
        double[][] m = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, m[i], 0, matrix.length);
        }
        return m;
    }

}
