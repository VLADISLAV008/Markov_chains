package algorithms;

import java.util.ArrayList;

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

    public static ArrayList<Integer> getListOfReachableVertices(double[][] adjacencyList, int state) {
        ArrayList<Integer> result = new ArrayList<>();
        boolean[] used = new boolean[adjacencyList.length];
        Algorithms.dfs(adjacencyList, used, state);
        for (int i = 0; i < used.length; i++) {
            if (used[i]) {
                result.add(i);
            }
        }
        return result;
    }

    private static void dfs(double[][] adjacencyList, boolean[] used, int state) {
        used[state] = true;
        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[state][i] > 0 && !used[i]) {
                dfs(adjacencyList, used, i);
            }
        }
    }

    public static boolean isStrongConnectedComponent(double[][] adjacencyList) {
        double[][] transposedGraph = buildTransposedGraph(adjacencyList);
        return getListOfReachableVertices(adjacencyList, 0).size() == adjacencyList.length &&
                getListOfReachableVertices(transposedGraph, 0).size() == adjacencyList.length;
    }

    public static ArrayList<ArrayList<Integer>> getStrongConnectedComponents(double[][] adjacencyList) {
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();

        double[][] transposedGraph = buildTransposedGraph(adjacencyList);
        ArrayList<Integer> order = topologicalSorting(adjacencyList);

        boolean[] used = new boolean[adjacencyList.length];

        for (int i = adjacencyList.length - 1; i >= 0; i--) {
            int s = order.get(i);
            if (!used[s]) {
                ArrayList<Integer> component = new ArrayList<>();
                dfs(transposedGraph, used, component, s);
                components.add(component);
            }
        }
        return components;
    }

    private static double[][] buildTransposedGraph(double[][] adjacencyList) {
        double[][] transposedGraph = new double[adjacencyList.length][adjacencyList.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            for (int j = 0; j < adjacencyList.length; j++) {
                transposedGraph[i][j] = adjacencyList[j][i];
            }
        }
        return transposedGraph;
    }

    private static ArrayList<Integer> topologicalSorting(double[][] adjacencyList) {
        ArrayList<Integer> order = new ArrayList<>();
        boolean[] used = new boolean[adjacencyList.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            if (!used[i]) {
                dfs(adjacencyList, used, order, i);
            }
        }
        return order;
    }

    private static void dfs(double[][] adjacencyList, boolean[] used, ArrayList<Integer> list, int state) {
        used[state] = true;
        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[state][i] > 0 && !used[i]) {
                dfs(adjacencyList, used, list, i);
            }
        }
        list.add(state);
    }
}
