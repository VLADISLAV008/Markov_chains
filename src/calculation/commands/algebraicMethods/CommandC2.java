package calculation.commands.algebraicMethods;

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

public final class CommandC2 extends Command {
    public CommandC2(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 3);

        Label labelPeriodic = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, true, 0, rowIndex++, 1);

        Label period = null;
        if(Calculator.isPeriodicChain(markovChain))
        {
            Manager.addLabelToGridPane(table, new Font("", 20), true,
                    "label.period_chain", HPos.LEFT, true, 0, rowIndex, 1);
            period = Manager.addLabelToGridPane(table, new Font("", 20), false,
                    "", HPos.LEFT, true, 1, rowIndex++, 1);
        }

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.periodic_states", HPos.LEFT, true, 0, rowIndex, 1);
        Label labelPeriodicStates = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, true, 1, rowIndex++, 1);

        try {
            if (Calculator.isIrreducibleChain(markovChain)) {
                if (Calculator.isPeriodicChain(markovChain)) {
                    labelPeriodic.textProperty().bind(I18N.createStringBinding("label.periodic"));
                } else {
                    labelPeriodic.textProperty().bind(I18N.createStringBinding("label.not_periodic"));
                }
                int p = Calculator.getPeriodOfChain(markovChain);
                period.setText(Integer.toString(p));
            } else {
                labelPeriodic.textProperty().bind(I18N.createStringBinding("label.reducible"));
            }

            ArrayList<Integer> periodicStates = Calculator.getPeriodicStates(markovChain);
            StringBuilder periodic = new StringBuilder();
            for (Integer i : periodicStates) {
                periodic.append(i).append(", ");
            }
            deleteTwoLastCharacters(periodic);
            labelPeriodicStates.setText(periodic.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            Controller.showError(I18N.get("INVALID_INPUT_DATA"));
        }
    }

    private static void deleteTwoLastCharacters(StringBuilder sb) {
        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length());
        } else {
            sb.append("-");
        }
    }
}
