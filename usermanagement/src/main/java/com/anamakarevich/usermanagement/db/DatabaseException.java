package com.anamakarevich.usermanagement.db;

public class DatabaseException extends Exception {

    public DatabaseException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public DatabaseException() {
        super();
    }

}
