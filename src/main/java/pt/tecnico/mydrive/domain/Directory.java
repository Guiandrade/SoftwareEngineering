package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.DirAlreadyExistsException;

import pt.tecnico.mydrive.exception.IllegalRemovalException;

import pt.tecnico.mydrive.exception.InvalidFileNameException;


public class Directory extends Directory_Base {

	public Directory (User owner, Directory parentDir, String name, String filePermissionMask, String path) throws DirAlreadyExistsException{

		init(owner, parentDir, name, filePermissionMask, path);

	}

	protected void init(User owner, Directory parentDir, String name, String filePermissionMask, String path) throws DirAlreadyExistsException{
		addCounter();
		setName(name);
		setFilePermissionMask(filePermissionMask);
		if (super.checkPathSize(path, name)){
			setPath(path);
		}
		setLink(path+'/'+name);
		setLastEditDate(new DateTime());
		setUser(owner);
		setDirectory(parentDir);
	}

	public Directory (Directory parentDir, String name,  String filePermissionMask, String path) throws DirAlreadyExistsException{

		MyDrive md = MyDrive.getInstance();
		addCounter();
		setName(name);
		if (super.checkPathSize(path, name)){
			setPath(path);
		}
		setLink(path+'/'+name);
		setFilePermissionMask(filePermissionMask);
		setUser(md.getUserById("root"));
		setLastEditDate(new DateTime());
		setDirectory(parentDir);

	}


	/* constructor for "/" */
	public Directory (String name, String filePermissionMask) throws DirAlreadyExistsException{
		addCounter();
		setName(name);
		setPath("/");
		setLink('/'+name);
		setDirectory(this);
		setFilePermissionMask(filePermissionMask);
		setLastEditDate(new DateTime());
		setUser(MyDrive.getInstance().getUserById("root"));
	}

	public Directory (Directory parentDir,String name, String filePermissionMask) throws DirAlreadyExistsException{
		addCounter();
		setName(name);
		setPath("/");
		setLink('/'+name);
		setFilePermissionMask(filePermissionMask);
		setDirectory(parentDir);
		setLastEditDate(new DateTime());
	}


	public Directory(User owner, Directory parentDir ,String name, String currentDir) throws DirAlreadyExistsException{

		super();
		if((name.contains(".")) || (name.contains("/"))){
			throw new InvalidFileNameException(name);
		}

		if(parentDir.getName().equals(name)){
			throw new DirAlreadyExistsException(name);
		}
		setUser(owner);
		addCounter();
		setName(name);
		setFilePermissionMask("rwxd--x-");
		if (super.checkPathSize(currentDir+"/"+ name, name)){
			setPath(currentDir+"/"+ name);
		}
		setLink(currentDir+'/'+name);
		setLastEditDate(new DateTime());
		setDirectory(parentDir);
	}

	public Directory(){
		super();
	}

	@Override
	public void setDirectory(Directory parentDir) {
		super.setDirectory(parentDir);
	}

	public void remove() { 

		for (File file: getFileSet()){

			if((file.getName().equals("barra")) || file.getName().equals("home"))

				throw new IllegalRemovalException(file.getName());


			else{

				Directory d = (Directory) file;
				if(d.getFileSet().size()==0)

					file.removeDir();

				else
					for(File file1: d.getFileSet())

						file1.remove();

			}

		}
	}



	public File getFileByName(String fileName){

		for (File file: this.getFileSet()){
			if(file.getName().equals(fileName)){
				return file;
			}
		}
		return null;
	}

	@Override
	public String getFileType(){
		return "dir";
	}


	public void verifyXmlImport(MyDrive md,Element dirNode) {
		if (setup(md,dirNode) == false){
			xmlImport(dirNode);
		}
		else{
			deleteDomainObject();
		}
	}
}
