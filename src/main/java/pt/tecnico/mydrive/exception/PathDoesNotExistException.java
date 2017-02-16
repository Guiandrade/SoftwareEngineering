package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to use an unknown path.
 */

public class PathDoesNotExistException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingPath;

    public PathDoesNotExistException(String conflictingPath) {
        _conflictingPath = conflictingPath;
    }

    public String getConflictingPath() {
        return _conflictingPath;
    }

    @Override
    public String getMessage() {
        return "The path " + _conflictingPath + " does not exist";
    }
}