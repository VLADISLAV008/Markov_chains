package calculation.commands.qualityCharacteristics;

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

import java.util.ArrayList;

public final class CommandB1 extends Command {
    public CommandB1(String taskName, GridPane table) {
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

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.determine",
                HPos.CENTER, 0, rowIndex++, 2);

        Label labelResult = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.CENTER, false, 0, rowIndex, 2);

        button.setOnAction(e ->
        {
            labelResult.setVisible(false);
            try {
                int initialState = Integer.parseInt(tFInitState.getText());
                int finiteState = Integer.parseInt(tFFiniteState.getText());
                ArrayList<Integer> list = Calculator.getListOfReachableStates(markovChain, initialState);
                boolean result = list.contains(finiteState);
                labelResult.setVisible(true);
                String text;
                if (result) {
                    text = "label.reachable";
                } else {
                    text = "label.notReachable";
                }
                labelResult.textProperty().bind(I18N.createStringBinding(text));
            } catch (Exception ex) {
                Controller.showError(I18N.get("INVALID_INPUT_DATA"));
            }
        });

    }
}
