package com.github.gabrielpbzr.cookbook.exceptions;

/**
 * Database migration related exception
 * @author gabriel
 */
public class DatabaseMigrationException extends Exception {

    public DatabaseMigrationException() {
    }

    public DatabaseMigrationException(String message) {
        super(message);
    }

    public DatabaseMigrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseMigrationException(Throwable cause) {
        super(cause);
    }
    
}
