package calculation.commands.qualityCharacteristics;

import calculation.Calculator;
import calculation.commands.Command;
import entities.MarkovChain;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import utilities.I18N;
import view.Controller;
import view.Manager;

import java.util.ArrayList;

public final class CommandB6 extends Command {
    public CommandB6(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 3);

        Label label = Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.absorbing_states", HPos.CENTER, true, 0, rowIndex, 1);
        Label labelResult = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.CENTER, true, 1, rowIndex++, 1);

        try {
            ArrayList<Integer> absorbingStates = Calculator.getAbsorbingStates(markovChain);
            StringBuilder result = new StringBuilder();
            for (Integer n : absorbingStates) {
                result.append(n).append(", ");
            }
            result.delete(result.length() - 2, result.length());
            labelResult.setText(result.toString());
        } catch (Exception ex) {
            Controller.showError(I18N.get("INVALID_INPUT_DATA"));
        }

    }
}
