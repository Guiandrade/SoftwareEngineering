package pt.tecnico.mydrive.service;


import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;
import pt.tecnico.mydrive.domain.MyDrive;

public abstract class AbstractServiceTest {
	protected static final Logger log = LogManager.getRootLogger();
	
	@BeforeClass
	public static void setUpBeforeAll() throws Exception {
		MyDrive.getInstance();
	}
	
	@Before
	public void setUp() throws Exception {
		try {
			FenixFramework.getTransactionManager().begin(false);
			populate();
		} catch (WriteOnReadError | NotSupportedException | SystemException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		try {
			FenixFramework.getTransactionManager().rollback();
		} catch (IllegalStateException | SecurityException | SystemException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void populate();

}