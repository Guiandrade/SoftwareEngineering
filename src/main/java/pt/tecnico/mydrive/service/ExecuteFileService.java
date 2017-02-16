package pt.tecnico.mydrive.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.File;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.exception.MyDriveException;

public class ExecuteFileService extends MyDriveService {
	
	private Login login;
	private String filePath;
	private String[] args;
	private Map<String, App> associations;
	
	public ExecuteFileService(long token, String filePath, String args) {
		this.login  = getMyDrive().getLoginByToken(token);
		this.filePath = filePath;
		this.args = processStringIntoArray(args);
	}
	
	public ExecuteFileService(long token, String filePath) {
		this.login  = getMyDrive().getLoginByToken(token);
		this.filePath = filePath;
	}

	public String[] processStringIntoArray(String str) {
		String str1 = str.substring(1, str.length()-1);
		return str1.split(",");
	}
	
	@Override
	protected void dispatch() throws MyDriveException {
		
		File file = MyDrive.getInstance().getFileByLinkPath(filePath);
		
		if (file.getFileType().equals("app")){
			
			String path = MyDrive.getInstance().getFileContent(filePath);
			executePath(path);
		} else if (file.getFileType().equals("plain")){
			PlainFile pf = (PlainFile) file;
			String[] pfContentLines = pf.getFileContent().split("\\n");
			for (int i = 0; i < pfContentLines.length; i++) {
				String[] pfContentSplit = pfContentLines[i].split("\\s+");
				String pfPath = pfContentSplit[0];
				String pfMethodPath = MyDrive.getInstance().getFileContent(pfPath);
				args = processStringIntoArray(pfContentSplit[1]);
				executePath(pfMethodPath);
			}
		} else if (file.getFileType().equals("link")) {
			Link lnk = (Link) file;
			String path = lnk.getLinkedFileContent();
			executePath(path);
		}
	}
	
	public void executePath(String path) {
		String[] pathParts = path.split("\\.");
    	String forKlazz = "";
    	
    	for (int i = 0; i < pathParts.length -1; i++) {
    		if (i != 0){
    			forKlazz += '.' + pathParts[i];
    		}
    		else forKlazz += pathParts[i];
    	}
    	Class<?> klazz;
		try {
			klazz = Class.forName(forKlazz);
			
			if (args != null) {
				Method m = klazz.getMethod(pathParts[pathParts.length-1], args.getClass());
				m.invoke(null,(Object) args);
			} else {
				Method m = klazz.getMethod(pathParts[pathParts.length-1]);
				m.invoke(null);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/* For the Mockup */
	public final Map<String, App> result()
	{
		return associations ;
	}
}
