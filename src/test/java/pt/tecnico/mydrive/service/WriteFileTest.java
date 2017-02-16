package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;


public class WriteFileTest extends AbstractServiceTest {
	private MyDrive md;
	private PlainFile plain;
	private App app;
	private Link lnk;
	private User u;
	private User notOwnerUser;
	
	
	@Override
	protected void populate() {	
		md = MyDrive.getInstance();
		u = new User(md,"123940", "fritz", "sdij2", "rwxd----", "/home");
		notOwnerUser = new User(md,"2834723", "maluko", "ssdf3", "rwxd----", "/home");
		plain = new PlainFile(u, "plainTest", "/home", "sois, sem dúvida");
		app = new App(u, "appling", "rwxd----", "/home", "app bue fixe");
		lnk = new Link(u, "link","rxwd----","/home","alo meus irmãos");
	}
	
	@Test
	public void testEnvironmentLinkContentHasBeenWritten() throws Exception {
		
		Login login = new Login(md, u, null);
		String expectedPathName = "/home/fritz/profile";
		String newContent = "/home/$USER/profile";
		
		new MockUp<EnvironmentLinksService>() {
			@Mock
			String result() { 
			  return expectedPathName;
			}
		};
		
		EnvironmentLinksService environmentLinks = new EnvironmentLinksService(newContent);
		environmentLinks.execute();
		WriteFileService service = new WriteFileService(login.getToken(), lnk.getName(), environmentLinks.result());
		service.execute();
		String lnkContentAfterService = lnk.getFileContent();
		assertEquals(lnkContentAfterService, expectedPathName);	
	}
	
	@Test
	public void testUserOwnsThisPlainFile() {
		assertTrue(u.hasFile(plain.getName(), plain.getPath()));
	}
	
	@Test
	public void testUserOwnsThisLink() {
		assertTrue(u.hasFile(lnk.getName(), lnk.getPath()));
	}
	
	@Test
	public void testUserOwnsThisApp() {
		assertTrue(u.hasFile(app.getName(), app.getPath()));
	}
	
	@Test 
	public void testUserDoesNotOwnThisApp() {
		assertFalse(notOwnerUser.hasFile(app.getName(), app.getPath()));
	}
	
	@Test 
	public void testUserDoesNotOwnThisLink() {
		assertFalse(notOwnerUser.hasFile(lnk.getName(), lnk.getPath()));
	}
	
	@Test 
	public void testUserDoesNotOwnThisPlainFile() {
		assertFalse(notOwnerUser.hasFile(plain.getName(), plain.getPath()));
	}
	
	@Test
	public void testUserHasPlain() throws Exception {		
		assertTrue(u.hasFile(plain.getName(), plain.getPath()));
	}
	
	@Test
	public void testUserHasApp() throws Exception {		
		assertTrue(u.hasFile(app.getName(), app.getPath()));
	}

	@Test
	public void testUserHasLink() throws Exception {	
		assertTrue(u.hasFile(lnk.getName(), lnk.getPath()));
	}
	
	@Test
	public void testPlainContenHasBeenWritten() throws Exception {
		Login login = new Login(md, u, null);
		String plainContent = plain.getFileContent();
		String newContent = "ora pois, belíssima!";
		WriteFileService service = new WriteFileService(login.getToken(), plain.getName(), newContent);
		service.execute();
		String plainContentAfterService = plain.getFileContent();
		assertEquals(plainContentAfterService, newContent);	
	}
	
	@Test
	public void testAppContentHasBeenWritten() throws Exception {
		Login login = new Login(md, u, null);
		String appContent = app.getFileContent();
		String newContent = "ora pois, belíssima!";
		WriteFileService service = new WriteFileService(login.getToken(), app.getName(), newContent);
		service.execute();
		String appContentAfterService = app.getFileContent();
		assertEquals(appContentAfterService, newContent);	
	}
	
	@Test
	public void testLinkContentHasBeenWritten() throws Exception {
		Login login = new Login(md, u, null);
		String linkContent = lnk.getFileContent();
		String newContent = "ora pois, belíssima!";
		WriteFileService service = new WriteFileService(login.getToken(), lnk.getName(), newContent);
		service.execute();
		String lnkContentAfterService = lnk.getFileContent();
		assertEquals(lnkContentAfterService, newContent);	
	}
		
}
