package pt.tecnico.mydrive.service;

public class EnvironmentLinksService extends MyDriveService {
	
	
	//private List<String> names;
    private String pathName;

    public EnvironmentLinksService(String pathName) {
    	this.pathName = pathName;
    	//contact = contactName;
    }
	
	 public final void dispatch() { // throws ContactDoesNotExistException
			// TODO: mockup example
	}
	 
	 public final String result() {
	        return pathName;
	 }

}