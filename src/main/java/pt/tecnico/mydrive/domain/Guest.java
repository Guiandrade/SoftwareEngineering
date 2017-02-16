package pt.tecnico.mydrive.domain;

public class Guest extends Guest_Base {
	
	private final static String INITIAL_MASK = "rxwdr-x-";
    
    public Guest() {
        super();
    }
    
    public Guest(MyDrive md) {
		super();
		setMyDrive(md);
		setUserId("nobody");
		setUserName("Guest");
		setPassword("");
		setUserPermissionsMask(INITIAL_MASK);
		setHomeDir("/home");
		//new Login(md,this,getHomeDir()); Feito no MdShell com um serviço p guardar token
	}
    
    @Override
    public String getUserType() {
    	return "guest";
    }
}