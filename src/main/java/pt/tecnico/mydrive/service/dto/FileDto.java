package pt.tecnico.mydrive.service.dto;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.domain.User;

public class FileDto implements Comparable<FileDto> {

    private String name;
    private String filePermissionMask;
    private User owner;
    private int fileId;
    private DateTime lastEditDate;
    private String fileType;
    private String content;

    public FileDto(String name, String filePermissionMask, User owner, int fileId, DateTime lastEditDate, String fileType, String content) {
        this.name = name;
        this.filePermissionMask = filePermissionMask;
        this.owner = owner;
        this.fileId = fileId;
        this.lastEditDate = lastEditDate;
        this.fileType = fileType;
        this.content = content;
    }
    
    public FileDto(String name, String filePermissionMask, User owner, int fileId, DateTime lastEditDate, String fileType) {
        this.name = name;
        this.filePermissionMask = filePermissionMask;
        this.owner = owner;
        this.fileId = fileId;
        this.lastEditDate = lastEditDate;
        this.fileType = fileType;
    }

    public final String getName() {
        return this.name;
    }

    public final String getFilePermissionMask() {
        return this.filePermissionMask;
    }

    public final User owner() {
        return this.owner;
    }
    
    public final int fileId() {
        return this.fileId;
    }
    
    public final DateTime lastEditDate() {
        return this.lastEditDate;
    }
    
    public final String fileType() {
        return this.fileType;
    }
    
    public final String content() {
        return this.content;
    }

	@Override
	public int compareTo(FileDto o) {
		return getName().compareTo(o.getName());
	}
}