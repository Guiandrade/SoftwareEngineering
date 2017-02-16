package pt.tecnico.mydrive.exception;

/**
 * Thrown when an error occurs while exporting XML.
 */

public class ExportDocumentException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    public ExportDocumentException() {
        super("Error in exporting entry from XML");
    }
}
