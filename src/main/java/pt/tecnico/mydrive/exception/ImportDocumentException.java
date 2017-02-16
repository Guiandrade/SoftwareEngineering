package pt.tecnico.mydrive.exception;

/**
 * Thrown when an error occurs while importing XML.
 */

public class ImportDocumentException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    public ImportDocumentException() {
        super("Error in importing entry from XML");
    }
}
