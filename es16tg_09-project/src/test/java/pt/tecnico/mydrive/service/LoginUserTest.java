package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import org.junit.Test;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.WrongCredentialsException;
import pt.tecnico.mydrive.exception.ShortPasswordException;

public class LoginUserTest extends AbstractServiceTest{
	private MyDrive md;
	private User user1;

	@Override
	protected void populate() {
		md = MyDrive.getInstance();
		user1 = new User(md, "ut1","/home","pass2312");
	}


	@Test
	public void success() {
		final String userId = "ut1";
		final String pass = "pass2312";

		
		LoginUserService service = new LoginUserService(userId,pass);

		service.execute();
		long token = service.result();

		// check if login was created
		assertTrue("login was not created",LoginUserService.getMyDrive().getLoginByToken(token).checkSession());
	}

	@Test(expected = WrongCredentialsException.class)
	public void  loginError(){
		final String user = "root";
		final String password = "190412345678";
		LoginUserService service = new LoginUserService(user,password);
		service.execute();
	}
	
	@Test(expected = ShortPasswordException.class)
	public void  loginPassError(){
		final String user = "root";
		final String password = "1904";
		LoginUserService service = new LoginUserService(user,password);
		service.execute();
	}



}
