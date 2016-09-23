package com.anamakarevich.usermanagement;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UserTest extends TestCase{
	private User user;
	private Date dateOfBirthd;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1941, Calendar.MAY,24);
		dateOfBirthd = calendar.getTime();
	}
	
	@Test 
	public void testGetFullName() {
		user.setFirstName("Bob");
		user.setLastName("Dylan");
		assertEquals("Dylan, Bob",  user.getFullName());
	}

	@Test 
	public void testGetAge() {
		user.setDateOfBirthd(dateOfBirthd);
		assertEquals(2016-1941, user.getAge());
	}
}
