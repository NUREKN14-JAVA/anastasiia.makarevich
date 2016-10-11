package com.anamakarevich.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
    private final Properties properties;
    
    private final static DaoFactory INSTANCE = new DaoFactory();
    
    public static DaoFactory getInstance() {
        return INSTANCE;
    }
    
    public DaoFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private ConnectionFactory getConnectionFactory() {
        String databaseDriver=properties.getProperty("connection.drive");//"org.hsqldb.jdbcDriver"; 
        String url=properties.getProperty("connection.url");
                //"jdbc:hsqldb:file:db/usermanagement"; 
        String user=properties.getProperty("connection.user"); 
        String password=properties.getProperty("connection.password");
        properties.getProperty("connection.drive");
        ConnectionFactoryImpl connectionFactory= new ConnectionFactoryImpl(databaseDriver,url,user,password);
        return connectionFactory;
        
    }
    
    public UserDao getUserDao() {
        String userDaoStr = "dao.com.anamakarevich.usermanagement.db.UserDao";
        try {
            Class<?> cla = Class.forName(properties.getProperty(userDaoStr));
            UserDao userDao = (UserDao) cla.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
            return userDao;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

}
