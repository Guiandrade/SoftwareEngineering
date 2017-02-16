package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to use an unknown file.
 */

public class FileDoesNotExistException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingFile;

    public FileDoesNotExistException(String conflictingFile) {
        _conflictingFile = conflictingFile;
    }

    public String getConflictingFile() {
        return _conflictingFile;
    }

    @Override
    public String getMessage() {
        return "The file " + _conflictingFile + " does not exist";
    }
}
