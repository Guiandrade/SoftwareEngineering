package pt.tecnico.mydrive.service;

import org.jdom2.Document;

import pt.tecnico.mydrive.exception.MyDriveException;

public class ImportMyDriveService extends MyDriveService {
	private final Document doc;

	public ImportMyDriveService(Document doc) {
		this.doc = doc;
	}

	@Override
	protected void dispatch() throws MyDriveException {
		getMyDrive().xmlImport(doc.getRootElement());
		
	}

}