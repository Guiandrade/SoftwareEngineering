package pt.tecnico.mydrive.exception;

public class InvalidContentException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingContent;

    public InvalidContentException(String conflictingContent) {
        _conflictingContent = conflictingContent;
    }

    public String getconflictingContent() {
        return _conflictingContent;
    }

    @Override
    public String getMessage() {
        return "This content " + _conflictingContent+ " is invalid.";
    }
}