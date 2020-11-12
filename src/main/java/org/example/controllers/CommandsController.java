package org.example.controllers;

import org.example.OrdersHandler;
import org.example.core.Template;
import org.example.models.Order;
import org.example.models.items.Console;
import org.example.models.items.Goodie;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CommandsController {
    private OrdersHandler ordersHandler;

    public CommandsController(OrdersHandler handler) {
        this.ordersHandler = handler;
    }

    public String createOrder(Request request, Response response) {
        String action = request.queryParamOrDefault("action", "");

        if (action.equals("create_order")) {
            String console = request.queryParamOrDefault("console-name", "");
            String goodie = request.queryParamOrDefault("goodie-name", "");

            Order order = new Order();
            if (!console.equals("")) {
                Console item = new Console();
                item.setName(console);
                order.addItem(item);
            }

            if (!goodie.equals("")) {
                Goodie item = new Goodie();
                item.setName(goodie);
                order.addItem(item);
            }
            order.setOnChangeListener(ordersHandler);
            ordersHandler.addCommand(order);

            // TODO: Rediriger vers la page de la commande (customer)
        }

        Map<String, Object> params = new HashMap<>();
        return Template.render("create_command.html", params);
    }

    public String dashboard(Request request, Response response) {
        String action = request.queryParamOrDefault("action", "");

        if (!action.isEmpty()) {
            if (action.equals("undo")) {
                ordersHandler.undoAction();
            } else if (action.equals("redo")) {
                ordersHandler.redoAction();
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("commands", ordersHandler.getCommands());
        params.put("history", ordersHandler.getHistory());
        params.put("historyIndex", ordersHandler.getHistoryIndex());
        return Template.render("dashboard.html", params);
    }

    public String employeeDetail(Request request, Response response) {
        int id = Integer.parseInt(request.params("id"));
        int index = id - 1;
        Order order = ordersHandler.getCommands().get(index);

        String newStateString = request.queryParamOrDefault("state", "");
        if (!newStateString.isEmpty()) {
            Order.State newState = Order.State.valueOf(newStateString);
            order.setState(newState);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("states", Order.State.values());
        return Template.render("employee/detail.html", params);
    }

    public String customerDetail(Request request, Response response) {
        return "";
    }
}
