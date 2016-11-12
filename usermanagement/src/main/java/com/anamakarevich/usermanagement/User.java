package com.anamakarevich.usermanagement;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author ana_makarevich
 * User class that describes the user
 */
public class User {
	
    private Long id;
    
    private String firstName;
    private String lastName;
    
    //private Date dateOfBirthd;
    private LocalDate dateOfBirthd;
    
	public User() {
		
	}
	public User(String firstName, String lastName, LocalDate date) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirthd = date;
	}
	public User(Long id, String firstName, String lastName, LocalDate date) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirthd = date;
	}
	
    
    public Long getId() {
        return id;
        }
    
    public void setId(Long id) {
        this.id = id;
        }
    
    public String getFirstName() {
        return firstName;
        }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        }
    
    public String getLastName() {
        return lastName;
        }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
        }
    
    public LocalDate getDateOfBirthd() {
        return dateOfBirthd;
        }
    
    public void setDateOfBirthd(LocalDate dateOfBirthd) {
        this.dateOfBirthd = dateOfBirthd;
        }
    
    /**
     * Builds the full name of the person 
     * @return concatenation of last name and first name
     */
    public Object getFullName() {
        return getLastName() + ", " + getFirstName();
        }
    
    /**
     * Calculates the age of the user
     * @return the age of the user
     */
    public int getAge() {
        int currentYear = LocalDate.now().getYear();
        int year = dateOfBirthd.getYear();
        return currentYear - year;
        }
    /**
     * Compares two users
     * @param user
     * @return
     */
    public boolean equals(Object obj) {
    	if (obj == null) {
    		return false;
    	}
    	if (this == obj) {
    		return true;
    	}
    	if (this.getId() == null && ((User) obj).getId() == null) {
    		return true;
    	}
    	return this.getId().equals(((User)obj).getId());
    }
    public int hashCode() {
    	if (this.getId() == null) {
    		return 0;
    		}
    	return this.getId().hashCode();
    	}
    
    

}
