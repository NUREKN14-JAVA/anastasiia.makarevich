package com.anamakarevich.usermanagement.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DaoFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetUserDao() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        assertNotNull("Dao Factory is null", daoFactory);
        UserDao userDao = daoFactory.getUserDao();
        assertNotNull("User Dao is null", userDao);
        
    }

}
