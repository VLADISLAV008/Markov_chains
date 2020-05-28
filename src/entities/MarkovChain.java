package entities;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkovChain {
    private ArrayList<HashMap<Integer, Double>> adjacencyList;

    public MarkovChain() {
        adjacencyList = new ArrayList<>();
    }

    public void addEdge(int stateFrom, int stateTo, double probability) {
        while (stateFrom >= adjacencyList.size()) {
            adjacencyList.add(new HashMap<>());
        }
        HashMap<Integer, Double> hashMap = adjacencyList.get(stateFrom);
        hashMap.put(stateTo, probability);
    }
}
