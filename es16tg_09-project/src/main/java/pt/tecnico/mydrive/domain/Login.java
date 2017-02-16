	package pt.tecnico.mydrive.domain;

import org.apache.logging.log4j.*;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.WrongCredentialsException;
import pt.tecnico.mydrive.exception.ShortPasswordException;


public class Login extends Login_Base {


	private String currentDir;
	private long token;
	private User user;
	private  DateTime timeout; 
	private final static int SESSION_MILLISECONDS = 7200000;
	private final static int ROOT_SESSION_MILLISECONDS = 600000;
	private final static int GUEST_SESSION_MILLISECONDS = 2147483647;
	private final static int MIN_CHARACTERS_PASS = 8;
	private static org.apache.logging.log4j.Logger log = LogManager.getRootLogger();

	public Login() {
		super();
	}

	public Login(MyDrive md,String username,String password) throws WrongCredentialsException{
		String pass="";
		User user = md.getUserById(username);
		
		if (user != null){
			pass = user.getPassword();
		}
		
		if ((password.length() < MIN_CHARACTERS_PASS) && !(((username.equals("nobody") && password.equals(""))) || ((username.equals("root") && password.equals("***"))))){
			throw new ShortPasswordException();
		}
		if (pass.equals(password)){
			setLoggedUser(user);
			setDefaultDir();
			md.addingSession(this);
		}

		else{
			throw new WrongCredentialsException();
		}
	}


	public Login(MyDrive md,User user,String currentDir){
		setLoggedUser(user);
		setCurrentDir(currentDir);
		md.addingSession(this);
	}

	public String getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(String currentDir) {
		this.currentDir = currentDir;
	}
	
	@Override
	public void addEnvironmentVar(EnvironmentVariable e){
		for (EnvironmentVariable env: getEnvironmentVarSet()){
			if (env.getName().equals(e.getName())) {
				env.setValue(e.getValue());
				return;
			}
		}
		super.addEnvironmentVar(e);

	}

	public void setDefaultDir(){
		
		User u = this.getLoggedUser();
		String currentDir = u.getHomeDir();
		this.setCurrentDir(currentDir);
	}
	@Override
	public long getToken() {
		return token;
	}
	@Override
	public void setToken(long tok) {
		super.setToken(tok);
		token=tok;
	}


	public User getLoggedUser() {
		return user;
	}

	public void setLoggedUser(User user) {
		this.user = user;
	}

	public DateTime getTimeout() {
		return timeout;
	}

	public void setTimeout() {
		DateTime dt = new DateTime();
		if (this.getLoggedUser().getUserId().equals("root")){
			// Root has a session time of 10 min
			this.timeout = dt.plusMillis(ROOT_SESSION_MILLISECONDS);
		}
		if (this.getLoggedUser().getUserId().equals("nobody")){
			// Root has a session time of 10 min
			this.timeout = dt.plusMillis(GUEST_SESSION_MILLISECONDS);
		}
		else{
			// Users have a session time of 2 hours
			this.timeout = dt.plusMillis(SESSION_MILLISECONDS) ;
		}
	}

	public boolean checkSession(){
		DateTime actualTime = new DateTime();
		DateTime sessionTime = this.getTimeout();
		User user = this.getLoggedUser();
		

		if (( sessionTime.getMillis() <= actualTime.getMillis())){
			if (user.getUserId().equals("root")){
				// We will renew the root's token after 10 min
				new Login(getMydrive(),user,getCurrentDir());
				return true;
				
			}
			if (user.getUserId().equals("nobody")){
				// We will ensure here the infinity of Guest session
				setTimeout();
				return true;
			}
			else{
				log.warn("Invalid session!");
				return false;
			}
			
		}
		else{
			
			return true;
		}
	}

	
}
