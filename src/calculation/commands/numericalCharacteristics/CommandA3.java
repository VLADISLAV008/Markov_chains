package calculation.commands.numericalCharacteristics;

import calculation.Calculator;
import calculation.commands.Command;
import entities.MarkovChain;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import utilities.I18N;
import view.Controller;
import view.Manager;

import java.util.ArrayList;

public final class CommandA3 extends Command {
    public CommandA3(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 5);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.number_steps", HPos.CENTER, true, 0, rowIndex, 1);
        TextField tFNumberSteps = Manager.addTextFieldToGridPane(table, HPos.LEFT, true, 1, rowIndex++);

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.calculate",
                HPos.CENTER, 0, rowIndex++, 3);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.math_expect_uniform", HPos.LEFT, true, 0, rowIndex, 3);
        Label label1 = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, true, 3, rowIndex++, 1);

        GridPane gridPane = Manager.addGridPaneToGridPane(table, HPos.LEFT, 0, rowIndex, 5);
        int columnIndex = 0;
        Manager.addLabelToGridPane(gridPane, new Font("", 20), true,
                "label.math_expect_state", HPos.LEFT, true, columnIndex++, 0, 1);
        TextField tFState = Manager.addTextFieldToGridPane(gridPane, HPos.CENTER, true, columnIndex++, 0);
        gridPane.add(new Label(" : "), columnIndex++, 0);
        Label label2 = Manager.addLabelToGridPane(gridPane, new Font("", 20), false,
                "", HPos.CENTER, true, columnIndex++, 0, 1);
        deleteAllInsets(gridPane);

        tFState.setPrefWidth(50);
        GridPane.setMargin(label2, new Insets(0, 0, 0, 40));

        button.setOnAction(e ->
        {
            label1.setText("");
            label2.setText("");
            try {
                int numberSteps = Integer.parseInt(tFNumberSteps.getText());
                ArrayList<Double> initialProbabilities = new ArrayList<>();
                double probability = 1.0 / markovChain.getNumberStates();
                for (int i = 0; i < markovChain.getNumberStates(); i++) {
                    initialProbabilities.add(probability);
                }
                int result1 = Calculator.getMathematicalExpectation(markovChain, initialProbabilities, numberSteps);
                label1.setText(Integer.toString(result1));

                if (!"".equals(tFState.getText())) {
                    int state = Integer.parseInt(tFState.getText());
                    for (int i = 0; i < markovChain.getNumberStates(); i++) {
                        initialProbabilities.set(i, 0.0);
                        if (i == state) {
                            initialProbabilities.set(i, 1.0);
                        }
                    }
                    int result2 = Calculator.getMathematicalExpectation(markovChain, initialProbabilities, numberSteps);
                    label2.setText(Integer.toString(result2));
                }
            } catch (Exception ex) {
                Controller.showError(I18N.get("INVALID_INPUT_DATA"));
            }
        });
    }

    private void deleteAllInsets(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            GridPane.setMargin(node, null);
        }
    }
}
