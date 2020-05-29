package calculation.commands.numericalCharacteristics;

import calculation.Calculator;
import calculation.commands.Command;
import entities.MarkovChain;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import utilities.I18N;
import view.Controller;
import view.Manager;

public final class CommandA1 extends Command {
    public CommandA1(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 3);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.initial_state", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFInitState = Manager.addTextFieldToGridPane(table, HPos.LEFT, true,
                1, rowIndex++);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.finite_state", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFFiniteState = Manager.addTextFieldToGridPane(table, HPos.LEFT, true,
                1, rowIndex++);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.number_steps", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFNumberSteps = Manager.addTextFieldToGridPane(table, HPos.LEFT, true,
                1, rowIndex++);

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.calculate",
                HPos.CENTER, 0, rowIndex++, 2);

        Label label = Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.transition_probability", HPos.LEFT, false, 0, rowIndex, 1);
        Label labelResult = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, false, 1, rowIndex, 1);

        button.setOnAction(e ->
        {
            try {
                int initialState = Integer.parseInt(tFInitState.getText());
                int finiteState = Integer.parseInt(tFFiniteState.getText());
                int numberSteps = Integer.parseInt(tFNumberSteps.getText());
                double result = Calculator.getTransitionProbability(markovChain, initialState, finiteState, numberSteps);
                label.setVisible(true);
                labelResult.setVisible(true);
                labelResult.setText(Double.toString(result));
            } catch (Exception ex) {
                Controller.showError(I18N.get("INVALID_INPUT_DATA"));
            }
        });

    }
}
