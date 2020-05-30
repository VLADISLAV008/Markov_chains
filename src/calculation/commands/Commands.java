package calculation.commands;

import calculation.commands.numericalCharacteristics.CommandA1;
import calculation.commands.numericalCharacteristics.CommandA2;
import calculation.commands.numericalCharacteristics.CommandA3;
import entities.MarkovChain;
import exceptions.AppException;
import javafx.scene.layout.GridPane;
import utilities.I18N;

import java.util.*;

public class Commands {
    private HashMap<String, Command> commands;

    public Commands(GridPane table) {
        commands = new HashMap<>();
        commands.put("A1", new CommandA1("command.A1", table));
        commands.put("A2", new CommandA2("command.A2", table));
        commands.put("A3", new CommandA3("command.A3", table));
    }

    public ArrayList<Map.Entry<String, String>> getCommandsInfo() {
        ArrayList<Map.Entry<String, String>> result = new ArrayList<>();

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            result.add(new Map.Entry<String, String>() {
                @Override
                public String getKey() {
                    return entry.getKey();
                }

                @Override
                public String getValue() {
                    return entry.getValue().taskName;
                }

                @Override
                public String setValue(String value) {
                    return null;
                }
            });
        }

        return result;
    }

    public void executeCommand(String command, MarkovChain markovChain) throws AppException {
        if (markovChain == null) {
            throw new AppException(I18N.get("FILE_NOT_UPLOADED"));
        }
        commands.get(command).execute(markovChain);
    }
}
