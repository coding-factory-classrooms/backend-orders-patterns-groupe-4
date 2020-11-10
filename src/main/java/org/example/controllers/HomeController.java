package org.example.controllers;

import org.example.CommandHandler;
import org.example.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    private CommandHandler commandHandler;

    public HomeController(CommandHandler handler) {
        this.commandHandler = handler;
    }

    public String homePage(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        params.put("commands", commandHandler.getCommands());
        return Template.render("home.html", params);
    }
}
