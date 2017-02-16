package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to create a duplicate file.
 */

public class FileAlreadyExistsException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingFile;

    public FileAlreadyExistsException(String conflictingFile) {
        _conflictingFile = conflictingFile;
    }

    public String getConflictingFile() {
        return _conflictingFile;
    }

    @Override
    public String getMessage() {
        return "The file " + _conflictingFile + " is already exists";
    }
}