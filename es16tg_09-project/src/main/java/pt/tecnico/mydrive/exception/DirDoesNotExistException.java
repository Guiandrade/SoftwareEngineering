package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to use an unknown directory.
 */

public class DirDoesNotExistException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingDir;

    public DirDoesNotExistException(String conflictingDir) {
        _conflictingDir = conflictingDir;
    }

    public String getConflictingDir() {
        return _conflictingDir;
    }

    @Override
    public String getMessage() {
        return "The directory name " + _conflictingDir + " does not exist";
    }
}
