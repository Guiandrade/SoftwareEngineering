package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to use an unknown user.
 */

public class UserDoesNotExistException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingUser;

    public UserDoesNotExistException(String conflictingUser) {
        _conflictingUser = conflictingUser;
    }

    public String getConflictingUser() {
        return _conflictingUser;
    }

    @Override
    public String getMessage() {
        return "This username " + _conflictingUser + " does not exist";
    }
}
