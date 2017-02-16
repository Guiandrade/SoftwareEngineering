package pt.tecnico.mydrive.service;

import org.apache.commons.lang.ArrayUtils;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.MyDriveException;

public class ChangeDirectoryService extends MyDriveService {

	private String path;
	private Login lg;
	
	public ChangeDirectoryService(long token, String path_given){
		path = path_given;
		lg = getMyDrive().getLoginByToken(token);
	}

	@Override
    public final void dispatch() throws MyDriveException {
		
		String path_pretendido = path;
		getMyDrive().checkPathSize(path);

		//trata do caso de . -> imprime o nome do current dir

		if (path_pretendido.equals(".")){
			lg.setCurrentDir(lg.getCurrentDir());
			return;
		}

		//trata do caso dos .. -> ve qual e o current dir, tenta achar o anterior e muda para la
		
		String [] path_split = getMyDrive().separatePath(path); //path dado pelo user dividido
		String [] curDir_split = getMyDrive().separatePath(lg.getCurrentDir()); //path do current dir dividido
		if (path_pretendido.equals("..")){
			if(curDir_split.length != 0){
				
				curDir_split = (String[]) ArrayUtils.removeElement(curDir_split, curDir_split[curDir_split.length-1]);
				String new_current_dir_path = "";
				
				for (String file: curDir_split){
					if (file.equals("")){
						continue;
					}
					else{
						new_current_dir_path = new_current_dir_path + "/" + file;
					}
				}
				lg.setCurrentDir(new_current_dir_path);
				return;
			}
			else{
				lg.setCurrentDir(lg.getCurrentDir());
				return;
			}
		}
		if (path_split.length == 0){
			Directory new_current_dir = getMyDrive().getToDirectory(path);
			lg.setCurrentDir(new_current_dir.getPath());
			return;
		}
		
		// trata do caso em que o user da um camiho absoluto
		if ((path_split[0].equals("")) && (path_split[1].equals("home"))==false){
			path_pretendido = lg.getCurrentDir() + path;
			Directory new_current_dir = getMyDrive().getToDirectory(path_pretendido);
			lg.setCurrentDir(new_current_dir.getPath() + "/" + new_current_dir.getName());
		}
		
		else{
			Directory new_current_dir = getMyDrive().getToDirectory(path_pretendido);
			lg.setCurrentDir(new_current_dir.getPath() + "/" + new_current_dir.getName());
		}
    }

    public final String result(){
    	return lg.getCurrentDir();
    }
}