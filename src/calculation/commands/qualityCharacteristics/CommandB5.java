package calculation.commands.qualityCharacteristics;

import calculation.Calculator;
import calculation.commands.Command;
import entities.MarkovChain;
import javafx.geometry.HPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import utilities.I18N;
import view.Controller;
import view.Manager;

import java.util.ArrayList;

public final class CommandB5 extends Command {
    public CommandB5(String taskName, GridPane table) {
        super(taskName, table);
    }

    @Override
    public void execute(MarkovChain markovChain) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                taskName, HPos.CENTER, true, 0, rowIndex++, 2);

        ScrollPane scrollPane = new ScrollPane();
        GridPane.setColumnSpan(scrollPane, 1);
        table.add(scrollPane, 0, rowIndex++);

        try {
            ArrayList<ArrayList<Integer>> components =
                    Calculator.getEquivalenceClassesCommunicatingStates(markovChain);
            GridPane resultGridPane = createGridPane(components);
            scrollPane.setContent(resultGridPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            Controller.showError(I18N.get("INVALID_INPUT_DATA"));
        }
    }

    /**
     * Create a GridPane to display all equivalence classes of communicating states.
     */
    private GridPane createGridPane(ArrayList<ArrayList<Integer>> components) {
        GridPane table = new GridPane();
        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.equivalence_classes", HPos.CENTER, true, 0, 0, 1);
        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.states", HPos.CENTER, true, 0, 1, 1);
        for (int i = 0; i < components.size(); i++) {
            Manager.addLabelToGridPane(table, new Font("", 20), false,
                    Integer.toString(i + 1), HPos.CENTER, true, i + 1, 0, 1);
            StringBuilder sb = new StringBuilder();
            for (Integer state : components.get(i)) {
                sb.append(state).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            Manager.addLabelToGridPane(table, new Font("", 20), false,
                    sb.toString(), HPos.CENTER, true, i + 1, 1, 1);
        }
        return table;
    }
}
