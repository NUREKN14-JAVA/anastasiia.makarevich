package com.anamakarevich.usermanagement.db;

import java.util.Collection;

import com.anamakarevich.usermanagement.User;

public interface UserDao {

    /**
     * <p>Adds user to the database and returns a user instance with initialized id field</p>
     * <p><b>Note</b>: id field of the user parameter must be null</p>
     * @param user - new user that has not been added to database yet
     * @return User instance with initialized id field
     * @throws DatabaseException if the user was not added
     */
    User create(User user) throws DatabaseException;
    
    
    /**
     * <p>Updates the existing user data in the database</p>
     * <p><b>Note</b>: Mutates all fields in the database except for the id field<p>
     * <p><b>Note</b>: id field of the user can't be null</p>
     * @param user instance with the new data (id can't be null)
     * @throws DatabaseException if the update failed (possible reasons: 
     * id field was null, connection to database failed)
     */
    void update (User user) throws DatabaseException;
    
    /**
     * Removes existing user from the database
     * @param user existing user (id can't be null)
     * @throws DatabaseException if the deletion failed (possible reasons: 
     * id field was null, connection to database failed)
     */
    void delete(User user) throws DatabaseException;
    
    /**
     * Looks up the user with the given id in the database. If found, creates 
     * and returns the object instantiated 
     * with the retrieved data
     * @param id - positive Long (>=1), example: 1L
     * @return User instance   
     * @throws DatabaseException if the user was not found or the connection 
     * to database failed
     */
    User find(Long id) throws DatabaseException;
    
    /**
     * Selects all the users from the database and returns them as collection of users
     * @return Collection of users
     * @throws DatabaseException if the connection to database failed
     */
    Collection<?> findAll() throws DatabaseException;
    
    /**
     * Sets the connection factory to use for creating connection to the database
     * @param connectionFactory
     */
    void setConnectionFactory(ConnectionFactory connectionFactory);
}
