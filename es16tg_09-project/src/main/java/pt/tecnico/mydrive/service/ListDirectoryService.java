package pt.tecnico.mydrive.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.File;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.MyDriveException;
import pt.tecnico.mydrive.service.dto.*;

public class ListDirectoryService extends MyDriveService {

	private List<FileDto> directories;
	private Login lg;
	private String dir;

	public ListDirectoryService(long token) {
		lg = getMyDrive().getLoginByToken(token);
		this.dir ="";
	}
	public ListDirectoryService(long token, String Dir) {
		lg = getMyDrive().getLoginByToken(token);
		this.dir = Dir;

	}


	@Override
	protected void dispatch() throws MyDriveException {
		
		
		if (dir.length()!=0){
			System.out.println("ENTROU");
			directories = new ArrayList<FileDto>();
			System.out.println("TAMANHO: "+directories.size());
			Directory curDir = getMyDrive().getToDirectory(dir);
			directories.add(new FileDto(".",
					curDir.getFilePermissionMask(),
					curDir.getUser(),
					curDir.getFileId(),
					curDir.getLastEditDate(),
					curDir.getFileType()));

			Directory parent = getMyDrive().getToDirectory(dir).getDirectory();
			directories.add(new FileDto("..",
					parent.getFilePermissionMask(),
					parent.getUser(),
					parent.getFileId(),
					parent.getLastEditDate(),
					parent.getFileType()));


			for(File file: getMyDrive().getFileSet()){
				if(file.getPath().equals(dir)){
					if (file.getFileType().equals("link")){
						directories.add(new FileDto(file.getName(),
								file.getFilePermissionMask(),
								file.getUser(),
								file.getFileId(),
								file.getLastEditDate(),
								file.getFileType(),
								((Link) file).getFileContent()));
						continue;
					}
					directories.add(new FileDto(file.getName(),
							file.getFilePermissionMask(),
							file.getUser(),
							file.getFileId(),
							file.getLastEditDate(),
							file.getFileType()));
				}
			}

			Collections.sort(directories);
		}
		else{
			String currentDir = lg.getCurrentDir();
			directories = new ArrayList<FileDto>();

			Directory curDir = getMyDrive().getToDirectory(currentDir);
			directories.add(new FileDto(".",
					curDir.getFilePermissionMask(),
					curDir.getUser(),
					curDir.getFileId(),
					curDir.getLastEditDate(),
					curDir.getFileType()));

			Directory parent = getMyDrive().getToDirectory(currentDir).getDirectory();
			directories.add(new FileDto("..",
					parent.getFilePermissionMask(),
					parent.getUser(),
					parent.getFileId(),
					parent.getLastEditDate(),
					parent.getFileType()));


			for(File file: getMyDrive().getFileSet()){
				if(file.getPath().equals(lg.getCurrentDir())){
					if (file.getFileType().equals("link")){
						directories.add(new FileDto(file.getName(),
								file.getFilePermissionMask(),
								file.getUser(),
								file.getFileId(),
								file.getLastEditDate(),
								file.getFileType(),
								((Link) file).getFileContent()));
						continue;
					}
					directories.add(new FileDto(file.getName(),
							file.getFilePermissionMask(),
							file.getUser(),
							file.getFileId(),
							file.getLastEditDate(),
							file.getFileType()));
				}
			}

			Collections.sort(directories);
		}
	}


	public final List<FileDto> result() {
		return directories;
	}
}