package harness;

import java.sql.SQLException;

import database.DBConnector;

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting Service Manager...");

        try {

            DBConnector dbConnector = new DBConnector();
        
        } catch (SQLException e) {

            System.err.println("Failed to connect to the database: " + e.getMessage());
            return;

        }

        boolean running = true;

        while (running) {

        }

    }
    
}