package pt.tecnico.mydrive.exception;

/**
 * Thrown when a given path size is over 1024.
 */

public class PathTooLongException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "The path size is too long";
    }
}