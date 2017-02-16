/*package pt.tecnico.mydrive;


import java.io.IOException;
import java.io.PrintStream;
import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

import pt.tecnico.mydrive.domain.*;


public class Main {
	static final Logger log = LogManager.getRootLogger();

	public static void main(String[] args) throws IOException {
		System.out.println("*** Welcome to the MyDrive application! ***");
		try {
			setup(args); 
			delete("/usr/local");
			print();
			xmlPrint();
		} finally { FenixFramework.shutdown(); }
	}

	@Atomic
	public static void init() { 
		log.trace("Init: " + FenixFramework.getDomainRoot());
		MyDrive.getInstance().cleanup();
	}

	@Atomic
	public static void setup(String[] args) { 
		log.trace("Setup: " + FenixFramework.getDomainRoot());
		MyDrive md = MyDrive.getInstance();
		
			if (md.getUserSet().isEmpty()){
				
				SuperUser superuser = new SuperUser(md);
				Guest guest = new Guest(md);
				User usr1 = new User(md,"usr","usr","pass","rwxd----","/home");
				Directory barra = new Directory( "barra","rwxd----");
				
				Directory home = new Directory(superuser,barra,"home","rwxd----","/");
				new Directory(guest,home,"guest","rwxd---","/home");
				Directory root = new Directory(home, "root","rwxd----","/home");
				Directory usr = new Directory(usr1,barra,"usr","rwxd----","/");
				Directory local = new Directory(usr1,usr,"local","rwxd----","/usr");
				Directory bin = new Directory (usr1,local,"bin","rwxd----","/usr/local");
				Directory readme = new Directory(usr1,home,"README","rwxd----","/home");
				PlainFile plain1 = new PlainFile(usr1,"README","/home/README",md.listUsers());
		
			}			

		}

		//for (String s: args) xmlScan(new File(s)); 

	@Atomic
	public static void print() {
		log.trace("Print: " + FenixFramework.getDomainRoot());
		MyDrive md = MyDrive.getInstance();

		for (User user: md.getUserSet()) {
			System.out.println("The name of user " + user.getUserName());
		}
	}


	@Atomic
	public static void delete(String Pathname) { 
		log.trace("Init: " + FenixFramework.getDomainRoot());
		MyDrive.getInstance().deleteFiles(Pathname);
		System.out.println("AFTER REMOVE FILES: " + MyDrive.getInstance().getUserById("root").getFileSet().size());
	}

	@Atomic
	public static void deletePlainFile(String Pathname) { 
		log.trace("Init: " + FenixFramework.getDomainRoot());
		MyDrive.getInstance().removePlainFile(Pathname);
	}

	@Atomic
	public static void xmlPrint() {
		log.trace("xmlPrint: " + FenixFramework.getDomainRoot());
		Document doc = MyDrive.getInstance().xmlExport();
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		try { 
			xmlOutput.output(doc, new PrintStream(System.out));
		} catch (IOException e) { 
			System.out.println(e); }
	}

	@Atomic
	public static void xmlScan(File file) {
		log.trace("xmlScan: " + FenixFramework.getDomainRoot());
		MyDrive md = MyDrive.getInstance();
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = (Document)builder.build(file);
			Element element = document.getRootElement();
			md.xmlImport(element);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

} */
