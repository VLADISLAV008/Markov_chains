package calculation;

import algorithms.Algorithms;
import entities.MarkovChain;

import java.util.ArrayList;

public final class Calculator {

    public static double getTransitionProbability(MarkovChain markovChain,
                                                  int initialState, int finiteState, int numberSteps) {
        double[][] matrix = Algorithms.binaryExponentiationSquareMatrix(markovChain.getAdjacencyList(), numberSteps);
        return matrix[initialState][finiteState];
    }

    public static ArrayList<Double> getProbabilityDistribution(
            MarkovChain markovChain, ArrayList<Double> initialProbabilities, int numberSteps) {
        ArrayList<Double> result = new ArrayList<>();
        double[][] matrix = Algorithms.binaryExponentiationSquareMatrix(markovChain.getAdjacencyList(), numberSteps);

        for (int i = 0; i < markovChain.getNumberStates(); i++) {
            double probability = 0;
            for (int j = 0; j < markovChain.getNumberStates(); j++) {
                probability += initialProbabilities.get(j) * matrix[j][i];
            }
            result.add(probability);
        }

        return result;
    }

    public static int getMathematicalExpectation(
            MarkovChain markovChain, ArrayList<Double> initialProbabilities, int numberSteps) {
        double result = 0;
        ArrayList<Double> curProbabilitiesDistribution = getProbabilityDistribution(markovChain, initialProbabilities, numberSteps);

        for (int i = 0; i < markovChain.getNumberStates(); i++) {
            result += curProbabilitiesDistribution.get(i) * i;
        }

        return Double.valueOf(result).intValue();
    }
}
