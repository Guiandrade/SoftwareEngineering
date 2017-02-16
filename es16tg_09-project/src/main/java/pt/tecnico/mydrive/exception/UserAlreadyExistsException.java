package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to create a duplicate user.
 */

public class UserAlreadyExistsException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingUser;

    public UserAlreadyExistsException(String conflictingUser) {
        _conflictingUser = conflictingUser;
    }

    public String getConflictingUser() {
        return _conflictingUser;
    }

    @Override
    public String getMessage() {
        return "This username " + _conflictingUser + " is already being used";
    }
}