package com.anamakarevich.usermanagement.web;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.User;


public class BrowseServletTest extends MockServletTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}
	
	@Test
	public void testBrowse() {
	    
	    // create user
		User user = new User(666L, "Ozzy", "Osbourne", LocalDate.now());
		
		// simulate find all operation
		List<User> list = Collections.singletonList(user);
		getMockUserDao().expectAndReturn("findAll", list);
		doGet();
		
		// try to find users in simulated session
		Collection<?> collection = (Collection<?>) getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotNull("Could not find collection of users in session", collection);
		assertSame(list,collection);
	}
	
	@Test
	public void testEdit() {
	    
	    // create user
	    User user = new User(666L, "Ozzy", "Osbourne", LocalDate.now());
	    
	    // simulate update operation
	    getMockUserDao().expectAndReturn("find", 666L, user);
	    addRequestParameter("editButton", "Edit");
	    addRequestParameter("id", "666");
	    doPost();
	    
	    // try to find user in the session
	    User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
	    assertNotNull("Could not find user in session", user);
	    assertSame(user, userInSession);
	    
	}
	@Test
	public void testDelete() {
	       
	       // create user
	       User user = new User(666L, "Ozzy", "Osbourne", LocalDate.now());
	       // simulate find
	       getMockUserDao().expectAndReturn("find", 666L, user);
	       // simulate deletion
	       getMockUserDao().expect("delete", user);
	       List<User> list = new ArrayList<User>();
	       
	       // simulate find all
	       getMockUserDao().expectAndReturn("findAll", list);
	       
	       // add parameters to request
	       addRequestParameter("deleteButton", "Delete");
	       addRequestParameter("id", "666");
	       doPost();
	       
	       // extract deletion result from session
	       String deletionResult = (String) getWebMockObjectFactory().getMockSession().getAttribute("result");
	       assertNotNull("Deletion failed", deletionResult);
	       assertSame("ok", deletionResult);
	       
	       // extract users from session
	       List<User> users = (List<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
	       assertNotNull("Couldn't find users in session", users);
	       assertSame(list,users);
	}
	
	@Test
	public void testDetails() {
	    
	        // create user
	        User user = new User(666L, "Ozzy", "Osbourne", LocalDate.now());
	        getMockUserDao().expectAndReturn("find", 666L, user);
	        addRequestParameter("detailsButton", "Details");
	        addRequestParameter("id", "666");
	        doGet();
	        
	        // extract user from session
	        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
	        assertNotNull("Could not find user in session", user);
	        assertSame(user, userInSession);
	        
	}

}
