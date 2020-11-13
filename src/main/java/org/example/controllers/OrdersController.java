package org.example.controllers;

import org.example.OrdersHandler;
import org.example.core.Template;
import org.example.models.Order;
import org.example.models.items.consoles.Console;
import org.example.models.items.consoles.PS5;
import org.example.models.items.consoles.Switch;
import org.example.models.items.consoles.XboxSerieX;
import org.example.models.items.goodies.FateStrap;
import org.example.models.items.goodies.Goodie;
import org.example.models.items.goodies.SaberFigurine;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class OrdersController {
    private OrdersHandler ordersHandler;

    public OrdersController(OrdersHandler handler) {
        this.ordersHandler = handler;
    }

    public String createOrder(Request request, Response response) {
        String action = request.queryParamOrDefault("action", "");

        if (action.equals("create_order")) {
            String consoleName = request.queryParamOrDefault("console-name", "");
            String goodieName = request.queryParamOrDefault("goodie-name", "");

            Order order = new Order();
            if (!consoleName.equals("")) {
                Console console = this.createConsole(consoleName);
                if (console != null) order.addItem(console);
            }

            if (!goodieName.equals("")) {
                Goodie goodie = this.createGoodie(goodieName);
                if (goodie != null) order.addItem(goodie);
            }

            order.setOnChangeListener(ordersHandler);
            ordersHandler.addOrder(order);
            response.redirect("/orders/" + ordersHandler.getCommands().size() + "/customer");
        }

        Map<String, Object> params = new HashMap<>();
        return Template.render("create_order.html", params);
    }

    private Console createConsole(String consoleName) {
        if (consoleName.equals("ps5")) return new PS5();
        if (consoleName.equals("xbox")) return new XboxSerieX();
        if (consoleName.equals("switch")) return new Switch();
        return null;
    }

    private Goodie createGoodie(String goodieName) {
        if (goodieName.equals("fate-strap")) return new FateStrap();
        if (goodieName.equals("saber-figurine")) return new SaberFigurine();
        return null;
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
        params.put("orders", ordersHandler.getCommands());
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
        int id = Integer.parseInt(request.params("id"));
        int index = id - 1;

        Order order = ordersHandler.getCommands().get(index);

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        params.put("state", order.getState());
        params.put("items", order.getItems());
        return Template.render("customer/orderList.html", params);
    }
}
