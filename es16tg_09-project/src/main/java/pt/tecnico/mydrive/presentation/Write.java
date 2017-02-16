package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.WriteFileService;

public class Write extends MdCommand {
	
	public Write(Shell sh) { 
		
		super(sh, "write", "write new file content"); 
		
	}
	
	@Override
    public void execute(String[] args) {
	if (args.length != 2)
	    throw new RuntimeException("<absolute or relative path to a file> <text>");
	
	else{
		
	    new WriteFileService(((MdShell)shell()).getToken(), args[0],args[1].substring(args[1].lastIndexOf('/') + 1)).execute();
	    // VERIFICAR VALORES NOS ARGS
    	}
	}  
}