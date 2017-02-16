package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to remove "." or ".."
 */

public class IllegalRemovalException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingEntry;

    public IllegalRemovalException(String conflictingEntry) {
        _conflictingEntry = conflictingEntry;
    }

    public String getConflictingEntry() {
        return _conflictingEntry;
    }

    @Override
    public String getMessage() {
        return "The entry " + _conflictingEntry + " can't be removed";
    }
}
