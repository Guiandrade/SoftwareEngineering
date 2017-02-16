package pt.tecnico.mydrive.exception;

public class InvalidSessionException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflicting;

    public InvalidSessionException(String conflicting) {
        _conflicting = conflicting;
    }

    public String getConflictingFile() {
        return _conflicting;
    }

    @Override
    public String getMessage() {
        return "This session " + _conflicting + " is not valid";
    }
}
