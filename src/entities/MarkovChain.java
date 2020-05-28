package entities;

public class MarkovChain {
    private double adjacencyList[][];

    public MarkovChain(int numberStates) {
        adjacencyList = new double[numberStates][numberStates];
    }

    public void addEdge(int stateFrom, int stateTo, double probability) {
        adjacencyList[stateFrom][stateTo] = probability;
    }

    public double[][] getAdjacencyList() {
        return adjacencyList;
    }
}
