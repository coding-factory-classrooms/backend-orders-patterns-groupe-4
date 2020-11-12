package org.example.controllers;

import org.example.CommandHandler;
import org.example.core.Template;
import org.example.models.Command;
import org.example.models.items.Console;
import org.example.models.items.Goodie;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CommandsController {
    private CommandHandler commandHandler;

    public CommandsController(CommandHandler handler) {
        this.commandHandler = handler;
    }

    public String createOrder(Request request, Response response) {
        String action = request.queryParamOrDefault("action", "");

        if (action.equals("create_order")) {
            String console = request.queryParamOrDefault("console-name", "");
            String goodie = request.queryParamOrDefault("goodie-name", "");

            Command command = new Command();
            if (!console.equals("")) {
                Console item = new Console();
                item.setName(console);
                command.addItem(item);
            }

            if (!goodie.equals("")) {
                Goodie item = new Goodie();
                item.setName(goodie);
                command.addItem(item);
            }
            command.setOnChangeListener(commandHandler);
            commandHandler.addCommand(command);

            // TODO: Rediriger vers la page de la commande (customer)
        }

        Map<String, Object> params = new HashMap<>();
        return Template.render("create_command.html", params);
    }

    public String dashboard(Request request, Response response) {
        String action = request.queryParamOrDefault("action", "");

        if (!action.isEmpty()) {
            if (action.equals("undo")) {
                commandHandler.undoAction();
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("commands", commandHandler.getCommands());
        params.put("history", commandHandler.getHistory());
        params.put("historyIndex", commandHandler.getHistoryIndex());
        return Template.render("dashboard.html", params);
    }

    public String employeeDetail(Request request, Response response) {
        int id = Integer.parseInt(request.params("id"));
        int index = id - 1;
        Command order = commandHandler.getCommands().get(index);

        String newStateString = request.queryParamOrDefault("state", "");
        if (!newStateString.isEmpty()) {
            Command.State newState = Command.State.valueOf(newStateString);
            order.setState(newState);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("states", Command.State.values());
        return Template.render("employee/detail.html", params);
    }

    public String customerDetail(Request request, Response response) {
        return "";
    }
}
