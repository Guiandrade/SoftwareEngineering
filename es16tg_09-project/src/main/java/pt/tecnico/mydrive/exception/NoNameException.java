package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt to create file from XLM is made with an file element without a name tag
 */

public class NoNameException extends MyDriveException {

    private static final long serialVersionUID = 1L;


    public NoNameException() {
    }

    @Override
    public String getMessage() {
        return "The name attribute does not exist";
    }
}