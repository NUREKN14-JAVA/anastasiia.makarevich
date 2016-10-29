package com.anamakarevich.usermanagement.db;

public class DaoFactoryImpl extends DaoFactory {

    public UserDao getUserDao() {

        try {
            Class<?> cla = Class.forName(properties.getProperty(USER_DAO));
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
