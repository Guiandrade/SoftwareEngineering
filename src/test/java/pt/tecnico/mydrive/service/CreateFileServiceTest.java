package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.File;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.InvalidContentException;
import pt.tecnico.mydrive.exception.InvalidFileNameException;
import pt.tecnico.mydrive.exception.WrongCredentialsException;
import pt.tecnico.mydrive.exception.PathTooLongException;


public class CreateFileServiceTest extends AbstractServiceTest{


	Login login;
	String filePermissionMask = "rwxd---";
	String fileName = "fileName";
	String content = "content";
	String dir = "dir";
	String plain = "plain";
	String app = "app";
	String link = "link";



	protected void populate(){

		MyDrive md = MyDrive.getInstance();

		User userPedro = new User(md, "PedroID", "Pedro", "password", filePermissionMask, "/home");

		Directory testDirectory = new Directory(userPedro, md.getToDirectory("/home"), fileName, filePermissionMask, "/home");
		PlainFile testPlainFile = new PlainFile(userPedro, md.getToDirectory("/home"), "PlainFile",  content ,"/home");
		Link testLink = new Link(userPedro, "linkFile", md.getToDirectory("/home"), content, "/home");
		App testApp = new App(userPedro, "appFile", md.getToDirectory("/home"), content, "/home");

	}

	private File getFiles(String userName, String fileName) {                  
		User user = MyDriveService.getMyDrive().getUserById(userName);
		return user.getFileByName(fileName);
	}


	@Test
	public void successCreateDirectory(){

		final String directory = "/home";
		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "password");
		final long token = login.getToken();


		CreateFileService createDirectoryService = new CreateFileService(token, fileName, dir);
		createDirectoryService.execute();

		// check if Directory was in fact created
		File createdDirectory = getFiles("PedroID", fileName);

		assertNotNull("Directory was not created", createdDirectory);
		assertEquals("Invalid type", "dir", createdDirectory.getFileType());

	}
	
	@Test(expected = PathTooLongException.class) 
	public void pathTooLongExceptionTest(){

		String file = null;
		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "password");
		final long token = login.getToken();

		for(int i=0; i<115; i++){
			file = file + "fileName";
		}
		
		CreateFileService dirWithLongPath = new CreateFileService(token, file, dir);
		dirWithLongPath.execute();
	}

	@Test
	public void successCreatePlainFile(){

		final String plainFile = "TestPlainFile";
		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "password");
		final long token = login.getToken();

		CreateFileService createPlainFileService = new CreateFileService(token, "PlainFileTestVersion", content, plain);
		createPlainFileService.execute();

		// check if File was in fact created
		File createdPlainFile = getFiles("PedroID", "PlainFileTestVersion");
		assertNotNull("PlainFile was not created", createdPlainFile); 
		assertEquals("Invalid type", "plain", createdPlainFile.getFileType());	 
	}

	@Test
	public void successCreateLink(){

		final String link = "TestLink";
		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "password");
		final long token = login.getToken();

		CreateFileService createLinkService = new CreateFileService(token, "linkFile", content, link);
		createLinkService.execute();

		// check if File was in fact created
		File createdLink = getFiles("PedroID", "linkFile");

		assertNotNull("Link was not created", createdLink); 
		assertEquals("Invalid type", "link", createdLink.getFileType());

	}

	@Test
	public void successCreateApp(){

		final String app = "TestApp";
		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "password");
		final long token = login.getToken();

		CreateFileService createAppService = new CreateFileService(token, "appFile", content, app);
		createAppService.execute();

		// check if File was in fact created
		File createdApp = getFiles("PedroID", "appFile");

		assertNotNull("App was not created", createdApp); 
		assertEquals("Invalid type", "app", createdApp.getFileType());	 
	}

	@Test(expected = InvalidFileNameException.class)                      //Tem de disparar a excepção pois o nome tem /
	public void invalidFileCreationWithWrongName() {

		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "password");
		final long token = login.getToken();

		CreateFileService createFileService = new CreateFileService(token, "link.", content, link);
		createFileService.execute();
	}

	@Test(expected =  WrongCredentialsException.class)                          //Tem de disparar a excepcçao porque o token não é um inteiro positivo
	public void invalidFileCreationWithInvalidToken() {

		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "pass12345678");
		final long token = login.getToken();

		CreateFileService createFileService = new  CreateFileService(token, fileName, dir);
		createFileService.execute();
	}

	@Test(expected = InvalidContentException.class)                        //Tem de disparar a excepcçao porque o conteudo é nulo
	public void invalidLinkCreationDueInvalidContent() {

		final Login login = new Login(MyDriveService.getMyDrive(),"PedroID", "password");
		final long token = login.getToken();

		CreateFileService createFileService = new CreateFileService(token, fileName, null, link);
		createFileService.execute();
	}
}