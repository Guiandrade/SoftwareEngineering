package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.jdom2.Element;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.ImportDocumentException;
import pt.tecnico.mydrive.exception.InvalidPermissionException;
import pt.tecnico.mydrive.exception.NoNameException;
import pt.tecnico.mydrive.exception.PathTooLongException;
import pt.tecnico.mydrive.exception.RootCannotBeDeletedException;
import pt.tecnico.mydrive.exception.UserAlreadyExistsException;

public abstract class File extends File_Base {

	private final static String DEFAULT_PERMISSIONS = "rwxd----";

	public File() {
		super();
	}

	public File(User user, String name,String filePermissionMask,String path, DateTime lastEditDate) {
		init(user, name, filePermissionMask, path, lastEditDate);
	}

	protected void init(User user, String name,String filePermissionMask,String path, DateTime lastEditDate) {
		if (checkIncorrectName(name)){
			this.remove();
		}
		setUser(user);
		addCounter();
		setName(name);
		this.setMydrive(MyDrive.getInstance());
		setFilePermissionMask(filePermissionMask);
		setLastEditDate(lastEditDate);
	}

	public File (User user, Element xml){
		xmlImport(xml);
		this.setMydrive(MyDrive.getInstance());
		setUser(user);
		setFilePermissionMask(DEFAULT_PERMISSIONS);
		addCounter();
	}
	public boolean checkIncorrectName (String fileName) {
		if ((fileName.contains("/") || fileName.contains("/0"))){
			return true; // Nao pode existir / ou /0 no nome
		}
		else{
			return false;
		}
	}
	
	public boolean checkPathSize(String path, String name) throws PathTooLongException{
		MyDrive md = MyDrive.getInstance();
		return md.checkPathSize(path + name);
	}

	public void addCounter(){
		MyDrive md = MyDrive.getInstance();
		int  newId = md.getFileCounter() + 1;
		md.setFileCounter(newId);
		setFileId(newId);
	}
	public void initFile(User user)  { 
		setUser(user);
		addCounter();
		setLastEditDate(new DateTime());
	}

	public boolean checkSpecialUser(User user){
		if (getUser().equals(user) || user.getUserId().equals("root")){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public void setUser(User user) throws UserAlreadyExistsException {
		MyDrive md = MyDrive.getInstance();
		if (user == null) {
			super.setUser(null);

			return;
		}
		md.addFile(this);
		user.addFile(this);
	}

	public void remove(User user) {

		if(this.getUser().getUserId().equals(user.getUserId()) && this.getUser().getUserId() != "root") {
			setUser(null);
			setDirectory(null);
			setMydrive(null);
			deleteDomainObject();
		}else {
			throw new RootCannotBeDeletedException(user.getUserId());
		}
	}

	public abstract void remove();

	public void removeDir(){
		setUser(null);
		setDirectory(null);
		setMydrive(null);
		deleteDomainObject();

	}


	public abstract String getFileType();

	public Element xmlExport() {
		Element element = new Element(getFileType());
		element.setAttribute("id","" + getFileId());


		Element pathElement = new Element("path");
		Element nameElement = new Element ("name");
		Element ownerElement = new Element ("owner");
		Element permElement = new Element ("perm");

		element.addContent(pathElement);
		element.addContent(nameElement);
		element.addContent(ownerElement);
		element.addContent(permElement);

		pathElement.addContent(getPath());
		nameElement.addContent(getName());
		ownerElement.addContent(getUser().getUserId());
		permElement.addContent(getFilePermissionMask());
		return element;
	}
	public boolean setup (MyDrive md,Element fileElement) {
		String path = fileElement.getChildText("path");
		String fileName = fileElement.getChildText("name");
		String owner = fileElement.getChildText("owner");
		
		if (checkIncorrectName(fileName)){
			return true;
		}

		if ( path == null){ // Colocar Default Path
			path="/home/"+owner;
		}

		if ( (path.equals("")&& fileName.equals("/") ) || (path.equals("/") && fileName.equals("home")) ){
			return true; // Nao pode ocorrer import de "/" ou "/home"
		}
		// Verificar se existe ficheiro com mesmo nome no mesmo dir
		Set<File> filesSet = md.getFileSet();
		for (File file: filesSet){

			if ( ( file.getPath().equals(path) && file.getName().equals(fileName) ) ){
				return true;
			}
		}
		setPath(path);
		setLink(path+'/'+fileName);
		return false;
	}


	public void xmlImport(Element fileElement) throws ImportDocumentException {
		String path = getPath();
		MyDrive md = MyDrive.getInstance();
		String valOwnerUsername = fileElement.getChildText("owner");
		String[] parts = md.separatePath(path + "/"); //divide o path
		String parentPath = "";
		File fileNew=null;

		parts[0] = "/";

		try {
			if (fileElement.getChildText("name") == null) {
				throw new NoNameException();
			}
			else{
				setName(new String(fileElement.getChildText("name").getBytes("UTF-8"))); 
			}           

			if (fileElement.getChildText("perm") == null) {
				setFilePermissionMask(DEFAULT_PERMISSIONS);
			}
			else{
				setFilePermissionMask(new String(fileElement.getChildText("perm").getBytes("UTF-8")));
			}

			// caso em que tem que ir verificando o path ate
			// ate ter que comecar a criar diretorios

			for( int i = 1; i < (parts.length); i++)
			{
				boolean found=false;
				if(i==1){
					parentPath="/";

				}
				else if(i==2){
					parentPath=parentPath +parts[i-1];
				}

				else{
					parentPath=parentPath + "/"+parts[i-1];
				}

				for(File file1: md.getFileSet()){

					if(file1.getPath().equals(parentPath) && file1.getName().equals(parts[i])){
						found = true;
						fileNew=file1;
						break;
					}
				}

				if (found == false && (i+1)!=(parts.length)){
					//dir no caminho dado nao existe logo criamos
					Directory dir = new Directory((Directory)fileNew,parts[i], DEFAULT_PERMISSIONS,parentPath);
					md.addFile(dir);
					fileNew= dir;
				}
			}
			setDirectory((Directory)fileNew);
			if (valOwnerUsername != null){
				User user = (md.getUserById(valOwnerUsername));
				initFile(user);	
			}
			else{
				initFile(md.getUserById("root")); // Default username
			}
		}
		catch (UnsupportedEncodingException e) {
			throw new ImportDocumentException();
		}	
	}
}
