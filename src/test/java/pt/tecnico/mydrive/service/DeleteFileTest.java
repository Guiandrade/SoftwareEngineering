package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.FileDoesNotExistException;
import pt.tecnico.mydrive.exception.WrongCredentialsException;

public class DeleteFileTest extends AbstractServiceTest {
	private MyDrive md;
	private Directory dir;
	private Directory dir1;
	private PlainFile plain;
	private App app;
	private Link lnk;
	private User user1;
	
	@Override
	protected void populate() {	
		md = MyDrive.getInstance();
		user1 = new User(md,"user1", "user1", "pass1234567", "rwxd----", "/home");
		dir = new Directory(user1,md.getToDirectory("/home"),"dirTest","rwxd----","/home");
		dir1 = new Directory(user1,dir,"dirTest2","rwxd----","/home/dirTest");
		plain = new PlainFile(user1, "plainTest", "/home", "Plain teste");
		app = new App(user1, "applTest", "rwxd----", "/home", "App teste");
		lnk = new Link(user1, "linkTest","rxwd----","/home","Link teste");
	}
	
	

	
	@Test
	public void successRemovePlain() {
		Login login = new Login(MyDriveService.getMyDrive(), user1.getUserId(), "pass1234567");
		final String fileName = "plainTest";
		DeleteFileService service = new DeleteFileService(login.getToken(), fileName);
		service.execute();
		assertEquals(service.getUser().getFileSet().contains(fileName),false);
	}
	
	@Test
	public void successRemoveDir() {
		Login login = new Login(MyDriveService.getMyDrive(), user1.getUserId(), "pass1234567");
		final String fileName = "dirTest2";
		final long token = login.getToken();
		DeleteFileService service = new DeleteFileService(token, fileName);
		service.execute();
		assertEquals(service.getUser().getFileSet().contains(fileName),false);

	}
	@Test
	public void successRemoveDirWithChilds() {
		Login login = new Login(MyDriveService.getMyDrive(), user1.getUserId(), "pass1234567");
		final String fileName = "dirTest";
		final long token = login.getToken();
		DeleteFileService service = new DeleteFileService(token, fileName);
		service.execute();
		assertEquals(service.getUser().getFileSet().contains(fileName),false);

	}
	@Test(expected = WrongCredentialsException.class)                          //Tem de disparar a excepcçao porque o token não é um inteiro positivo
	 public void invalidFileCreationWithInvalidToken() {
		 
		 final Login login = new Login(MyDriveService.getMyDrive(),user1.getUserId(),"wrongcredentials");
		 final String fileName = "dirTest";
		 final long token = login.getToken();
		 
		 DeleteFileService service = new DeleteFileService(token, fileName);
		 service.execute();
		 }
	
	@Test(expected = FileDoesNotExistException.class)
	public void removeInvalidFile() {
		Login login = new Login(MyDriveService.getMyDrive(), user1.getUserId(), "pass1234567");
		final String fileName = "home";
		DeleteFileService service = new DeleteFileService(login.getToken(), fileName);
		service.execute();
		
	}

	@Test(expected = FileDoesNotExistException.class)
	public void invalidFile() {
		Login login = new Login(MyDriveService.getMyDrive(), user1.getUserId(), "pass1234567");
		final String fileName = "test12";
		DeleteFileService service = new DeleteFileService(login.getToken(), fileName);
		service.execute();
	}



}
