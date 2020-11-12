package org.example;

import org.example.models.Command;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CommandHandler implements OnOrderChangeListener{
    public List<Command> commands;
    private List<CommandsMemento> history;
    private int historyIndex;


    public CommandHandler() {
        this.commands = new ArrayList<>();
        this.history = new ArrayList<>();
        historyIndex = 0;
    }

    public List<Command> getCommands() {
        return commands;
    }



    public void addCommand(Command command) {
        if (!command.getState().equals(Command.State.NEW)) {
            return;
        }

        this.commands.add(command);
        this.setState();
    }


    public void setState() {
        this.history.add( new CommandsMemento(this.commands));
        historyIndex++;

    }

    public List<CommandsMemento> getHistory() {
        return history;
    }

    @Override
    public void onOrderChange() {
        this.setState();
    }

    public void undoAction() {
        int undoIndex = historyIndex -1;
        if (undoIndex < 0) {
            return;
        }
        historyIndex = undoIndex;
        this.commands = new ArrayList<>(this.history.get(historyIndex).getState());
    }

    public int getHistoryIndex() {
        return this.historyIndex;
    }

    public class CommandsMemento {
        private List<Command> state;

        public List<Command> getState() {
            return state;
        }

        public CommandsMemento(List<Command> state) {
            this.state = this.clone(state);
        }

        private List<Command> clone(List<Command> commands) {
            List<Command> commandList = new ArrayList<>();

            for (Command command: commands) {
                Command copyCommand = new Command();
                copyCommand.setState(command.getState());
                copyCommand.getItems().addAll(command.getItems());
                commandList.add(copyCommand);
            }
            return commandList;
        }

        @Override
        public String toString() {
            return Arrays.toString(this.state.toArray());
        }

    }
}

