package com.anamakarevich.usermanagement;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UserTest extends TestCase{
    
    private User user;
    
    private LocalDate dateOfBirthd;

    @Before
    protected void setUp() throws Exception {
        
        super.setUp();
        
        user = new User();
        
        dateOfBirthd = LocalDate.of(1941, 5, 24); 
                
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
        
        int correctAnswer = LocalDate.now().getYear()-dateOfBirthd.getYear();
        
        assertEquals(correctAnswer, user.getAge());
        
        }


}
