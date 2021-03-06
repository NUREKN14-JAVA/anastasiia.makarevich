package com.anamakarevich.usermanagement.db;

/**
 * Dao Factory Implementation. 
 */
public class DaoFactoryImpl extends DaoFactory {
	
    public UserDao getUserDao() {
        UserDao result = null;
        try {
            Class<?> cla = Class.forName(properties.getProperty(USER_DAO));
            result = (UserDao) cla.newInstance();
            result.setConnectionFactory(getConnectionFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
