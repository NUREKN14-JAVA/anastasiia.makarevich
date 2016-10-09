package com.anamakarevich.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import com.anamakarevich.usermanagement.User;

class HsqldbUserDao implements UserDao {

    ConnectionFactory connectionFactory;
    
    /** getter method for the connectionFactory, can return null
     */
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
    
    /**
     * setter method for the connectionFactory
     */
    public void setConnectionFactory(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public HsqldbUserDao() {

        }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {

        this.connectionFactory = connectionFactory;
        
        }
    
   /**
    * Creates new row in the database based on the User instance
    * @param user - not null, id field must be null, 
    * @return user - id field is not null
    */
   @Override
    public User create(User user) throws DatabaseException {

       Connection connection = connectionFactory.createConnection();
       
       String insertQuerySQL = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
       
        try {
            // convert query string into prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuerySQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, Date.valueOf(user.getDateOfBirthd()));
            
            // check that we have added exactly 1 row 
            int numberOfRowsAdded = preparedStatement.executeUpdate();
            if (numberOfRowsAdded!=1) {
                throw new DatabaseException("Number of the inserted rows: " + numberOfRowsAdded); 
            }
            
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            
            if (keys.next()) {
                // take the 1st element in the returned set and set it as user's id
                //Long userID = keys.getLong(1);
                user.setId(keys.getLong(1));
            }
            
            // close all database resources and database connection
            keys.close();
            callableStatement.close();
            preparedStatement.close();
            connection.close();

            
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
        }

   /**
    * Updates the user data in the database 
    * @param user - user instance to be updated, id is not null
    */
    @Override
    public void update(User user) throws DatabaseException {
        
        // connect to database
        Connection connection = connectionFactory.createConnection();
        
        // Get id to work with
        Long id = user.getId();
        
        // create query
        String updateQuerySQL = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
        
        // convert it into a prepared statement
        PreparedStatement preparedStatement;
        
        try {
            
            preparedStatement = connection.prepareStatement(updateQuerySQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, Date.valueOf(user.getDateOfBirthd()));
            preparedStatement.setLong(4, id);
            
            // execute query and check that it worked
            int numberOfRowsUpdated = preparedStatement.executeUpdate();
            if (numberOfRowsUpdated!=1) {
                throw new DatabaseException("Number of the inserted rows: " + numberOfRowsUpdated); 
            }
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

    @Override
    public void delete(User user) throws DatabaseException {
        // TODO Auto-generated method stub

        }
    
    /**
     * 
     */
    @Override
    public User find(Long id) throws DatabaseException {
        Connection connection = connectionFactory.createConnection();
        try {
            String findQuerySQL =  "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(findQuerySQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new DatabaseException("User with id" + id + " is not found");
            }
            User user = new User();
            user.setId(resultSet.getLong(1));
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setDateOfBirthd(resultSet.getDate(4).toLocalDate());
            
            preparedStatement.close();
            connection.close();
            return user;
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        }

    /**
     * Note: can return null
     */
    @Override
    public Collection<?> findAll() throws DatabaseException {
        
        Connection connection = connectionFactory.createConnection();
        String selectQuerySQL = "SELECT id, firstname, lastname, dateofbirth FROM users";
        Collection<User> result = new LinkedList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet allUsers = statement.executeQuery(selectQuerySQL);
            while (allUsers.next()) {
                User user = new User();
                user.setId(allUsers.getLong(1));
                user.setFirstName(allUsers.getString(2));
                user.setLastName(allUsers.getString(3));
                user.setDateOfBirthd(allUsers.getDate(4).toLocalDate());
                result.add(user);
            }
            statement.close();
            connection.close();
            
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub
        return result;
        }

}
