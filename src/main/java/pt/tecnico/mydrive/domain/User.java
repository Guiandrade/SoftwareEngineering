package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.FileAlreadyExistsException;
import pt.tecnico.mydrive.exception.ImportDocumentException;
import pt.tecnico.mydrive.exception.NoUsernameException;
import java.io.UnsupportedEncodingException;



public class User extends User_Base {

	private final static String DEFAULT_PERMISSIONS = "rwxd----";

	public User(){
		super();
	}
	
	public User(MyDrive md, String userId, String userName, String password, String userPermissionsMask, String homeDir) {
		super();
		setMyDrive(md);
		setUserId(userId);
		setUserName(userName);
		setPassword(password);
		setUserPermissionsMask(userPermissionsMask);
		setHomeDir(homeDir);
		
	}

	public User(MyDrive md,String username) {
		super();
		setUserId(username);
		setMyDrive(md);
		setUserPermissionsMask(DEFAULT_PERMISSIONS);
	}
	
	public User(MyDrive md,String username,String homeDir,String pass) {
		super();
		setUserId(username);
		setPassword(pass);
		setHomeDir(homeDir);
		setMyDrive(md);
		setUserPermissionsMask(DEFAULT_PERMISSIONS);
	}
	
	public String getUserType() {
		return "user";
	}
	
	@Override
	public void setMyDrive(MyDrive md) {
		if (md == null)
			super.setMyDrive(null);
		else{
			md.addUser(this);
		}
	}
	
	@Override
    public void addFile(File fileToBeAdded) throws FileAlreadyExistsException {
        if (hasFile(fileToBeAdded.getName(),fileToBeAdded.getPath()))
            throw new FileAlreadyExistsException(fileToBeAdded.getName());

        super.addFile(fileToBeAdded);
    }
	
	public File getFileByName(String name) {
        for (File file: getFileSet())
            if (file.getName().equals(name))
                return file;
        return null;
    }
	
	public boolean hasFile(String fileName,String filePath){
        for (File file: getFileSet()){
            if (file.getName().equals(fileName) && file.getPath().equals(filePath)){
            	return true;
            }
        }    
        return false;
	}
	
	public void remove() {
        for (File f: getFileSet())
            f.remove();
        setMyDrive(null);
        deleteDomainObject();
    }

	
    public void removeFile(String Pathname) {	 
		for (File file: getFileSet()){
			if((file.getPath()+"/"+file.getName()).equals(Pathname)){
				if(file.getFileType().equals("dir")){
					Directory d = (Directory) file;
					
					if(d.getFileSet().size()==0)
						file.remove(this);
				
				else{
					for(File file1: d.getFileSet()){
						
					removeFile(file1.getPath()+"/"+file1.getName());
					}
					removeFile(file.getPath()+"/"+file.getName());
				}
					
				}
				else
					file.remove(this);
		}
			
	}
    }

	public Element xmlExport() {
		Element element = new Element("user");
		element.setAttribute("username", getUserId());


		Element passwordElement = new Element("password");
		Element nameElement = new Element ("name");
		Element homeElement = new Element ("home");
		Element maskElement = new Element ("mask");

		element.addContent(passwordElement);
		element.addContent(nameElement);
		element.addContent(homeElement);
		element.addContent(maskElement);


		passwordElement.addContent(getPassword());
		nameElement.addContent(getUserName());
		homeElement.addContent(getHomeDir());
		maskElement.addContent(getUserPermissionsMask());
		return element;
	}
	
	public void verifyXmlImport(MyDrive md,Element userNode) {
		String username = userNode.getAttribute("username").getValue();
		if (username.equals("root")){
			return; // Ignora user root
		}
		User user = md.getUserById(username);
		if (user == null) {// Does not exist
			this.xmlImport(userNode);
		}
		else{
			deleteDomainObject();
			return;  // Ignora entrada incorreta
		}


	}
	

	public void xmlImport(Element userElement) throws ImportDocumentException {
		try {
			MyDrive md= MyDrive.getInstance();
			String userId= new String(userElement.getAttribute("username").getValue().getBytes("UTF-8"));
			String defaultPath="/home";
			String mask;
			if (userElement.getAttribute("username") == null) {
				throw new NoUsernameException();
			}
			else{
				setUserId(userId);
			}

			if (userElement.getChildText("password") == null) {
				setPassword(this.getUserId());
			}
			else{
				String password= new String(userElement.getChildText("password").getBytes("UTF-8"));
				setPassword(password);
			}


			if (userElement.getChildText("name") == null) {
				setUserName(this.getUserId());
			}
			else{
				String name = new String(userElement.getChildText("name").getBytes("UTF-8"));
				setUserName(name);	
			}			

			if (userElement.getChildText("home") == null) {
				setHomeDir(defaultPath);

			}
			else{
				String homeDir=new String(userElement.getChildText("home").getBytes("UTF-8"));
				setHomeDir(homeDir);
			}

			if (userElement.getChildText("mask") == null) {
				setUserPermissionsMask(DEFAULT_PERMISSIONS);
				mask=DEFAULT_PERMISSIONS;
			}
			else{
				mask= new String(userElement.getChildText("mask").getBytes("UTF-8"));
				setUserPermissionsMask(mask);
			}

			Directory home = (Directory) md.getUserById("root").getFileByName("home");
			md.addUser(this);
			new Directory (this,home, userId, mask, defaultPath); //adiciona dir ao novo User
			

		} catch (UnsupportedEncodingException e) {
			throw new ImportDocumentException();
		}


	}




}
