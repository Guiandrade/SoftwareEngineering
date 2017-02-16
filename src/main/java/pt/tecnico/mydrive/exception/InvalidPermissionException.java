package pt.tecnico.mydrive.exception;

public class InvalidPermissionException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private String _conflictingPermission;

    public InvalidPermissionException(String conflictingPermission) {
        _conflictingPermission = conflictingPermission;
    }

    public String getconflictingEntry() {
        return _conflictingPermission;
    }

    @Override
    public String getMessage() {
        return "Your permission " + _conflictingPermission + " is invalid for this use. ";
    }
}