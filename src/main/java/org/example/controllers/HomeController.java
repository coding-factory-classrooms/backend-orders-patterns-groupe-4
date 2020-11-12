package org.example.controllers;

import org.example.OrdersHandler;
import org.example.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    private OrdersHandler ordersHandler;

    public HomeController(OrdersHandler handler) {
        this.ordersHandler = handler;
    }

    public String homePage(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        params.put("commands", ordersHandler.getCommands());
        return Template.render("home.html", params);
    }
}
