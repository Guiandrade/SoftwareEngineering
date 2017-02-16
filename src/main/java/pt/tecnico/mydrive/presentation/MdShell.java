package pt.tecnico.mydrive.presentation;

import java.util.HashMap;
import java.util.Map;

import pt.tecnico.mydrive.service.LoginUserService;

public class MdShell extends Shell {

	private long currentToken ;
	private Map<String,Long> logins = new HashMap<String,Long>();
	// Command Login should add usrs and tokens to this hashmap (addLogin) 

	public static void main(String[] args) throws Exception {
		MdShell sh = new MdShell();
		sh.execute();
	}

	public MdShell() {
		super("MyDrive"); 
		setup();
		new Login(this);
		new ChangeWorkingDirectory(this); 
		new List(this);
		new Execute(this);
		new Write(this);
		new Environment(this);
		new Key(this);
	}

	public void setup(){
		LoginUserService loginGuest = new LoginUserService("nobody","");
		loginGuest.execute();
		long tokenGuest = loginGuest.result();
		setToken(tokenGuest);
		addLogin("nobody",tokenGuest);
		
		LoginUserService loginRoot = new LoginUserService("root","***");
		loginRoot.execute();
		long tokenRoot = loginRoot.result();
		setToken(tokenRoot);
		addLogin("root",tokenRoot);
	}
	
	public void setToken(long tkn)
	{
		currentToken = tkn ;
	}

	public long getToken()
	{
		return currentToken ;
	}

	public boolean usrIsLogged(String usr)
	{
		if( logins.containsKey(usr) )
			return true ;
		else
			return false;
	}

	public void addLogin(String usr,long tkn)
	{
		logins.put(usr, tkn);
		setToken(tkn);
	}

	public long getTokenByUser(String usr)
	{
		return logins.get(usr);
	}

	public String getUserByToken(long tkn)
	{
		for(String key : logins.keySet()){
			if(logins.get(key).equals(tkn)){
				return key; //return the first found
			}
		}
		return null ;
	}
}