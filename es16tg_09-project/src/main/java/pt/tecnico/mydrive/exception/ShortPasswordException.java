package pt.tecnico.mydrive.exception;

public class ShortPasswordException extends MyDriveException  {

    private static final long serialVersionUID = 1L;

    public ShortPasswordException() {
      
    }
    
    @Override
    public String getMessage() {
        return " You must have at least 8 characters in your password.";
    }
}

