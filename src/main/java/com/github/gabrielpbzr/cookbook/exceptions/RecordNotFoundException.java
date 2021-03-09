package com.github.gabrielpbzr.cookbook.exceptions;

/**
 *
 * @author gabriel
 */
public class RecordNotFoundException extends Exception {

    public RecordNotFoundException() {
        super();
    }
    
    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }
}
