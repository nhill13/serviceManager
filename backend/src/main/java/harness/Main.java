package harness;

import java.io.IOException;
import java.sql.SQLException;

import database.DBConnector;
import webserver.WebServer;

public class Main {

    public static void main(String[] args) throws IOException {

        boolean running = true;
        
        System.out.println("Starting Service Manager...");

        try {

            DBConnector dbConnector = new DBConnector();
        
        } catch (SQLException e) {

            System.err.println("Failed to connect to the database: " + e.getMessage());
            return;

        }

        try {

            WebServer webServer = new WebServer();
        
        } catch (IOException e) {

            System.err.println("Failed to start the web server: " + e.getMessage());
            //return;

        }

    }
    
}