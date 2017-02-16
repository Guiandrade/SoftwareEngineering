package pt.tecnico.mydrive.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pt.tecnico.mydrive.domain.EnvironmentVariable;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.FileDoesNotExistException;
import pt.tecnico.mydrive.exception.MyDriveException;
import pt.tecnico.mydrive.service.dto.FileDto;

public class AddEnvironmentVariableService extends MyDriveService{

	private List<EnvironmentVariable> Env;
	private Login login;
	private String name;
	private String value;


	public AddEnvironmentVariableService(long token, String name, String value){
		
		this.login  = getMyDrive().getLoginByToken(token);
		this.name = name;
		this.value = value;
	}

	public AddEnvironmentVariableService(long token, String name){
		
		this.login  = getMyDrive().getLoginByToken(token);
		this.name = name;
		this.value ="";
	}

	public AddEnvironmentVariableService(long token){
		
		this.login  = getMyDrive().getLoginByToken(token);
		this.name = "";
		this.value ="";
	}





	@Override
	protected void dispatch() throws MyDriveException, FileDoesNotExistException{
		EnvironmentVariable var = new EnvironmentVariable(name,value);
		Env = new ArrayList<EnvironmentVariable>();
		
		if (name.length()==0 ){
			
			for (EnvironmentVariable env: login.getEnvironmentVarSet())
				System.out.println(env.getName()+" = "+env.getValue());
		}
		if (value.length()==0)
			for (EnvironmentVariable env: login.getEnvironmentVarSet()){
				if (env.getName().equals(name))
					System.out.println("Value: "+env.getValue());
			}
		else{
			var.setLogin(login);
			for (EnvironmentVariable env: login.getEnvironmentVarSet()){
				
				Env.add(env);
			}
		}

	}


	public final int getSize(){
		return Env.size();
	}
	public final String result(String name){
		for (EnvironmentVariable env: login.getEnvironmentVarSet()){
			if (env.getName().equals(name))
				return env.getValue();
		}
		return name;


	}

}
