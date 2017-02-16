package pt.tecnico.mydrive.system;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.mydrive.domain.MyDrive;
import pt.tecnico.mydrive.service.AbstractServiceTest ;
import pt.tecnico.mydrive.presentation.*;

public class SystemTest extends AbstractServiceTest {

    private MdShell sh;

    protected void populate() {
        sh = new MdShell();
    }

    @Test
    public void success() {
    	new Login(sh).execute(new String[] { "puser","12345678" } );
    	new Execute(sh).execute(new String[] {"/home/applingz"} );
        new ChangeWorkingDirectory(sh).execute(new String[] { "/home" } );
        new List(sh).execute(new String[] { "/home" } );      
        new Write(sh).execute(new String[] { "/home/README", "novoTexto" } );
        new Key(sh).execute(new String[] {"puser" } );
        /*new Environment(sh).execute(new String[] { "" } );
        */
    }
}