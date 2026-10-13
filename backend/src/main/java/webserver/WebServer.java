package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import static java.util.stream.Collectors.joining;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

    private static WebServer instance = null;

    private final int PORT = 8080;
    private final String HOST = "localhost";

    private HttpServer server = null;

    public WebServer() throws IOException{
      
        if (instance == null) {

            instance = this;
        
        }

    this.server = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);

    server.createContext("/api/login", new LoginHandler());
    server.setExecutor(null);
    server.start();
    
    }

    static class LoginHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Guard clause: Reject anything that isn't an authentication POST
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {

                sendResponse(exchange, 405, "Method Not Allowed");
                return;

            }

            try {

                // Read the incoming credentials payload from the stream
                String requestBody;

                requestBody = new BufferedReader(
                        new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(joining("\n"));

                // Print to console to verify Apache -> Java piping is working perfectly
                System.out.println("Received auth payload: " + requestBody);

                // TODO: Parse credentials and validate securely
                // For now, return a successful stub response
                String jsonResponse = "{\"success\": true, \"message\": \"Authenticated successfully\"}";
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                sendResponse(exchange, 200, jsonResponse);

            } catch (IOException e) {
            
                sendResponse(exchange, 500, "Internal Server Error");
            
            }

        }

        // Helper method to safely write raw text bytes back to the browser via Apache
        private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
            
            byte[] bytes = responseText.getBytes(StandardCharsets.UTF_8);
            
            exchange.sendResponseHeaders(statusCode, bytes.length);
            
            try (OutputStream os = exchange.getResponseBody()) {
            
                os.write(bytes);
            
            }
        
        }
    
    }

    public WebServer getInstance() {
      
        return WebServer.instance;
    
    }
    
}