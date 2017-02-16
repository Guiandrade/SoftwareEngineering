package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to create a duplicate directory.
 */

public class DirAlreadyExistsException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingDir;

    public DirAlreadyExistsException(String conflictingDir) {
        _conflictingDir = conflictingDir;
    }

    public String getConflictingDir() {
        return _conflictingDir;
    }

    @Override
    public String getMessage() {
        return "The directory name " + _conflictingDir + " is already being used";
    }
}