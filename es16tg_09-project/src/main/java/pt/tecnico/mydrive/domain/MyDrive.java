package pt.tecnico.mydrive.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.InvalidSessionException;
import pt.tecnico.mydrive.exception.InvalidTokenException;
import pt.tecnico.mydrive.exception.IsNotDirException;
import pt.tecnico.mydrive.exception.IsNotFileException;
import pt.tecnico.mydrive.exception.PathDoesNotExistException;
import pt.tecnico.mydrive.exception.PathTooLongException;
import pt.tecnico.mydrive.exception.UserAlreadyExistsException;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;



public class MyDrive extends MyDrive_Base {


	private static String testOutput = "";

	static final Logger log = LogManager.getRootLogger();

	@Atomic
	public static MyDrive getInstance() {
		MyDrive md = FenixFramework.getDomainRoot().getMyDrive();
		if (md != null) {

			return md;
		}
		return new MyDrive();
	} 

	private MyDrive() {

		setRoot(FenixFramework.getDomainRoot());
		MyDrive md = FenixFramework.getDomainRoot().getMyDrive();
		setup(md);
	}


	public static void setup(MyDrive md) { 
		log.trace("Setup: " + FenixFramework.getDomainRoot());

		if (md.getUserSet().isEmpty()){

			SuperUser superuser = new SuperUser(md);
			Guest guest = new Guest(md);
			User usr1 = new User(md,"usr","usr","pass","rwxd----","/home");
			User presentationUser = new User(md, "puser", "puser", "12345678","rwxd----","/home");
			Directory barra = new Directory( "barra","rwxd----");
			App app = new App(presentationUser, "applingz", "rwxd----", "/home", "pt.tecnico.mydrive.domain.MyDrive.testMethod");
			Link lnkling = new Link(presentationUser, "linkTest","rxwd----","/home","/home/applingz");
			Link lnklord = new Link(presentationUser, "linkr","rxwd----","/home","/home/linkTest");
			Directory home = new Directory(superuser,barra,"home","rwxd----","/");
			PlainFile plain = new PlainFile(presentationUser, "plainTest", "/home", "/home/applingz ['aleluia','ball']\n/home/applingz ['aleluia','ball']");
			new Directory(guest,home,"guest","rwxd---","/home");
			Directory root = new Directory(home, "root","rwxd----","/home");
			Directory usr = new Directory(usr1,barra,"usr","rwxd----","/");
			Directory local = new Directory(usr1,usr,"local","rwxd----","/usr");
			Directory bin = new Directory (usr1,local,"bin","rwxd----","/usr/local");
			Directory readme = new Directory(usr1,home,"README","rwxd----","/home");
			PlainFile plain1 = new PlainFile(usr1,"README","/home/README",md.listUsers());

		}			
	}


	public boolean checkPathSize(String path) throws PathTooLongException{
		if(path.length() <= 1024){
			return true;
		}
		else{
			throw new PathTooLongException();
		}
	}

	public Directory getToDirectory(String path) throws IsNotDirException, PathDoesNotExistException{

		String[] parts = this.separatePath(path + "/");

		String parent_path = "";
		String file_name = "";
		for( int i = 1; i < (parts.length-1); i++)
		{
			parent_path = parent_path + "/" + parts[i];
		}

		if (parent_path.equals("")){
			parent_path = "/";
			file_name = "barra";
		}
		else{
			file_name = parts[(parts.length)-1];
		}

		for(File file: getFileSet()){

			if (file.getPath().equals(parent_path) 
					&& file.getName().equals(file_name)){
				if (file.getFileType().equals("dir")){
					return (Directory) file;
				}
				else{
					throw new IsNotDirException(file.getName());
				}
			}
		}

		throw new PathDoesNotExistException(path);	
	}

	public String getFileContent(String path) throws IsNotDirException, PathDoesNotExistException{

		String[] parts = this.separatePath(path + "/");

		String parent_path = "";
		for( int i = 1; i < (parts.length-1); i++)
		{
			parent_path = parent_path + "/" + parts[i];
		}

		for(File file: this.getFileSet()){
			if (file.getPath().equals(parent_path) 
					&& file.getName().equals(parts[(parts.length)-1])){

				PlainFile pf = (PlainFile) file ;
				return pf.getFileContent();	
			}
		}
		throw new PathDoesNotExistException(path);	
	}

	public File getFileByPath(String path) {
		String[] parts = this.separatePath(path + "/");

		String parent_path = "";
		for( int i = 1; i < (parts.length-1); i++)
		{
			parent_path = parent_path + "/" + parts[i];
		}
		for(File file: this.getFileSet()){
			if (file.getPath().equals(parent_path) 
					&& file.getName().equals(parts[(parts.length)-1])){
				if (file.getFileType().equals("plain")){
					PlainFile pf = (PlainFile) file ;
					return pf;
				} else if (file.getFileType().equals("app")) {
					App app = (App) file;
					return app;
				} else if (file.getFileType().equals("link")) {
					Link lnk = (Link) file;
					return lnk;
				}
				else{
					throw new IsNotFileException(file.getName());
				}
			}
		}
		throw new PathDoesNotExistException(path);	
	}

