package com.anamakarevich.usermanagement;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ana_makarevich
 * User class that describes the user
 */
public class User {

    private Long id;
    
    private String firstName;
    private String lastName;
    
    private Date dateOfBirthd;
    
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
    
    public Date getDateOfBirthd() {
        return dateOfBirthd;
        }
    
    public void setDateOfBirthd(Date dateOfBirthd) {
        this.dateOfBirthd = dateOfBirthd;
        }
    
    /**
     * 
     * @return concatenation of last name and first name
     */
    public Object getFullName() {
        return getLastName() + ", " + getFirstName();
        }
    
    /**
     * 
     * @return difference between the current year and the day of birth of the user
     */
    public int getAge() {
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        
        calendar.setTime(getDateOfBirthd());
        int year = calendar.get(Calendar.YEAR);
        
        return currentYear - year;
        
        }

}
