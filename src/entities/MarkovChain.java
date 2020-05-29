package entities;

public class MarkovChain {
    private double[][] adjacencyList;
    private int numberStates;

    public MarkovChain(int numberStates) {
        this.numberStates = numberStates;
        adjacencyList = new double[numberStates][numberStates];
    }

    public void addEdge(int stateFrom, int stateTo, double probability) {
        adjacencyList[stateFrom][stateTo] = probability;
    }

    public double[][] getAdjacencyList() {
        return adjacencyList;
    }

    public int getNumberStates() {
        return numberStates;
    }
}
