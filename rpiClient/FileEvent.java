import java.io.*;

public class FileEvent implements Serializable {
	// Global variables

	private String filename;
	private long fileSize;
	private byte[] fileData;
	
	private String serverPath;
	private String clientPath;
	
	private String valid;
	
	// Sender and receiver need to have the same serial Version UID 
	// if not the transfer will not happen
	private static final long serialVersionUID = 42L;
	
	public FileEvent() {
	
	}

	// ------- SETTERS -------
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setFileSize(long size) {
		this.fileSize = size;
	}
	
	public void setFileData(byte[] data) {
		this.fileData = data;
	}
	
	public void setServerPath(String path) {
		this.serverPath = path;
	}
	
	public void setClientPath(String path) {
		this.clientPath = path;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	// ------- GETTERS -------
	public String getFilename() {
		return filename;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	
	public byte[] getFileData() {
		return fileData;
	}
	
	public String getServerPath() {
		return serverPath;
	}

	public String getClientPath() {
		return clientPath;
	}
	
	public String getValid() {
		return valid;
	}
	
}

