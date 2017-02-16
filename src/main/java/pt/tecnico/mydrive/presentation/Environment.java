package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.AddEnvironmentVariableService;
import pt.tecnico.mydrive.service.ChangeDirectoryService;

public class Environment extends MdCommand {

	public Environment(Shell sh) {
		super(sh, "env", "Create or modify the environment variable");
	}

	@Override
	public void execute(String[] args) {
		if (args.length == 2) {			
			new AddEnvironmentVariableService (((MdShell)shell()).getToken(),args[0],args[1]).execute();
			
			
		}
		if (args.length == 1){
			new AddEnvironmentVariableService(((MdShell)shell()).getToken(),args[0]).execute();
			

		}
		if (args.length >= 0 && args.length < 1){
			new AddEnvironmentVariableService (((MdShell)shell()).getToken()).execute();
		
		}
	}
}
