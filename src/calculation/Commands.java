package calculation;

import entities.MarkovChain;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import view.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Commands {
    private HashMap<String, String> commands;

    public Commands() {
        commands = new HashMap<>();
        commands.put("A1", "command.A1");
        commands.put("A2", "command.A2");
    }

    public Set<Map.Entry<String, String>> getCommandsInfo() {
        return commands.entrySet();
    }

    public void executeCommand(String command, MarkovChain markovChain, GridPane table) {
        switch (command) {
            case "A1":
                commandA1(markovChain, table);
                break;
            case "A2":
                commandA2(markovChain, table);
                break;
            default:
                break;
        }
    }

    private void commandA1(MarkovChain markovChain, GridPane table) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                commands.get("A1"), HPos.CENTER, 0, rowIndex++, 3);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.initial_state", HPos.LEFT, 0, rowIndex, 1);
        Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.finite_state", HPos.LEFT, 0, rowIndex, 1);
        Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.number_steps", HPos.LEFT, 0, rowIndex, 1);
        Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.calculate",
                HPos.CENTER, 0, rowIndex++, 2);

        final int rowIndexCont = rowIndex;
        button.setOnAction(e ->
        {
            Manager.addLabelToGridPane(table, new Font("", 20), true,
                    "label.transition_probability", HPos.LEFT, 0, rowIndexCont, 1);
            String result = "";
            Manager.addLabelToGridPane(table, new Font("", 20), false,
                    result, HPos.LEFT, 1, rowIndexCont, 1);

        });

    }

    private void commandA2(MarkovChain markovChain, GridPane table) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                commands.get("A2"), HPos.CENTER, 0, rowIndex++, 3);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.number_steps", HPos.LEFT, 0, rowIndex, 1);
        Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

    }
}
