package calculation.commands.algebraicMethods;

import calculation.Calculator;
import calculation.commands.Command;
import entities.MarkovChain;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import utilities.I18N;
import view.Controller;
import view.Manager;

import java.util.ArrayList;

public final class CommandC3 extends Command {
    public CommandC3(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 3);

        Label labelErgodicChain = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, true, 0, rowIndex++, 1);

        ScrollPane resultScrollPane8 = new ScrollPane();
        resultScrollPane8.setPrefHeight(130);
        GridPane.setColumnSpan(resultScrollPane8, 3);
        resultScrollPane8.setVisible(false);
        table.add(resultScrollPane8, 0, rowIndex++);

        ScrollPane resultScrollPane16 = new ScrollPane();
        resultScrollPane16.setPrefHeight(130);
        GridPane.setColumnSpan(resultScrollPane16, 3);
        resultScrollPane16.setVisible(false);
        table.add(resultScrollPane16, 0, rowIndex++);

        ScrollPane resultScrollPane32 = new ScrollPane();
        resultScrollPane32.setPrefHeight(130);
        GridPane.setColumnSpan(resultScrollPane32, 3);
        resultScrollPane32.setVisible(false);
        table.add(resultScrollPane32, 0, rowIndex++);

        try {
            if (Calculator.isIrreducibleChain(markovChain) && !Calculator.isPeriodicChain(markovChain)) {
                labelErgodicChain.textProperty().bind(I18N.createStringBinding("label.ergodic_chain"));

                ArrayList<Double> initialProbabilities = new ArrayList<>();
                double probability = 1.0 / markovChain.getNumberStates();
                for (int i = 0; i < markovChain.getNumberStates(); i++) {
                    initialProbabilities.add(probability);
                }

                ArrayList<Double> probabilityDistribution =
                        Calculator.getProbabilityDistribution(markovChain, initialProbabilities, 8);
                GridPane resultGridPane8 = Manager.createGridPane(markovChain.getNumberStates(),
                        probabilityDistribution, "label.result_probability_distribution_8_steps");
                resultScrollPane8.setVisible(true);
                resultScrollPane8.setContent(resultGridPane8);

                probabilityDistribution =
                        Calculator.getProbabilityDistribution(markovChain, initialProbabilities, 16);
                GridPane resultGridPane16 = Manager.createGridPane(markovChain.getNumberStates(),
                        probabilityDistribution, "label.result_probability_distribution_16_steps");
                resultScrollPane16.setVisible(true);
                resultScrollPane16.setContent(resultGridPane16);

                probabilityDistribution =
                        Calculator.getProbabilityDistribution(markovChain, initialProbabilities, 16);
                GridPane resultGridPane32 = Manager.createGridPane(markovChain.getNumberStates(),
                        probabilityDistribution, "label.result_probability_distribution_32_steps");
                resultScrollPane32.setVisible(true);
                resultScrollPane32.setContent(resultGridPane32);
            } else {
                labelErgodicChain.textProperty().bind(I18N.createStringBinding("label.not_ergodic_chain"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Controller.showError(I18N.get("INVALID_INPUT_DATA"));
        }
    }
}
