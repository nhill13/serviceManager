package webserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class WebServer {

    private static WebServer instance = null;

    private final int PORT = 8080;
    private final String HOST = "localhost";

    private HttpServer server = null;

    // API Endpoints
    private final String loginEndpoint = "/api/login";

    public WebServer() throws IOException{
      
        if (instance == null) {

            instance = this;
        
        }

    this.server = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);

    server.createContext(loginEndpoint, new LoginHandler());
    server.setExecutor(null);
    server.start();
    
    }

    public WebServer getInstance() {
      
        return WebServer.instance;
    
    }
    
}