package pt.tecnico.mydrive.domain;


import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.InvalidContentException;
import pt.tecnico.mydrive.exception.InvalidFileNameException;

public class App extends App_Base {
    
    public App() {
        super();
    }
    
    public App(User user, String name, String filePermissionMask, String path, String fileContent) {
        super();
        setUser(user);
        addCounter();
        setName(name);
        setFilePermissionMask(filePermissionMask);
        setPath(path);
        setLink(path+'/'+name);
        setLastEditDate(new DateTime());
        setFilesContent(fileContent);
    }
    

    public App(User owner, String name, Directory parentDir, String fileContent, String currentDir){
    	
    	super();
    	
    	if((name.contains(".")) || (name.contains("/"))){
			throw new InvalidFileNameException(name);
			}
    	
    	if(fileContent != null){
    		setUser(owner);
    		setFilePermissionMask("rwxdr-x-");
    		setFilesContent(fileContent);
    		addCounter();
    		setName(name);
    		setPath(currentDir);    
    		setLink(currentDir+'/'+name);
    		setLastEditDate(new DateTime());
    		setDirectory(parentDir);
		}else{
			throw new InvalidContentException("Invalid content");
		}
	}
     @Override
	public String getFileType(){
		return "app";
	}
    
    public Element xmlExport() {
	     Element element = super.xmlExport();
	    
	     Element methodElement = new Element ("method");
	     
	     element.addContent(methodElement);
	    
	     methodElement.addContent(getFileContent());
	     return element;
	 }
    
	public void verifyXmlImport(MyDrive md,Element appNode) {
		String content = appNode.getChildText("method");
	
		if (setup(md,appNode) == false){
			setFileContent(content);
			xmlImport(appNode);
		}
		else{
			this.deleteDomainObject();
		}

	}
}

