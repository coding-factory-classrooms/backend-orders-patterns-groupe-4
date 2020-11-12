package org.example;

import org.example.controllers.OrdersController;
import org.example.controllers.HomeController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;

public class App {
    public static void main(String[] args) {
        initialize();

        OrdersHandler ordersHandler = new OrdersHandler();
        HomeController homeController = new HomeController();
        OrdersController ordersController = new OrdersController(ordersHandler);

        // Home routes
        Spark.get("/", homeController::homePage);

        // Commands route
        Spark.get("/order", ordersController::createOrder);
        Spark.get("/orders/:id/customer", ordersController::customerDetail);
        Spark.get("/orders/:id/employee", ordersController::employeeDetail);
        Spark.get("/dashboard", ordersController::dashboard);
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
