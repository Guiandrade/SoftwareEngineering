package pt.tecnico.mydrive.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

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

public class ExecuteFileTest extends AbstractServiceTest {

	private App app;
	private User u;
	private Link lnk;
	private MyDrive md;
	private PlainFile plain;
	
	
	@Override
	protected void populate() {
		md = MyDrive.getInstance();
		u = new User(md,"123940", "fritz", "sdij212345678", "rwxd----", "/home");
		plain = new PlainFile(u, "plainTest", "/home", "/home/appling ['aleluia','ball']\n/home/appling ['aleluia','ball']");
		app = new App(u, "appling", "rwxd----", "/home", "pt.tecnico.mydrive.domain.MyDrive.testMethod");
		lnk = new Link(u, "link","rxwd----","/home","/home/appling");
	}
	

	@Test
	public void executeAppWithArguments() {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ExecuteFileService execAppServiceArgs = new ExecuteFileService(login.getToken(), "/home/appling", "['aleluia','ball']");
		execAppServiceArgs.execute();
		assertEquals("method executed with args", MyDrive.getInstance().getTestOutput());
	}
	
	
	@Test
	public void executeLink() {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ExecuteFileService execAppService = new ExecuteFileService(login.getToken(), "/home/link");
		execAppService.execute();
		assertEquals("method executed without args", MyDrive.getInstance().getTestOutput());
	}
	
	@Test
	public void executePlainFile() {
		Login login = new Login(md, u.getUserId(), u.getPassword());
		ExecuteFileService execAppService = new ExecuteFileService(login.getToken(), "/home/plainTest");
		execAppService.execute();
		assertEquals("method executed with args", MyDrive.getInstance().getTestOutput());
	}
	

}
