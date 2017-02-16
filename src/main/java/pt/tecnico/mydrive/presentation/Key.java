package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.service.LoginUserService;

public class Key extends MdCommand {
	
	public Key(Shell sh) {
		super(sh, "token", "prints token");
	}

	@Override
	public void execute(String[] args) {
		if ( args.length == 1 )
		{
			//new LoginUserService(args[0],"null").execute();
			// Qual é que o argumento da password? Ou devem ser feitas alterações ao serviço?
			long tkn = ((MdShell)shell()).getTokenByUser(args[0]);
			((MdShell)shell()).setToken(tkn);
		}
		if ( args.length >= 0 && args.length < 2)
		{
			long tkn = ((MdShell)shell()).getToken();
			println(Long.toString(tkn));
			println(((MdShell)shell()).getUserByToken(tkn));
		}
		else
		{
			throw new RuntimeException("USAGE: "+name()+" [<username>]");
		}
	}
}