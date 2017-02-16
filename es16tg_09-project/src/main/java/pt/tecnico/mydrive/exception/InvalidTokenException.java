package pt.tecnico.mydrive.exception;

public class InvalidTokenException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingToken;

    public InvalidTokenException(String conflictingToken) {
        _conflictingToken = conflictingToken;
    }

    public String getconflictingToken() {
        return _conflictingToken;
    }

    @Override
    public String getMessage() {
        return "This token " + _conflictingToken+ " is invalid.";
    }
}