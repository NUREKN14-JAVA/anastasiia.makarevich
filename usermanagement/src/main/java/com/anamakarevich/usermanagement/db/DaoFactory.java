package com.anamakarevich.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
     
    protected static final String USER_DAO = "dao.com.anamakarevich.usermanagement.db.UserDao";
    private static final String DAO_FACTORY = "dao.factory";
    protected static Properties properties;   
    private static DaoFactory instance;
    
    /**
     * Returns an instance of class that implements user dao
     * 
     * @return instance of UserDao
     */
    public abstract UserDao getUserDao();
    
    /**
     * Returns instance of the dao factory. Uses reflection 
     * mechanism to obtain the class of the factory 
     * implementation defined in the properties.   
     * @return DaoFactory instance
     */
    public static synchronized DaoFactory getInstance() {
        if (instance == null){
            Class<?> factoryClass;
            try {
                factoryClass = Class.forName(properties
                    .getProperty(DAO_FACTORY));
            instance = (DaoFactory) factoryClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
       return instance;
    }
    
    // Static initialiser
    // Tries to download properties from settings.properties file 
    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().
                    getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Set up properites for the factory
     * @param properties
     */
    public static void init(Properties prop) {
        properties = prop;
        instance = null;
    }
    
    /**
     * Default constructor
     */
    protected DaoFactory() {
    }
    
    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }
    


}
