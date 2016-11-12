package com.anamakarevich.usermanagement.web;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.User;

import javax.servlet.http.HttpServlet;

public class BrowseServletTest extends MockServletTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}
	
	@Test
	public void testBrowse() {
		User user = new User(666L, "Ozzy", "Osbourne", LocalDate.now());
		List list = Collections.singletonList(user);
		getMockUserDao().expectAndReturn("findAll", list);
		doGet();
		Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotNull("Could not find collection of users in session", collection);
		assertSame(list,collection);
	}

}
