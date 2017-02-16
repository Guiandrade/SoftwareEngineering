package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.domain.User;

public class ImportTest extends AbstractServiceTest {
	private MyDrive md;
	private Document doc;
	private ImportMyDriveService service;
	
	private String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<myDrive>"
			+ "<user username=\"jtb\">"
			+ "<password>fernandes</password>"
			+ "<name>Joaquim Teófilo Braga</name>"
			+ "<home>/home/jtb</home>"
			+ "<mask>rwxdr-x-</mask>"
			+ "</user>"
			+ "<user username=\"mja\">"
			+ "<name>Manuel José de Arriaga</name>"
			+ "<password>Peyrelongue</password>"
			+ "</user>"
			+ "<plain id=\"3\">"
			+ "<path>/home/jtb</path>"
			+ "<name>profile</name>"
			+ "<owner>jtb</owner>"
			+ "<perm>rw-dr---</perm>"
			+ "<contents>Primeiro chefe de Estado do regime republicano (acumulando com a chefia do governo), numa capacidade provisória até à eleição do primeiro presidente da República.</contents>"
			+ "</plain>"
			+ "<dir id=\"4\">"
			+ "<path>/home/jtb</path>"
			+ "<name>documents</name>"
			+ "<owner>jtb</owner>"
			+ "<perm>rwxdr-x-</perm>"	
			+"</dir>"
			+ "</myDrive>";
	
	protected void populate() {
        md = MyDrive.getInstance();
    }
	
	@Before
	public void initialize() throws Exception {
		doc = new SAXBuilder().build(new StringReader(xml));
		service = new ImportMyDriveService(doc);
		service.execute();
		
		md = MyDrive.getInstance();	
	}
	
	@Test
	public void testCorrectNumberOfUsersCreated() throws Exception {
		assertEquals("created 3 Users", 6, md.getUserSet().size()); 
	}
	
	@Test
	public void testUserWithGivenUserIdWasCreated() throws Exception {
		assertTrue("created Arriaga", md.hasUserId("mja"));
	}
	
	@Test
	public void testIfPlainWasCreated() throws Exception {
		User user = md.getUserById("jtb");
		assertTrue(user.getFileByName("profile")!=null);
	}
	
	@Test
	public void testIfDirWasCreated() throws Exception {
		User user = md.getUserById("jtb");
		assertTrue(user.getFileByName("documents")!=null);
	}
	
	@After
	public void tearDown() {
		doc = null;
		service = null;
		md = null;
	}

}