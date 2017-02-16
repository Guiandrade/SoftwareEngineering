package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.ChangeDirectoryService;

public class ChangeWorkingDirectory extends MdCommand {
	
	private long token;

	public ChangeWorkingDirectory(Shell sh) {
		super(sh, "cwd", "change curent directory");
		token=((MdShell)sh).getToken();
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
		    throw new RuntimeException("USAGE: "+name()+" [<absolute or relative path to a file>]");
		}

		new ChangeDirectoryService(token,args[0]).execute();
	}
}