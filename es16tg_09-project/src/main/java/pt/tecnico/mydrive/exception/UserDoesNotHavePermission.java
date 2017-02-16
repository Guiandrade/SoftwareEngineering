package pt.tecnico.mydrive.exception;

/**
 * Thrown when an attempt is made to create a duplicate directory.
 */

public class UserDoesNotHavePermission extends MyDriveException {

    private static final long serialVersionUID = 1L;


    public UserDoesNotHavePermission() {
        super();
    }

    @Override
    public String getMessage() {
        return "The user does not have permission for the action";
    }
}