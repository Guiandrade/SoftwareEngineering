package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;

public class ListDirectoryTest extends AbstractServiceTest {

	private User u;
	private Login log;
	private MyDrive md;
	private Directory teste1, ist, es, documents;
	private PlainFile benfica;
	
	@Override
	protected void populate() {
		
		md = MyDrive.getInstance();
		u = new User(md, "teste1", "/home/teste1", "abc12345678");
		log = new Login(md, "teste1", "abc12345678");		
		Directory home = md.getToDirectory("/home");
		teste1 = new Directory (u,home,"teste1","rwxd----","/home");
		new Directory (u,teste1,"documents","rwxd----","/home/teste1");
		new Directory (u,teste1,"ist","rwxd----","/home/teste1");
		new Directory (u,teste1,"es","rwxd----","/home/teste1");
		new PlainFile(u, "benfica", "/home/teste1", "Da-me o 35");
		new Link(u, "xpto","rwxd----", "/home/teste1", "teste");
		
	}

	@Test(expected=Exception.class)
	public void listDirsTest0() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("List with 7 Contacts", 7, service.result().size());
	}
	
	@Test
	public void listDirsTest1() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("List with 7 Contacts", 7, service.result().size());
	}
	
	
	@Test
	public void listDirsTest2() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		 assertEquals("Link content is", "teste", service.result().get(6).content());
	}
	
	@Test
	public void listDirsTest3() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("First name is .", ".", service.result().get(0).getName());
	}
	
	@Test
	public void listDirsTest4() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("6th name is ist", "ist", service.result().get(5).getName());
	}
	
	@Test
	public void listDirsTest5() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("The Permissions of documents are", "rwxd----", service.result().get(3).getFilePermissionMask());
	}
	
	@Test
	public void listDirsTest6() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("The Owner of .. (/home) is root", md.getUserById("root"), service.result().get(1).owner());
	}
	
	@Test
	public void listDirsTest7() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("The type of benfica is plain file", "plain", service.result().get(2).fileType());
	}
	
	@Test
	public void listDirsTest8() throws Exception {
		
		long token = (long) log.getToken();
		ListDirectoryService service = new ListDirectoryService(token);
		service.execute();
		
		assertEquals("The type of es is directory", "dir", service.result().get(4).fileType());
	}
}
