package org.example;

import org.example.controllers.CommandsController;
import org.example.controllers.HomeController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;

import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        initialize();

        CommandHandler commandHandler = new CommandHandler();
        HomeController homeController = new HomeController(commandHandler);
        CommandsController commandsController = new CommandsController(commandHandler);

        // Home routes
        Spark.get("/", homeController::homePage);

        // Commands route
        Spark.get("/order", commandsController::createOrder);
    }

    static void initialize() {
        Template.initialize();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);
        final LoggerMiddleware log = new LoggerMiddleware();
        Spark.before(log::process);
    }
}
