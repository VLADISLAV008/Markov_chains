package calculation.commands.numericalCharacteristics;

import calculation.Calculator;
import calculation.commands.Command;
import entities.MarkovChain;
import exceptions.AppException;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import utilities.I18N;
import view.Controller;
import view.Manager;

import java.util.ArrayList;

public final class CommandA2 extends Command {
    private ArrayList<TextField> textFieldsInitialProbabilities;

    public CommandA2(String taskName, GridPane table) {
        super(taskName, table);
        textFieldsInitialProbabilities = new ArrayList<>();
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 3);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.number_steps", HPos.LEFT, true, 0, rowIndex, 1);
        TextField tFNumberSteps = Manager.addTextFieldToGridPane(table, HPos.LEFT, true, 1, rowIndex++);

        GridPane gridPane = createGridPane(markovChain.getNumberStates());
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefHeight(130);
        GridPane.setColumnSpan(scrollPane, 3);
        table.add(scrollPane, 0, rowIndex++);

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.calculate",
                HPos.CENTER, 0, rowIndex++, 3);

        ScrollPane resultScrollPane = new ScrollPane();
        resultScrollPane.setPrefHeight(130);
        GridPane.setColumnSpan(resultScrollPane, 3);
        resultScrollPane.setVisible(false);
        table.add(resultScrollPane, 0, rowIndex++);

        button.setOnAction(e ->
        {
            try {
                ArrayList<Double> initialProbabilities = new ArrayList<>();
                double sum = 0;
                for (TextField textField : textFieldsInitialProbabilities) {
                    double value = Double.parseDouble(textField.getText());
                    sum += value;
                    initialProbabilities.add(value);
                }
                if (sum != 1) {
                    throw new AppException(I18N.get("INVALID_INPUT_DATA") + "\n" + I18N.get("INVALID_PROBABILITY"));
                }

                int numberSteps = Integer.parseInt(tFNumberSteps.getText());
                ArrayList<Double> probabilityDistribution =
                        Calculator.getProbabilityDistribution(markovChain, initialProbabilities, numberSteps);
                GridPane resultGridPane = createGridPane(markovChain.getNumberStates(), probabilityDistribution);
                resultScrollPane.setVisible(true);
                resultScrollPane.setContent(resultGridPane);
            } catch (AppException ex) {
                Controller.showError(ex.getMessage());
            } catch (Exception ex) {
                Controller.showError(I18N.get("INVALID_INPUT_DATA"));
            }
        });
    }

    /**
     * Create a GridPane to enter the initial probability distribution.
     */
    private GridPane createGridPane(int numberStates) {
        GridPane table = new GridPane();
        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.states", HPos.CENTER, true, 0, 0, 1);
        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.initial_probability_distribution", HPos.CENTER, true, 0, 1, 1);
        for (int i = 0; i < numberStates; i++) {
            Manager.addLabelToGridPane(table, new Font("", 20), false,
                    Integer.toString(i), HPos.CENTER, true, i + 1, 0, 1);
            TextField textField = Manager.addTextFieldToGridPane(table, HPos.CENTER,
                    false, i + 1, 1);
            textFieldsInitialProbabilities.add(textField);
            textField.setPrefWidth(80);
        }
        return table;
    }

    /**
     * Create a GridPane to display the resulting probability distribution.
     */
    private GridPane createGridPane(int numberStates, ArrayList<Double> probabilityDistribution) {
        GridPane table = new GridPane();
        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.states", HPos.CENTER, true, 0, 0, 1);
        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.initial_probability_distribution", HPos.CENTER, true, 0, 1, 1);
        for (int i = 0; i < numberStates; i++) {
            Manager.addLabelToGridPane(table, new Font("", 20), false,
                    Integer.toString(i), HPos.CENTER, true, i + 1, 0, 1);
            Manager.addLabelToGridPane(table, new Font("", 20), false,
                    Double.toString(probabilityDistribution.get(i)), HPos.CENTER,
                    true, i + 1, 1, 1);
        }
        return table;
    }
}
