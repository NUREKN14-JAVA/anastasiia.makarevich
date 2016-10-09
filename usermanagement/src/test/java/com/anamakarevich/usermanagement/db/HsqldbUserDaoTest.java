package com.anamakarevich.usermanagement.db;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {
    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;
    
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", 
                "jdbc:hsqldb:file:db/usermanagement", 
                "sa",
                "");
        dao = new HsqldbUserDao(connectionFactory);
        }

    @Test
    public void testCreate() {
        
        User user = getTestUser();
        
        // Check if the current id is null
        assertNull(user.getId());
        
        try {
            // Insert user into the database
            user = dao.create(user);
            
            // Check that method returned user
            assertNotNull(user);
            // Check that the the user id has been added successfully
            assertNotNull(user.getId());
        } catch (DatabaseException e) {
            
            e.printStackTrace();
            fail(e.toString());
        }

    }
    
    @Test
    public void testFindAll() {
        try {
            Collection<?> allUsers = dao.findAll();
            assertNotNull("Collection is null", allUsers);
            assertEquals("Collection size.",2,allUsers.size());
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testFind_ValidId() {
        try {
            User user1 = getTestUser();
            user1 = dao.create(user1);
            User user = dao.find(user1.getId());
            assertEquals("Found wrong user",user.getId(),user1.getId());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testDelete() {
        User user = getTestUser();
        Long id;
        try {
            user = dao.create(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        try {
            dao.delete(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        id = user.getId();
        try {
            user = dao.find(id);
            fail("User was not deleted");
        } catch (DatabaseException e) {
            assertEquals(e.getMessage().toString(), "User with id" + id + " is not found");
        }
        
    }
    
    @Test
    public void testFind_InvalidId() {
        Long id = 666L;
        
        try {
            dao.find(id);
            fail("Expected DatabaseException: User with id" + id + " is not found. Got a user instead");
        } catch (DatabaseException e) {
            assertEquals(e.getMessage().toString(), "User with id" + id + " is not found");
            
        }
        
    }
    
    @Test
    public void testUpdate() {
        User user = getTestUser();
        try {
            user = dao.create(user);
            String newFirstName = user.getFirstName() + "N";
            String newLastName = user.getLastName() + "N";
            LocalDate newDate = LocalDate.of(1942, 4, 13); 
            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            user.setDateOfBirthd(newDate);
            
            dao.update(user);
            user = dao.find(user.getId());
            assertEquals("First name update failed", newFirstName, user.getFirstName());
            assertEquals("Last name update failed", newLastName, user.getLastName());
            assertEquals("Date of birth update failed", newDate, user.getDateOfBirthd());
            
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", 
                "jdbc:hsqldb:file:db/usermanagement", 
                "sa",
                "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().
                getClassLoader().
                getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }
    
    /**
     * Creates a typical user instance for tests
     * @return user - properly initialized user with id = null
     */
    public User getTestUser() {
        User user = new User();
        user.setFirstName("Mick");
        user.setLastName("Jagger");
        user.setDateOfBirthd(LocalDate.of(1941,4,24));
        return user;
    }
    
    /**
     * Creates a date instance for tests
     * @param day - day of month starting from 1
     * @param month - month index starting from 0 for January
     * @param year
     * @return Date object initialized with the given parameters
     */
    public Date getDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        return date;
    }

}
