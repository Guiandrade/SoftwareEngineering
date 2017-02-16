package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.LoginUserService;

public class Login extends MdCommand {

	public Login(Shell sh) {
		super(sh, "login", "log user in");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1){
			throw new RuntimeException("USAGE: "+name()+" <username> <password>");
		}
		if (args.length == 1){
			LoginUserService lus = new LoginUserService(args[0], "");
			lus.execute();
			long token = lus.result();
			((MdShell)shell()).addLogin(args[0],token);
		}
		else{
			LoginUserService lus = new LoginUserService(args[0],args[1]);
			lus.execute();
			long token = lus.result();
			((MdShell)shell()).addLogin(args[0],token);
		}
	}  
}