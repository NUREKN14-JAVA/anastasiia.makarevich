package com.anamakarevich.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String databaseDriver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl(String databaseDriver, String url, String user, String password) {
        
        this.databaseDriver = databaseDriver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection createConnection() throws DatabaseException {
        
        Connection connection;
        
        try {
            // Load class and run its static initializers that will register the driver
            Class.forName(databaseDriver);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();            
            throw new RuntimeException();
            }		
        
        try {
            // Get connection to database
            connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("ERROR: failed to get connection to HSQLDB.");
                e.printStackTrace();
                throw new DatabaseException();
                }
        return connection;
        }

}
