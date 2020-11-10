package org.example;

import org.example.models.Command;

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
}