	public File getFileByLinkPath(String path) {
		for (File file: this.getFileSet()) {
			if (file.getLink().equals(path)) {
				if (file.getFileType().equals("plain")) {
					PlainFile pf = (PlainFile) file;
					return pf;
				} else if (file.getFileType().equals("app")) {
					App app = (App) file;
					return app;
				} else if (file.getFileType().equals("link")) {
					Link lnk = (Link) file;
					return lnk;
				}
				else {
					throw new IsNotFileException(file.getName());
				}
			}
		}
		throw new PathDoesNotExistException(path);
	}

	public User getUserById(String name) {
		for (User user : getUserSet()) {
			if (user.getUserId().equals(name)) {
				return user;
			}
		}
		//Excecao ou log.warn()?
		return null;
	}

	@Atomic
	public Login getLoginByToken(long token) {
		for (Login session : getLoginSet()) {
			if (session.getToken()==token) {
				return session;
			}
		}
		// Excecao ou log.warn()?
		return null;
	}	

	public boolean hasUserId(String userId) {
		return getUserById(userId) != null;
	}


	@Override
	public void addUser(User userToBeAdded) throws UserAlreadyExistsException {
		if (hasUserId(userToBeAdded.getUserId())){
			throw new UserAlreadyExistsException(userToBeAdded.getUserId());
		}
		else{
			super.addUser(userToBeAdded);
		}
	}



	public String[] separatePath(String path){
		String[] parts = path.split("/");
		return parts;		
	}

	public void cleanup() {
		for (User user: getUserSet())
			user.remove();
	}
	public void deleteFiles(String Pathaname) {
		for (User user: getUserSet()){
			user.removeFile(Pathaname);

		}
	}
	public void removePlainFile( String path )
	{
		/* Must check if path exists */
		for(User u : getUserSet())
		{
			u.removeFile( path );
		}
	}


	public String listUsers() {
		List<String> usersList = new ArrayList<String>();
		for(User user : getUserSet()){
			usersList.add(user.getUserId());
		}

		String finalString = "";

		for (String str : usersList) {
			finalString += str + '\n';
		}
		return finalString;
	}

	public void addingSession(Login login){
		Set<Login> sessions = getLoginSet();
		System.out.println("TAMANHO LOGIN SET: " + getLoginSet().size());
		setToken(login);
		if (sessions != null){ // Avoid Empty Set

			for(Login session : sessions) {
				if(session.checkSession() == false){
					sessions.remove(session); //remove expired sessions
				}
			}
			login.setTimeout();
			sessions.add(login);
		}

		else{
			throw new InvalidSessionException("Invalid Session");
		}

	}

	public void setToken(Login login) {
		Set<Login> sessions = getLoginSet();
		long tempToken = new BigInteger(64, new Random()).longValue() ;

		if (sessions != null){ // Avoid Empty Set
			for(Login session : sessions) {
				long existingToken = session.getToken();
				if (tempToken == existingToken) {
					setToken(login); //Creates new token to avoid repetitions
				}
			} 
			login.setToken(tempToken);
		}
		else{

			throw new InvalidTokenException("invalid Token");
		}

	}


	public Document xmlExport() {
		Element element = new Element("myDrive");
		Document doc = new Document(element);

		for (User u: getUserSet())
			if(!(u.getUserId().equals("root")))
				element.addContent(u.xmlExport());

		for (File f: getFileSet()){
			if(!(f.getName().equals("barra")) && !(f.getName().equals("home")))
				element.addContent(f.xmlExport());
		}

		return doc;
	}

	public void xmlImport(Element element) {
		//For Users

		for (Element userNode: element.getChildren("user")) {
			User user = new User();
			user.verifyXmlImport(this,userNode);
		}

		//For Directories
		for (Element dirNode: element.getChildren("dir")){
			Directory dir = new Directory();
			dir.verifyXmlImport(this,dirNode);
		}

		//For PlainFiles
		for (Element pfNode: element.getChildren("plain")){
			PlainFile pf = new PlainFile();
			pf.verifyXmlImport(this,pfNode);
		}

		//For Apps
		for (Element appNode: element.getChildren("app")){
			App app = new App();
			app.verifyXmlImport(this,appNode);
		}

		//For Links
		for (Element linkNode: element.getChildren("link")){
			Link link = new Link();
			link.verifyXmlImport(this,linkNode);
		}	
	}

	public String getTestOutput(){
		return testOutput;
	}

	public static void testMethod(String[] args) {
		System.err.println("method executed with args");
		testOutput = "method executed with args";
	}

	public static void testMethod() {
		System.err.println("method executed without args");
		testOutput = "method executed without args";
	}

}

