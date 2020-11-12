package org.example;

import org.example.controllers.CommandsController;
import org.example.controllers.HomeController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;
import org.example.CommandHandler;

public class App {
    public static void main(String[] args) {
        initialize();

        CommandHandler ordersHandler = new CommandHandler();
        HomeController homeController = new HomeController();
        CommandsController commandsController = new CommandsController(ordersHandler);

        // Home routes
        Spark.get("/", homeController::homePage);

        // Commands route
        Spark.get("/order", commandsController::createOrder);
        Spark.get("/orders/:id/customer", commandsController::customerDetail);
        Spark.get("/orders/:id/employee", commandsController::employeeDetail);
        Spark.get("/dashboard", commandsController::dashboard);
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
