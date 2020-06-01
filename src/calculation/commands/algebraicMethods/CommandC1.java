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

public final class CommandC1 extends Command {
    public CommandC1(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 3);

        Label labelIrreducible = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, true, 0, rowIndex++, 2);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.count_components", HPos.LEFT, true, 0, rowIndex, 1);
        Label labelCountClasses = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, true, 1, rowIndex++, 1);

        try {
            if (Calculator.isIrreducibleChain(markovChain)) {
                labelIrreducible.textProperty().bind(I18N.createStringBinding("label.irreducible"));
            } else {
                labelIrreducible.textProperty().bind(I18N.createStringBinding("label.reducible"));
            }

            ArrayList<ArrayList<Integer>> components =
                    Calculator.getEquivalenceClassesCommunicatingStates(markovChain);
            labelCountClasses.setText(Integer.toString(components.size()));

        } catch (Exception ex) {
            ex.printStackTrace();
            Controller.showError(I18N.get("INVALID_INPUT_DATA"));
        }
    }
}
