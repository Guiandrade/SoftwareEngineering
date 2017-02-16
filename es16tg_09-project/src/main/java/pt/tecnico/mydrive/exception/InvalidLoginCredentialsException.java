package pt.tecnico.mydrive.exception;

public class InvalidLoginCredentialsException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflicting;

    public InvalidLoginCredentialsException(String conflicting) {
        _conflicting = conflicting;
    }

    public String getConflictingDir() {
        return _conflicting;
    }

    @Override
    public String getMessage() {
        return "The login " + _conflicting + " is not valid";
    }
}