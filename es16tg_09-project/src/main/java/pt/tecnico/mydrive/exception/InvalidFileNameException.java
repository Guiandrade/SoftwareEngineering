
package pt.tecnico.mydrive.exception;

public class InvalidFileNameException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingName;

    public InvalidFileNameException(String conflictingName) {
    	_conflictingName = conflictingName;
    }

    public String getconflictingName() {
        return _conflictingName;
    }

    @Override
    public String getMessage() {
        return "This name " + _conflictingName + " is invalid.";
    }
}