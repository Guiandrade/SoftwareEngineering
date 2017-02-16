package pt.tecnico.mydrive.service;



import pt.tecnico.mydrive.domain.File;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.FileDoesNotExistException;
import pt.tecnico.mydrive.exception.MyDriveException;

public class DeleteFileService extends MyDriveService {
	
	private Login login;
	private String fileName;
	private User user;
	
	public DeleteFileService(long token, String fileName){
		
		this.login  = getMyDrive().getLoginByToken(token);
		this.fileName = fileName;
		this.user = login.getLoggedUser();
	}
	public User getUser() {
		return user;
	}

	@Override
	protected void dispatch() throws MyDriveException, FileDoesNotExistException{
		
			File f = user.getFileByName(fileName);
		
				if(f == null)
					throw new FileDoesNotExistException(fileName);
				System.out.print("FIZ FIZ FIZ"+f.getName());
				f.remove();
				
		
	}
}
