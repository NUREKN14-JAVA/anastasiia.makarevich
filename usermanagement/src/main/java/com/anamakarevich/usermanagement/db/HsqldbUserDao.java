package com.anamakarevich.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.anamakarevich.usermanagement.User;

public class HsqldbUserDao implements UserDao {

    ConnectionFactory connectionFactory;

    public HsqldbUserDao() {

        }
    
    public HsqldbUserDao(ConnectionFactory connectionFactory) {

        this.connectionFactory = connectionFactory;
        
        }

    @Override
    public User create(User user) throws DatabaseException {
        
        try {
            
            Connection connection = connectionFactory.createConnection();
            
            String insertQuerySQL = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuerySQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
            
            int numberOfRowsAdded = preparedStatement.executeUpdate();
            if (numberOfRowsAdded!=1) {
                throw new DatabaseException("Number of the inserted rows: " + numberOfRowsAdded); 
            }
            
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            
            if (keys.next()) {
                Long userID = keys.getLong(1);
                user.setId(userID);
            }
            
            keys.close();
            callableStatement.close();
            preparedStatement.close();
            connection.close();

            
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
        }

    @Override
    public void update(User user) throws DatabaseException {
        // TODO Auto-generated method stub

        }

    @Override
    public void delete(User user) throws DatabaseException {
        // TODO Auto-generated method stub

        }

    @Override
    public User find(Long id) throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
        }

    @Override
    public Collection<?> findAll() throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
        }

}
