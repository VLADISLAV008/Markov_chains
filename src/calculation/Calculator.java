package calculation;

import algorithms.Algorithms;
import entities.MarkovChain;

public final class Calculator {

    public static double getTransitionProbability(MarkovChain markovChain,
                                                  int initialState, int finiteState, int numberSteps) {
        double[][] matrix = Algorithms.binaryExponentiationSquareMatrix(markovChain.getAdjacencyList(), numberSteps);
        return matrix[initialState][finiteState];
    }
}
