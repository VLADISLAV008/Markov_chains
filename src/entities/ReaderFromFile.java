package entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ReaderFromFile {

    public static MarkovChain getMarkovChain(File file) throws FileNotFoundException {
        MarkovChain markovChain = new MarkovChain();
        Scanner s = new Scanner(file);

        int stateFrom = 0;
        while (s.hasNextLine()) {
            ArrayList<Edge> list = parsingData(s.nextLine(), stateFrom);
            for (Edge e : list) {
                markovChain.addEdge(e.stateFrom, e.stateTo, e.probability);
            }
            stateFrom++;
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


