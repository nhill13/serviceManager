package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private String url = "jdbc:postgresql://localhost:5432/servicemanager";
    private String dbUser = "servicemanager";
    private String dbPassword = "P@55w0rd";

    public DBConnector() {
        
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword)) {
            
            if (connection != null) {
            
                System.out.println("Connected to the PostgreSQL server successfully!");
            
            }
        
        } catch (SQLException e) {
        
            System.err.println("Connection failure: " + e.getMessage());
        
        }

    }
    
}