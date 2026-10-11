
package com.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(
            System.getenv().getOrDefault("PORT", "8081")
        );

        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", port), 0
        );

        server.createContext("/", exchange -> {
            String response = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>CI/CD Project</title>
                </head>
                <body style="font-family: Arial; text-align: center;
                             margin-top: 100px;">
                    <h1>My CI/CD Pipeline Project</h1>
                    <h2>Java Application Running Successfully!</h2>
                    <p>Built with Java, Docker, Jenkins and AWS.</p>
                </body>
                </html>
                """;

            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8"
            );
            exchange.sendResponseHeaders(200, bytes.length);

            try (var output = exchange.getResponseBody()) {
                output.write(bytes);
            }
        });

        server.start();
        System.out.println("Application running on port " + port);
    }
}
