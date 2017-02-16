package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.exception.InvalidPermissionException;


public class CreateFileService extends MyDriveService {
	
	private Login login;
	private String fileName;
	private String content;
	private String type;
	private Directory parentDir;
	private String currentDir;
	
	MyDrive md = MyDrive.getInstance();
	
	public CreateFileService(long token, String fileName, String type){
		
		login = md.getLoginByToken(token);
		this.fileName = fileName;
		this.type = type;
		this.currentDir = login.getCurrentDir();
		this.parentDir = md.getToDirectory(currentDir);

	}
	
	public CreateFileService(long token, String fileName, String content, String type){
		
		login = md.getLoginByToken(token);
		this.fileName = fileName;
		this.content = content;
		this.type = type;
		this.currentDir = login.getCurrentDir();
		this.parentDir = md.getToDirectory(currentDir);
	
	}
	
	
	@Override
    public final void dispatch() throws InvalidPermissionException {
		/*
		System.out.println("Dir: " + parentDir.getName());
		System.out.println("Permission: " + parentDir.getFilePermissionMask());
		System.out.println("User: " + login.getLoggedUser().getUserId());
		System.out.println("Dir User: " + parentDir.getUser().getUserId());
		
		if((!login.getLoggedUser().equals(parentDir.getUser()) 
				|| (!login.getLoggedUser().getUserId().equals("root"))
		|| (!(parentDir.getFilePermissionMask().charAt(5) == 'w'))))
			throw new InvalidPermissionException(fileName);

		*/
		switch(type){
		
			case "dir":
				new Directory(login.getLoggedUser(), parentDir, fileName, currentDir);
				break;
				
			case "plain":
				new PlainFile(login.getLoggedUser(), parentDir ,fileName, content, currentDir);
				break;
				
			case "link":
				new Link(login.getLoggedUser(), fileName, parentDir, content, currentDir);
				break;
				
			case "app":
				new App(login.getLoggedUser(), fileName, parentDir, content, currentDir);
				break;
		}
	}
}