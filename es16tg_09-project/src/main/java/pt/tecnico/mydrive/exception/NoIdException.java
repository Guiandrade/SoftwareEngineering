package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt to create File from XLM is made with an file element without a file tag
 */

public class NoIdException extends MyDriveException {

    private static final long serialVersionUID = 1L;


    public NoIdException() {
    }

    @Override
    public String getMessage() {
        return "The username Id does not exist";
    }
}

