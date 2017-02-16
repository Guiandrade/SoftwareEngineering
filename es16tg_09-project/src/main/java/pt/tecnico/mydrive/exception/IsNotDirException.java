package pt.tecnico.mydrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */

public class IsNotDirException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingEntry;

    public IsNotDirException(String conflictingEntry) {
        _conflictingEntry = conflictingEntry;
    }

    public String getconflictingEntry() {
        return _conflictingEntry;
    }

    @Override
    public String getMessage() {
        return "This entry " + _conflictingEntry + " is not a directory";
    }
}
