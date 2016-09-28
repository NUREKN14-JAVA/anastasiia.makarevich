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
        
        // Check if the current id is null
        assertNull(user.getId());
        
        try {
            // Insert user into the database
            user = dao.create(user);
            
            assertNotNull(user);
            // Check that the the user id has been added successfully
            assertNotNull(user.getId());
        } catch (DatabaseException e) {
            
            e.printStackTrace();
            fail(e.toString());
        }

            
            
    }
            
    

}
