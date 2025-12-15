package com.example.vertxdevicesapi;

import io.vertx.core.Vertx;


public class VertxDevicesApiApplication {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new DevicesVerticle())
                .onSuccess(id -> {
                    System.out.println("==================================");
                    System.out.println("Devices API deployed successfully!");
                    System.out.println("Deployment ID: " + id);
                    System.out.println("==================================");
                })
                .onFailure(error -> {
                    System.err.println("Failed to deploy Verticle: " + error.getMessage());
                    error.printStackTrace();
                });
    }
}
