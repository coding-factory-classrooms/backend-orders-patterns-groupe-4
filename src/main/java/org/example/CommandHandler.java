package org.example;

import org.example.models.Command;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    public List<Command> commands;


    public CommandHandler() {
        this.commands = new ArrayList<>();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        if (!command.getState().equals(Command.State.NEW)) {
            return;
        }

        this.commands.add(command);
    }

    public void updateOrder(Command order, Command.State newState) {
        if (order.getState() == Command.State.PROCESSED || order.getState() == Command.State.ABORTED) {
            return;
        }

        order.setState(newState);
    }
}
