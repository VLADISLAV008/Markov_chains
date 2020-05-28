package calculation;

import entities.MarkovChain;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import view.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Commands {
    private HashMap<String, String> commands;
    private MarkovChain markovChain;

    public Commands() {
        commands = new HashMap<>();
        commands.put("A1", "command.A1");
        commands.put("A2", "command.A2");
    }

    public Set<Map.Entry<String, String>> getCommandsInfo() {
        return commands.entrySet();
    }

    public void setMarkovChain(MarkovChain markovChain) {
        this.markovChain = markovChain;
    }

    public void executeCommand(String command, GridPane table) {
        switch (command) {
            case "A1":
                commandA1(table);
                break;
            case "A2":
                commandA2(table);
                break;
            default:
                break;
        }
    }

    private void commandA1(GridPane table) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                commands.get("A1"), HPos.CENTER, true, 0, rowIndex++, 3);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.initial_state", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFInitState = Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.finite_state", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFFiniteState = Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.number_steps", HPos.LEFT, true, 0, rowIndex, 1);
        final TextField tFNumberSteps = Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

        Button button = Manager.addButtonToGridPane(table, new Font(20), "button.calculate",
                HPos.CENTER, 0, rowIndex++, 2);

        Label label = Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.transition_probability", HPos.LEFT, false, 0, rowIndex, 1);
        Label labelResult = Manager.addLabelToGridPane(table, new Font("", 20), false,
                "", HPos.LEFT, false, 1, rowIndex, 1);

        button.setOnAction(e ->
        {
            label.setVisible(true);
            labelResult.setVisible(true);
            int initialState = Integer.parseInt(tFInitState.getText());
            int finiteState = Integer.parseInt(tFFiniteState.getText());
            int numberSteps = Integer.parseInt(tFNumberSteps.getText());
            double result = Calculator.getTransitionProbability(markovChain, initialState, finiteState, numberSteps);
            labelResult.setText(Double.toString(result));
        });

    }

    private void commandA2(GridPane table) {
        int rowIndex = 0;
        Manager.addLabelToGridPane(table, new Font("System Bold", 20), true,
                commands.get("A2"), HPos.CENTER, true, 0, rowIndex++, 3);

        Manager.addLabelToGridPane(table, new Font("", 20), true,
                "label.number_steps", HPos.LEFT, true, 0, rowIndex, 1);
        Manager.addTextFieldToGridPane(table, HPos.LEFT, 1, rowIndex++);

    }
}
