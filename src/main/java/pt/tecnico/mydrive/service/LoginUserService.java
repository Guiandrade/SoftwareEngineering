package pt.tecnico.mydrive.service;


import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.exception.MyDriveException;
import pt.tecnico.mydrive.exception.WrongCredentialsException;

public class LoginUserService extends MyDriveService {
	private long token;
	private String username;
	private String password;
	
	
	public LoginUserService(String userId,String pass) {
		username=userId;
		password=pass;
	}
	
	public long result(){
		return token;
	}

	@Override
	protected void dispatch() throws MyDriveException,WrongCredentialsException{
		MyDrive md = MyDrive.getInstance();		
		Login login = new Login(md,username,password);
		token = login.getToken();		
	}

	

}
