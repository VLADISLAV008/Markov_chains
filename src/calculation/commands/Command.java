package calculation.commands;

import entities.MarkovChain;
import exceptions.AppException;
import javafx.scene.layout.GridPane;

public abstract class Command {
    protected String taskName;
    protected GridPane table;

    public Command(String taskName, GridPane table) {
        this.taskName = taskName;
        this.table = table;
    }

    public abstract void execute(MarkovChain markovChain) throws AppException;

    public String getTaskName() {
        return taskName;
    }
}
