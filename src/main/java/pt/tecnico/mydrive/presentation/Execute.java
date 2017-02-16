package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.ExecuteFileService;

public class Execute extends MdCommand {

	private long token;

	public Execute(Shell sh) { 
		super(sh, "do", "execute file");
		token=((MdShell)sh).getToken();
	}

	@Override
	public void execute(String[] args) {

		if (args.length < 1)
			throw new RuntimeException("USAGE: " + name() + "<path> [<args>]");
		if (args.length > 1)
			new ExecuteFileService(token, args[0], args[1]).execute();
		else
			new ExecuteFileService(token, args[0]).execute();
	}



}