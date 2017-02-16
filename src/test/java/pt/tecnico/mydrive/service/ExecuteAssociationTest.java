package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;

@RunWith(JMockit.class)
public class ExecuteAssociationTest extends AbstractServiceTest {
	
	private static final Map<String,App> associations = new HashMap<String, App>() ;
	private static PlainFile pf1 ;
	private static PlainFile pf2 ;
	private static App app1 ;
	private static App app2 ;
	
	@Override
	protected void populate() {
		MyDrive md = MyDrive.getInstance();
		pf1 = new PlainFile(md.getUserById("root"), "mockupTest1.doc", "home/mock1.doc", "MockUpPlainFileTestWord");
		//pf2 = new PlainFile(md.getUserById("root"), "mockupTest2.xls", "home/mock2.xls", "MockUpPlainFileTestExcel");
		app1 = new App(md.getUserById("root"), "PowerPoint", md.getToDirectory("/home"), "MockUpTestPowerpoint", null);
		//app2 = new App(md.getUserById("root"), "Excel", md.getToDirectory("/home"), "MockUpTestExcel", null);
		
		associations.put(".doc", app1);
	}
	
	@Test
	public void success() throws Exception {
		
		new MockUp<ExecuteFileService>(){
			@Mock
			public Map<String, App> result()
			{
				return associations ;
			}
			
			/* Simulate empty service */
			@Mock
			public void dispatch()
			{
				
			}
		};
		
		ExecuteFileService efs = new ExecuteFileService(0, null); 	// Arguments should be specified?
		efs.dispatch();
		assertTrue( efs.result().containsValue(app1));
	}
	
	/* Testar o invoke? */
	
	/* TO DO: Create exception?
	@Test (expected = NoAppAssociatedException.class)
	public void noAppAssociated()
	{
		
	} */
}
