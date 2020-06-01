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

public final class CommandB2 extends Command {
    public CommandB2(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 4);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.initial_state", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFInitState = Manager.addTextFieldToGridPane(table, HPos.LEFT, true,
                1, rowIndex++);

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.determine",
                HPos.CENTER, 0, rowIndex++, 2);

        Label label = Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.reachable_states", HPos.CENTER, false, 0, rowIndex, 1);
        Label labelResult = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.CENTER, false, 1, rowIndex++, 2);

        Label labelEssential = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.CENTER, false, 0, rowIndex++, 2);
        button.setOnAction(e ->
        {
            label.setVisible(false);
            labelEssential.setVisible(false);
            labelResult.setVisible(false);
            try {
                int initialState = Integer.parseInt(tFInitState.getText());
                ArrayList<Integer> list = Calculator.getListOfReachableStates(markovChain, initialState);
                StringBuilder result = new StringBuilder();
                for (Integer n : list) {
                    result.append(n).append(", ");
                }
                result.delete(result.length() - 2, result.length());
                labelResult.setText(result.toString());
                label.setVisible(true);
                labelResult.setVisible(true);


                if (Calculator.isEssentialState(markovChain,initialState)) {
                    labelEssential.textProperty().bind(I18N.createStringBinding("label.essential"));
                } else {
                    labelEssential.textProperty().bind(I18N.createStringBinding("label.notEssential"));
                }
                labelEssential.setVisible(true);
            } catch (Exception ex) {
                Controller.showError(I18N.get("INVALID_INPUT_DATA"));
            }
        });

    }
}
