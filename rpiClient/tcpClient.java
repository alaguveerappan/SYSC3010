import java.io.*;
import java.net.*;

public class tcpClient {
	// Global variables
	private Socket socket = null;
	private boolean connection = false;
	
	private String clientPath = "/home/pi/Desktop/camera/";
	private String serverPath = "/home/pi/Desktop/camera/";
	
	private FileEvent fileEvent = null;
	private ObjectOutputStream outStream = null;
	
	// IP address of the server on the other Raspberry Pi 
	private String address = "192.168.43.200"; 
	// This is the port that is used to send and receive 
	// data between the server and the client
	private int port = 4051;

	public tcpClient(String name) {
		clientPath = clientPath + name;
	}

	// Make a Connection with Server before starting to send anything

	public void makeConnection() {
		// make a socket, output stream, and try connecting to the server
		while (!connection) {
			try {
				socket = new Socket(address, port);
				outStream = new ObjectOutputStream(socket.getOutputStream());
				connection = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Connection is made and ready to transfer the file
	public void transferFile() {
		// create file event and set client and server path
		fileEvent = new FileEvent();
		fileEvent.setClientPath(clientPath);
		fileEvent.setServerPath(serverPath);
		
		// get client name and set filename
		File file = new File(clientPath);
		String name = clientPath.substring(clientPath.lastIndexOf("/") + 1, 
						clientPath.length());
		fileEvent.setFilename(name);
		
		// checks if the file exists in the path mentioned or sets valid to No
		if (file.isFile()) {
			//creates input stream setup the data in byte arrays
			DataInputStream inStream = null;
			try {
				inStream = new DataInputStream(new FileInputStream(file));
				long length = (int) file.length();
				byte[] byteArray = new byte[(int) length];
				int start = 0;
				int last = 0;
				int rest = inStream.read(byteArray, start, 
											byteArray.length - start);
				while (start < byteArray.length && (last = rest) >= 0) {
					start = start + last;
				}
				fileEvent.setFileSize(length);
				fileEvent.setFileData(byteArray);
				fileEvent.setValid("Yes");
			} catch (Exception e) {
				e.printStackTrace();
				fileEvent.setValid("No");
			}
		} else {
			System.out.println("Path does not exist.");
			fileEvent.setValid("No");
		}
		// Start sending the data byte array
		try {
			outStream.writeObject(fileEvent);
			System.out.println("Done...");
			Thread.sleep(5000);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		tcpClient client = new tcpClient(args[0]);
		client.makeConnection();
		client.transferFile();
	}
}

