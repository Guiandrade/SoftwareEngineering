package pt.tecnico.mydrive.domain;

public class SuperUser extends SuperUser_Base {
    
	public SuperUser(MyDrive md) {    	
		super();

		setUserId("root");
		setUserName("Super User");
		setPassword("***");
		setUserPermissionsMask("rwxdr-x-");
		setHomeDir("/home");
		setMyDrive(md);
		new Login(md,this,getHomeDir());
    }
	
	@Override
	public String getUserType() {
		return "super";
	}
	
}
	