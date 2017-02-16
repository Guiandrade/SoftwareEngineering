package pt.tecnico.mydrive.service;



import pt.tecnico.mydrive.domain.File;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.MyDriveException;

public class WriteFileService extends MyDriveService {
	private String fileName;
	private String newFileContent;
	private Login login;
	private User user;
	
	public WriteFileService(long token, String fileName, String newFileContent) {
		login = getMyDrive().getLoginByToken(token);
		this.fileName = fileName;
		this.newFileContent = newFileContent;
		user = login.getLoggedUser();
	}
	@Override
	protected void dispatch() throws MyDriveException {
		for (File file : MyDrive.getInstance().getFileSet()) {
			if (file.getName() == fileName) {
				PlainFile pf = (PlainFile) user.getFileByName(fileName);
				pf.setFileContent(newFileContent);
			}
		}
	}
}
