package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.mydrive.domain.EnvironmentVariable;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.WrongCredentialsException;

public class AddEnvironmentVariableTest extends AbstractServiceTest {

	private MyDrive md;
	private User user1;
	private Login login;
	private EnvironmentVariable var1;
	private EnvironmentVariable var2;

	@Override
	protected void populate() {

		md = MyDrive.getInstance();
		user1 = new User(md,"user1", "user1", "pass1234567", "rwxd----", "/home");
		login = new Login(MyDriveService.getMyDrive(), user1.getUserId(), "pass1234567");
		var1 = new EnvironmentVariable (login,"Goncalo","/home/goncalo");
		var2 = new EnvironmentVariable (login,"Teste","/home/teste");		
	}

	@Test
	public void successAddEnvironmentVariable() {

		final String name = "Teste123";
		final String value = "/home/teste123";
		AddEnvironmentVariableService service = new AddEnvironmentVariableService (login.getToken(), name,value);
		service.execute();
		String result = service.result(name);
		String expected = "/home/teste123"; 

		assertEquals(expected, result);
	}


	@Test
	public void successChangeValue() {

		final String name = "Goncalo";
		final String value = "/home/goncalo2";
		AddEnvironmentVariableService service = new AddEnvironmentVariableService (login.getToken(), name,value);
		service.execute();
		String result = service.result(name);
		String expected = "/home/goncalo2"; 

		assertEquals(expected, result);
	}

	@Test
	public void checkListSize() {

		final String name = "Goncalo";
		final String value = "/home/goncalo2";
		AddEnvironmentVariableService service = new AddEnvironmentVariableService (login.getToken(), name,value);
		service.execute();
		int result = service.getSize();
		int expected = 2; 

		assertEquals(expected, result);
	}
	@Test
	public void checkListSize2() {

		final String name = "YOLO";
		final String value = "/home/yolo";
		AddEnvironmentVariableService service = new AddEnvironmentVariableService (login.getToken(), name,value);
		service.execute();
		int result = service.getSize();
		int expected = 3; 

		assertEquals(expected, result);
	}

	@Test(expected = WrongCredentialsException.class)                          //Tem de disparar a excepcçao porque o token não é um inteiro positivo
	public void invalidFileCreationWithInvalidToken() {

		final Login login = new Login(MyDriveService.getMyDrive(),user1.getUserId(),"wrongcredentials");
		final String name = "YOLO";
		final String value = "/home/yolo";
		

		AddEnvironmentVariableService service = new AddEnvironmentVariableService (login.getToken(), name,value);
		service.execute();
	}
}


