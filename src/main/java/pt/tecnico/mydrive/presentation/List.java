package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.ListDirectoryService;
import pt.tecnico.mydrive.service.dto.FileDto;

public class List extends MdCommand {

	private long token;

	public List(Shell sh) {
		super(sh,"ls","list");
		token=((MdShell)sh).getToken();
	}

	@Override
	public void execute(String[] args) {
		if (args.length == 0) {
			ListDirectoryService lds = new ListDirectoryService(token);
			lds.execute();
			for (FileDto s: lds.result())
				System.out.println(s.getName() + " " + s.owner() + " " + s.getFilePermissionMask() + " " + s.fileType());
			System.out.println("use 'ls <path>' to list a path ");
		} else {

			ListDirectoryService lds = new ListDirectoryService(token,args[0]);
			lds.execute();
			System.out.println("List of "+args[0]);
			for (FileDto s: lds.result())
				System.out.println(s.getName() + " " + s.owner() + " " + s.getFilePermissionMask() + " " + s.fileType());
			
		}
	}
}
