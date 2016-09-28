package com.anamakarevich.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String driver;
    private String url;
    private String user;
    private String password;

    @Override
    public Connection createConnection() throws DatabaseException {
        Connection connection;
        driver="org.hsqldb.jdbcDriver"; 
        url="jdbc:hsqldb:file:db/usermanagement"; 
        user="sa"; 
        password="";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
            }		
        
        try {
            connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new DatabaseException();
                }
        	// TODO Auto-generated method stub
        return connection;
        }

}
