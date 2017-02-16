package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Before;
import org.junit.Test;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;

public class ExportTest extends AbstractServiceTest {
	MyDrive md;
	User user1;
	PlainFile plain;
	App app;
	Link link;
	
	@Override
	protected void populate() {
		md = MyDrive.getInstance();	
		md = MyDrive.getInstance();
		user1 = new User(md, "1234235", "userTest1", "pass2312", "rwxd----", "/home");
		plain = new PlainFile(user1, "plainTest", "/home", "sois, sem dúvida");
		app = new App(user1, "appling", "rwxd----", "/home", "app bue fixe");
		link = new Link(user1, "link","rxwd----","/home","alo meus irmãos");
		
	}
	
	@Test
	public void checkIfUserIsExported() throws Exception {
		ExportMyDriveService service = new ExportMyDriveService();
		service.execute();
		Document doc = service.result();
		Element e = doc.getRootElement();
		assertEquals("exported 3 users", e.getChildren("user").size(), 4);  //2 users because this test includes all instantiated
																			//objects at Main.setup. 
	}
	
	@Test
	public void checkIfPlainFileIsExported() throws Exception {
		ExportMyDriveService service = new ExportMyDriveService();
		service.execute();
		Document doc = service.result();
		
		Element e = doc.getRootElement();
		assertEquals("exported 2 plainfiles", e.getChildren("plain").size(), 3);
		/*for (Element u : e.getChildren()) {
			System.out.println(u);
		}*/
	}
	
	@Test
	public void checkIfAppIsExported() throws Exception {
		ExportMyDriveService service = new ExportMyDriveService();
		service.execute();
		Document doc = service.result();
		
		Element e = doc.getRootElement();
		assertEquals("exported 2 apps", e.getChildren("app").size(), 2);
		/*for (Element u : e.getChildren()) {
			System.out.println(u);
		}*/
	}
	
	@Test
	public void checkIfLinkIsExported() throws Exception {
		ExportMyDriveService service = new ExportMyDriveService();
		service.execute();
		Document doc = service.result();
		
		Element e = doc.getRootElement();
		assertEquals("exported 2 links", e.getChildren("link").size(), 3);
		/*for (Element u : e.getChildren()) {
			System.out.println(u);
		}*/
	}

}
