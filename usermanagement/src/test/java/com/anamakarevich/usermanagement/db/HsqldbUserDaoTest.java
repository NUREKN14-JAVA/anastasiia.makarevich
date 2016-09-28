package com.anamakarevich.usermanagement.db;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.User;

import junit.framework.TestCase;

public class HsqldbUserDaoTest extends TestCase {
    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;
    
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        connectionFactory = new ConnectionFactoryImpl();
        dao = new HsqldbUserDao(connectionFactory);
        }

    @Test
    public void testCreate() {
        User user = new User();
        user.setFirstName("Mick");
        user.setLastName("Jagger");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1941, Calendar.MAY,24);
        Date dateOfBirthd = calendar.getTime();
        user.setDateOfBirthd(dateOfBirthd);
        assertNull(user.getId());
        try {
            user = dao.create(user);
            assertNotNull(user);
            assertNotNull(user.getId());
            } catch (DatabaseException e) {
             //TODO Auto-generated catch block
                e.printStackTrace();
                fail(e.toString());
            }
    }

}
