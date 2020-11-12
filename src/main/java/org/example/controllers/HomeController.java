package org.example.controllers;

import org.example.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    public String homePage(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        return Template.render("home.html", params);
    }
}
