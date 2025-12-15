package com.example.vertxdevicesapi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class DevicesVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        // Create a Router
        Router router = Router.router(vertx);

        // Define routes
        setupRoutes(router);

        int port = config().getInteger("http.port", 8080);

        vertx.createHttpServer()
                .requestHandler(router) // Attach the router to handle requests
                .listen(port)
                .onSuccess(server -> {
                    System.out.println("✓ HTTP server started on port " + port);
                    System.out.println("✓ Try: http://localhost:" + port + "/api/devices");
                    startPromise.complete(); // Signal successful startup
                })
                .onFailure(error -> {
                    System.err.println("✗ Failed to start HTTP server: " + error.getMessage());
                    startPromise.fail(error); // Signal startup failure
                });
    }

    private void setupRoutes(Router router) {
        // Root endpoint - health check
        router.get("/").handler(this::handleRoot);

        // GET /api/devices - returns list of supported devices
        router.get("/api/devices").handler(this::handleGetDevices);
    }

    private void handleRoot(RoutingContext context) {
        context.response()
                .putHeader("content-type", "application/json")
                .end("{\"message\": \"Vert.x Devices API is running\", \"status\": \"OK\"}");
    }

    private void handleGetDevices(RoutingContext context) {
        // Create JSON array of devices
        JsonArray devices = new JsonArray()
                .add("IOS")
                .add("WEB")
                .add("DESKTOP");

        // Send response
        context.response()
                .putHeader("content-type", "application/json")
                .end(devices.encodePrettily());
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        System.out.println("Shutting down Devices API...");
        stopPromise.complete();
    }
}
