package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.File;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.MyDriveException;

public class ReadFileService extends MyDriveService {
	private Login login;
	private String fileName;
	private User user;
	private String content;

	public ReadFileService(long token, String name) {
		this.fileName = name;
		this.login = getMyDrive().getLoginByToken(token);
		this.user = login.getLoggedUser();
	}

	@Override
	protected void dispatch() throws MyDriveException {
		for (File file : MyDrive.getInstance().getFileSet()) {
			if (file.getName() == fileName) {
				if (file.getFileType() == "plain") {
					PlainFile pf = (PlainFile) file;
					content = pf.getFileContent();
				} else if (file.getFileType().equals("app")) {
					PlainFile pf = (PlainFile) file;
					content = pf.getFileContent(); //TODO app specific behaviour
				} else if (file.getFileType().equals("link")) {
					Link lnk = (Link) file;
					content = lnk.getLinkedFileContent();			
				}
			}
		}
	}
	
	public String getContent() throws Exception {
		return content;
	}
	
	public String getUserId() {
		return user.getUserId();
	}
}
