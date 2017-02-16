package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.InvalidContentException;
import pt.tecnico.mydrive.exception.InvalidFileNameException;

public class Link extends Link_Base {

	public Link() {
		super();
	}

	public Link(User user, String name, String filePermissionMask, String path, String fileContent) {
		super();
		setUser(user);
		setFilePermissionMask(filePermissionMask);
		setFilesContent(fileContent);
		addCounter();
		setName(name);
		setPath(path);
		setLink(path+'/'+name);
		setLastEditDate(new DateTime());
	}
	
public Link(User owner, String name, Directory parentDir, String fileContent, String currentDir){
		
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
				}
		else{
			throw new InvalidContentException("Invalid content");
		}
	}


	public String getLinkedFileContent() {
		String content = "";
		String linkContent = this.getFileContent();
		
		File file = MyDrive.getInstance().getFileByLinkPath(linkContent);
		if (file.getFileType().equals("link")) {
			Link lnk = (Link) file;
			return lnk.getLinkedFileContent();
		} else {
			PlainFile pf = (PlainFile) file;
			return pf.getFileContent();
		}
	
	}

	@Override
	public String getFileType(){
		return "link";
	}

    	public Element xmlExport() {
	     Element element = super.xmlExport();
	    
	     Element valueElement = new Element ("value");
	     
	     element.addContent(valueElement);
	    
	     valueElement.addContent(getFileContent());
	     return element;
	 }
	 
	public void verifyXmlImport(MyDrive md,Element appNode) {
		String content = appNode.getChildText("value");

		if (setup(md,appNode) == false){
			setFileContent(content);
			xmlImport(appNode);
		}
		else{
			this.deleteDomainObject();
		}

	}
}
