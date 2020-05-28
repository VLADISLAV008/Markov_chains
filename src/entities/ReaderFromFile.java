package entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ReaderFromFile {

    public static MarkovChain getMarkovChain(File file) throws FileNotFoundException {
        ArrayList<String> inputData = new ArrayList<>();
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            inputData.add(s.nextLine());
        }

        MarkovChain markovChain = new MarkovChain(inputData.size());
        for (int i = 0; i < inputData.size(); i++) {
            ArrayList<Edge> list = parsingData(inputData.get(i), i);
            for (Edge e : list) {
                markovChain.addEdge(e.stateFrom, e.stateTo, e.probability);
            }
        }
        return markovChain;
    }

    private static ArrayList<Edge> parsingData(String data, int stateFrom) {
        ArrayList<Edge> result = new ArrayList<>();

        String regex = "\\b(\\d+?(\\.\\d+?)?)\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);

        int count = 0;
        int stateTo = -1;
        while (matcher.find()) {
            count++;
            String number = matcher.group(1);

            if (count % 2 == 1) {
                stateTo = Integer.parseInt(number);
            } else {
                double probability = Double.parseDouble(number);
                result.add(new Edge(stateFrom, stateTo, probability));
            }
        }
        return result;
    }

    private static class Edge {
        int stateFrom;
        int stateTo;
        double probability;

        public Edge(int stateFrom, int stateTo, double probability) {
            this.stateFrom = stateFrom;
            this.stateTo = stateTo;
            this.probability = probability;
        }
    }
}


