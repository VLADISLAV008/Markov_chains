package calculation.commands;

import calculation.commands.algebraicMethods.*;
import calculation.commands.numericalCharacteristics.*;
import calculation.commands.qualityCharacteristics.*;
import entities.MarkovChain;
import exceptions.AppException;
import javafx.scene.layout.GridPane;
import utilities.I18N;

import java.util.*;

public class Commands {
    private LinkedHashMap<String, Command> commands;

    public Commands(GridPane table) {
        commands = new LinkedHashMap<>();
        commands.put("A1", new CommandA1("command.A1", table));
        commands.put("A2", new CommandA2("command.A2", table));
        commands.put("A3", new CommandA3("command.A3", table));

        commands.put("B1", new CommandB1("command.B1", table));
        commands.put("B2", new CommandB2("command.B2", table));
        commands.put("B3", new CommandB3("command.B3", table));
        commands.put("B4", new CommandB4("command.B4", table));

        commands.put("C1", new CommandC1("command.C1", table));
        commands.put("C2", new CommandC2("command.C2", table));
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
