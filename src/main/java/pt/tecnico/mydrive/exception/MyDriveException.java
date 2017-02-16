package pt.tecnico.mydrive.exception;

/**
 * This class represents events in unsuccessful operations.
 */

public abstract class MyDriveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MyDriveException() {
    }

    public MyDriveException(String msg) {
        super(msg);
    }
}