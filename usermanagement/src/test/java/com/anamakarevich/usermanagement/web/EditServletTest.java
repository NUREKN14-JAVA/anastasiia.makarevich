package com.anamakarevich.usermanagement.web;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.User;

public class EditServletTest extends MockServletTestCase {

    /* (non-Javadoc)
     * @see com.anamakarevich.usermanagement.web.MockServletTestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    @Test
    public void testEdit() {
        // create user
        LocalDate date = LocalDate.now();
        User user = new User(666L, "Ozzy", "Osbourne", date);
        
        // simulate update
        getMockUserDao().expect("update", user);
        
        // add request parameters
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        addRequestParameter("lastName", "Osbourne");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        
        // make post call
        doPost();
    }
    
    @Test
    public void testEditEmptyFirstName() {
        
        // create user
        LocalDate date = LocalDate.now();
        addRequestParameter("id", "666");
        addRequestParameter("lastName", "Osbourne");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        
        // try to find the error attibute and extract its value
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    @Test
    public void testEditEmptyLastName() {
        
        // create user
        LocalDate date = LocalDate.now();
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        
        // try to find the error attibute and extract its value
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    @Test
    public void testEditEmptyDate() {
        
        // create user
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        addRequestParameter("lastName", "Osbourne");
        addRequestParameter("okButton", "Ok");
        doPost();
        
        // try to find the error attibute and extract its value
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    @Test
    public void testEditInvalidDate() {
        
        // create user
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        addRequestParameter("lastName", "Osbourne");
        addRequestParameter("date", "42");
        addRequestParameter("okButton", "Ok");
        doPost();
        
        // try to find the error attibute and extract its value
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
}
