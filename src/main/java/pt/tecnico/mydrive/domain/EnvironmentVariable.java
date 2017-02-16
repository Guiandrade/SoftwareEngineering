package pt.tecnico.mydrive.domain;

public class EnvironmentVariable extends EnvironmentVariable_Base {
    
    public EnvironmentVariable() {
        super();
        
    }
    public EnvironmentVariable(String name, String value){
    	setName(name);
    	setValue(value);
    	
    	
    }
    public EnvironmentVariable(Login login,String name, String value){
    	setName(name);
    	setValue(value);
    	setLogin(login);
    	
    }
    
   @Override
    public void setLogin(Login l) {
    	
    	l.addEnvironmentVar	(this); 
    	
    	}

    
}
