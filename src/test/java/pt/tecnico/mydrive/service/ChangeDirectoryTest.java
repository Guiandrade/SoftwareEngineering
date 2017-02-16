package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.IsNotDirException;
import pt.tecnico.mydrive.exception.PathDoesNotExistException;
import pt.tecnico.mydrive.exception.PathTooLongException;

public class ChangeDirectoryTest extends AbstractServiceTest {
	
	private User u;
	private Login lg;
	private MyDrive md;
	private Directory teste1, documents, teste2, documents1;
	private PlainFile file1;
	
	@Override
	protected void populate() {
		MyDrive.getInstance();
		md = MyDrive.getInstance();
		u = new User(md, "teste1", "/home/teste1", "abc12345678");
		Directory home = md.getToDirectory("/home");
		teste1 = new Directory (u,home,"teste1","rwxd----","/home");
		documents = new Directory (u,teste1,"documents","rwxd----","/home/teste1");
		teste2 = new Directory (u,home,"teste2","rwxd----","/home");
		documents1 = new Directory (u,teste2,"documents1","rwxd----","/home/teste2");
		lg = new Login(md, "teste1", "abc12345678");
		file1 = new PlainFile(u, "file1", "/home/teste1/documents", "ola");
	}
	
	@Test
	public void changeDirTest1() throws Exception {
		long token = (long) lg.getToken();
		String path = ".";
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
		
		String new_current_dir = service.result();
		String expected = "/home/teste1"; 
		
		assertEquals(expected, new_current_dir);
	}

	@Test
	public void changeDirTest2() throws Exception {
		long token = (long) lg.getToken();
		String path = "..";
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
		
		String new_current_dir = service.result();
		String expected = "/home"; 
		
		assertEquals(expected, new_current_dir);
	}
	
	@Test
	public void changeDirTest3() throws Exception {
		// testa caminho relativo
		long token = (long) lg.getToken();
		String path = "/documents";
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
		
		String new_current_dir = service.result();
		String expected = "/home/teste1/documents";
		
		assertEquals(expected, new_current_dir);
	}
	
	@Test
	public void changeDirTest4() throws Exception {
		//testa caminho relativo
		long token = (long) lg.getToken();
		String path = "/home/teste2/documents1";
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
		
		String new_current_dir = service.result();
		String expected = "/home/teste2/documents1";
		
		assertEquals(expected, new_current_dir);
	}
	
	@Test (expected = IsNotDirException.class)
	public void changeDirTest5() throws Exception {
		
		long token = (long) lg.getToken();
		String path = "/documents/file1";
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
		
		service.result();
	}
	
	@Test (expected = PathDoesNotExistException.class)
	public void changeDirTest6() throws Exception {
		
		long token = (long) lg.getToken();
		String path = "/documents/file3";
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
		
		service.result();
	}
	
	@Test
	public void changeDirTest7() throws Exception {
		
		long token = (long) lg.getToken();
		String path = "/";
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
		path = "..";
		service = new ChangeDirectoryService(token,path);
		service.execute();
		String new_current_dir = service.result();
		String expected = "/";
		
		assertEquals(expected, new_current_dir);
	}
	
	@Test (expected = PathTooLongException.class)
	public void changeDirTest8() throws Exception {
		
		long token = (long) lg.getToken();
		String path = "";
		for(int i=0; i<1025; i++){
			path = path + "/";
		}
		ChangeDirectoryService service = new ChangeDirectoryService(token,path);
		service.execute();
	}
}
