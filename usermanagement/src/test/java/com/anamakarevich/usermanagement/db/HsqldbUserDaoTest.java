package com.anamakarevich.usermanagement.db;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
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
        dao = new HsqldbUserDao(connectionFactory);
        }

    @Test
    public void testCreate() {
        
        User user = new User();
        user.setFirstName("Andy");
        user.setLastName("Warhall");
        user.setDateOfBirthd(LocalDate.of(1931, 7, 26));
        
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
            assertEquals("Collection size is incorrect.",2,allUsers.size());
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.toString());
        }
        
    }
    
    @Test
    public void testFind_ValidId() {
        try {
            User user = dao.find(1L);
            //User user1 = getTestUser();
            //user1 = dao.create(user1);
            //User user = dao.find(user1.getId());
            assertEquals("Found wrong user", "Mick",user.getFirstName());
            assertEquals("Found wrong user", "Jagger",user.getLastName());
            assertEquals("Found wrong user", LocalDate.of(1943, 7, 26),user.getDateOfBirthd());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    
    @Test 
    public void testFindByName() {
        try {
            Collection<User> users = (Collection<User>) dao.find("Mick", "Jagger");
            assertEquals("Collection size is incorrect.",1,users.size());
            
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDelete() {
        
        User user = new User();
        user.setId(1L);
        user.setFirstName("Mick");
        user.setLastName("Jagger");
        user.setDateOfBirthd(LocalDate.of(1943, 7, 26));

        try {
            dao.delete(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Long id = 1L;
        try {
            user = dao.find(1L);
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
        try {
            User user = new User();
            user.setId(1L);
            
            String newFirstName = "Jack";
            String newLastName = "Magger";
            LocalDate newDate = LocalDate.of(1941, 4, 13); 
            
            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            user.setDateOfBirthd(newDate);
            
            dao.update(user);
            user = dao.find(user.getId());
            assertEquals("First name update failed", newFirstName, user.getFirstName());
            assertEquals("Last name update failed", newLastName, user.getLastName());
            assertEquals("Date of birth update failed", newDate, user.getDateOfBirthd());
            
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
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
        IDataSet dataSet = new XmlDataSet(this.getClass().getClassLoader()
                .getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }
    
    /**
     * Creates a typical user instance for tests
     * @return user - properly initialized user with id = null
     */
    public User getTestUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Mick");
        user.setLastName("Jagger");
        user.setDateOfBirthd(LocalDate.of(1941,4,24));
        return user;
    }
   
    
}
