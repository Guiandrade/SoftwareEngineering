package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt to create user from XLM is made with an user element without a username tag
 */

public class NoUsernameException extends MyDriveException {

    private static final long serialVersionUID = 1L;


    public NoUsernameException() {
    }

    @Override
    public String getMessage() {
        return "The username attribute does not exist";
    }
}

