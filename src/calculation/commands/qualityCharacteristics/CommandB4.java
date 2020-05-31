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

public final class CommandB4 extends Command {
    public CommandB4(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 4);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.states", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFState1 = Manager.addTextFieldToGridPane(table, HPos.LEFT, true,
                1, rowIndex);
        final TextField tFState2 = Manager.addTextFieldToGridPane(table, HPos.LEFT, true,
                2, rowIndex++);

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.determine",
                HPos.CENTER, 0, rowIndex++, 2);

        Label labelResult = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.CENTER, false, 0, rowIndex++, 2);

        button.setOnAction(e ->
        {
            labelResult.setVisible(false);
            try {
                int state1 = Integer.parseInt(tFState1.getText());
                int state2 = Integer.parseInt(tFState2.getText());
                labelResult.setVisible(true);
                if (Calculator.isCommunicatingStates(markovChain, state1, state2)) {
                    labelResult.textProperty().bind(I18N.createStringBinding("label.communicating"));
                } else {
                    labelResult.textProperty().bind(I18N.createStringBinding("label.notCommunicating"));
                }
            } catch (Exception ex) {
                Controller.showError(I18N.get("INVALID_INPUT_DATA"));
            }
        });
    }
}
