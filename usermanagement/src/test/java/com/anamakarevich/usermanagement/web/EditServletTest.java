/**
 * 
 */
package com.anamakarevich.usermanagement.web;


import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.User;

/**
 * @author ana_makarevich
 *
 */
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
        LocalDate date = LocalDate.now();
        User user = new User(666L, "Ozzy", "Osbourne", date);
        getMockUserDao().expect("update", user);
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        addRequestParameter("lastName", "Osbourne");
        // TODO: check the date formats: this thing can blow up!
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
    }
    
    @Test
    public void testEditEmptyFirstName() {
        LocalDate date = LocalDate.now();
        addRequestParameter("id", "666");
        addRequestParameter("lastName", "Osbourne");
        // TODO: check the date formats: this thing can blow up!
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    @Test
    public void testEditEmptyLastName() {
        LocalDate date = LocalDate.now();
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        // TODO: check the date formats: this thing can blow up!
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    @Test
    public void testEditEmptyDate() {
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        addRequestParameter("lastName", "Osbourne");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    @Test
    public void testEditInvalidDate() {
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "Ozzy");
        addRequestParameter("lastName", "Osbourne");
        addRequestParameter("date", "42");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
}
