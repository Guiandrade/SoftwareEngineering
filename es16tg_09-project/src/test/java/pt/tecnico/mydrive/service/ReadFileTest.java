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

public class ReadFileTest extends AbstractServiceTest{
	private MyDrive md;
	private PlainFile plain;
	private App app;
	private Link lnk, lnk2, link;
	private User u;
	
	@Override
	protected void populate() {	
		md = MyDrive.getInstance();
		u = new User(md,"123940", "fritz", "sdij212345678", "rwxd----", "/home");
		plain = new PlainFile(u, "plainReadTest", "/home", "sois, sem dúvida");
		app = new App(u, "appling", "rwxd----", "/home", "app bue fixe");
		lnk = new Link(u, "link","rxwd----","/home","/home/plainReadTest");
		lnk2 = new Link(u, "link2","rxwd----","/home","/home/link");
		PlainFile pf = new PlainFile(u, "profile", "/home/fritz", "tiago brincando com os mocks");
	}
	
	@Test
	public void testCanReadEnvironmentLink() throws Exception {

		String expectedPathName = "/home/fritz/profile";
		
		Login login = new Login(md, u.getUserId(), u.getPassword());
		link = new Link(u, "link1","rxwd----","/home","/home/$USER/profile");
		
		new MockUp<EnvironmentLinksService>() {
			@Mock
			String result() { 
			  return expectedPathName;
			}
		};
		EnvironmentLinksService environmentLinks = new EnvironmentLinksService(link.getFileContent());
		environmentLinks.execute();
		link.setFileContent(environmentLinks.result());
		ReadFileService service = new ReadFileService(login.getToken(), link.getName());
		service.execute();
		
		assertEquals(environmentLinks.result(), expectedPathName);
	}
	
	
	@Test
	public void checkIfServiceHasCorrectUser() {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ReadFileService service = new ReadFileService(login.getToken(), plain.getName());
		service.execute();
		User uTUser = md.getUserById(service.getUserId());
		assertEquals(uTUser,u);
	}
	
	@Test
	public void testCanReadPlainFile() throws Exception {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ReadFileService service = new ReadFileService(login.getToken(), plain.getName());
		service.execute();
		String fileContent = service.getContent();
		assertEquals("sois, sem dúvida", fileContent);
	}
	
	@Test
	public void testCanReadApp() throws Exception {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ReadFileService service = new ReadFileService(login.getToken(), app.getName());
		service.execute();
		String fileContent = service.getContent();
		assertEquals("app bue fixe", fileContent);
	}
	
	@Test
	public void testCanReadLink() throws Exception {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ReadFileService service = new ReadFileService(login.getToken(), lnk.getName());
		service.execute();
		String fileContent = service.getContent();
		assertEquals("sois, sem dúvida", fileContent);
	}
	
	@Test
	public void testCanReadRecursiveLink() throws Exception {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ReadFileService service = new ReadFileService(login.getToken(), lnk2.getName());
		service.execute();
		String fileContent = service.getContent();
		assertEquals("sois, sem dúvida", fileContent);
	}
}
