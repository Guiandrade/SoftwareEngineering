package pt.tecnico.mydrive.exception;

public class RootCannotBeDeletedException extends MyDriveException{
	
	
	private static final long serialVersionUID = 1L;

    private String _conflictingUser = "root";

    public RootCannotBeDeletedException(String conflictingUser) {
        _conflictingUser = conflictingUser;
    }

    public String getConflictingUser() {
        return _conflictingUser;
    }

    public String getMessage() {
        return "The user " + _conflictingUser + " cannot be deleted.";
    }

}
