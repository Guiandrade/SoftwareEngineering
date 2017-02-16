package pt.tecnico.mydrive.domain;

import org.apache.logging.log4j.LogManager;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.FileAlreadyExistsException;
import pt.tecnico.mydrive.exception.InvalidContentException;
import pt.tecnico.mydrive.exception.InvalidFileNameException;
import pt.tecnico.mydrive.exception.InvalidPermissionException;



public class PlainFile extends PlainFile_Base {
	
	  public static final int readFlagPosition = 4;
	  public static final int writeFlagPosition = 5;
	  public static final int exeFlagPosition = 6;
	  public static final int deleteFlagPosition = 7;
	  

	public PlainFile() {
		super();
	}
	
	public PlainFile(User user, String fileName, String path, String fileContent)
	{
		init(user, fileName, path, fileContent);
	}
	
	
	protected void init(User user, String fileName, String path, String fileContent)
	{
		setFilePermissionMask("rwxdr-x-");
		setUser(user);
		setFilesContent(fileContent);
		setName(fileName);
		if (super.checkPathSize(path, fileName)){
			setPath(path);
		}
		setLink(path+'/'+fileName);
		addCounter();
		
	}

	public PlainFile (User owner, Directory parentDir, String name, String content, String currentDir) {
		
		if((name.contains(".")) || (name.contains("/"))){
			throw new InvalidFileNameException(name);
			}
		if(parentDir.getFileByName(name) != null){
			throw new FileAlreadyExistsException(name);
		}
		
		if(content != null){
			addCounter();
			setName(name);
			setFilePermissionMask("rwxdr-x-");
			setUser(owner);
			setLastEditDate(new DateTime());
			setFilesContent(content);
			setDirectory(parentDir);
			if (super.checkPathSize(currentDir, "")){
				setPath(currentDir);
			}
			setLink(currentDir+'/'+name);
			}
		else{throw new InvalidContentException("Invalid content");
		}
	}
	@Override
	public String getFileType(){
		return "plain";
	}
	
	public void setFilesContent(String content){
		if (checkPermissions(this.getUser(),writeFlagPosition) == true ) {
			super.setFileContent(content);
		}
		else{
			throw new InvalidPermissionException(this.getUser().getUserPermissionsMask());
		}
	}
	

	public String getFilesContent() { 
		if (checkPermissions(this.getUser(),readFlagPosition) == true ) {
			return super.getFileContent();
		}
		else{
			throw new InvalidPermissionException(this.getUser().getUserPermissionsMask());
		}
	}
	
	public boolean checkPermissions(User user, int flagPerm) {
		String permFile = this.getFilePermissionMask();
		String permUser = user.getUserPermissionsMask();
		char readFilePerm = permFile.charAt(flagPerm);
		char readUserPerm = permUser.charAt(flagPerm);
		char nullFlag = '-';
		
		if ( (checkSpecialUser(user)==true) || ( (readFilePerm==readUserPerm) && (readFilePerm!=nullFlag) ) ) {
			return true;
		}
		else{
			return false;
			// Lançar excecao ou log.warn ?
		}
	}

	public Element xmlExport() {
		Element element = super.xmlExport();
		if(getFileType().equals("plain")) {
			Element contentElement = new Element ("contents");

			element.addContent(contentElement);

			contentElement.addContent(getFileContent());
		}
		return element;

	}
	public void remove() {
		if (checkPermissions(this.getUser(),deleteFlagPosition) == true ) {
			setUser(null);
			setDirectory(null);
			setMydrive(null);
			deleteDomainObject();
		}
		else{
			throw new InvalidPermissionException(this.getUser().getUserPermissionsMask());
		}
	}

	public void verifyXmlImport(MyDrive md,Element fileNode) {
		String content = fileNode.getChildText("contents");

		if (setup(md,fileNode) == false){
			super.setFileContent(content);
			xmlImport(fileNode);
		}
		else{
			deleteDomainObject();
		}
	}
	
}
