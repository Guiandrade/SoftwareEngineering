package pt.tecnico.mydrive.service;

import java.util.HashSet;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.ExportDocumentException;
import pt.tecnico.mydrive.exception.UserDoesNotExistException;

public class ExportMyDriveService extends MyDriveService {
	private Document doc;
	private Set<String> names;
	
	public ExportMyDriveService() {
		names = new HashSet<String>();
	}

	public Document result() {
		return doc;
	}

	@Override
	protected void dispatch() throws ExportDocumentException, UserDoesNotExistException {
		if (names.size() == 0)
			doc = getMyDrive().xmlExport();
		else {
			Element root = new Element("mydrive");
			doc = new Document(root);
			for (String s: names)
				root.addContent(getUser(s).xmlExport());
		}
		
	}

}
