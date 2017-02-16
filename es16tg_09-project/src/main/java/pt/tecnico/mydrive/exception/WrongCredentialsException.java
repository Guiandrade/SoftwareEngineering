package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt to login is made with wrong username or/and password.
 */

public class WrongCredentialsException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    public WrongCredentialsException() {
      
    }
    
    @Override
    public String getMessage() {
        return " Invalid username/password combination. ";
    }
}