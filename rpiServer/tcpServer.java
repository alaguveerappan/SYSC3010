import java.io.*;
import java.net.*;

public class tcpServer {
	// Global variables
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	
	private FileEvent fileEvent;
	private File file = null;

	private ObjectInputStream inStream = null;
	private FileOutputStream outStream = null;
	
	private int port = 4051;

	public tcpServer() {

	}

	// Wait until a Connection is made before doing anything else
	public void makeConnection() {
		// make a server socket, input stream and start listening
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			inStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Connection is made and ready to receive the file
	public void receiveFile() {
		try {
			// create FileEvent and check if any error occurred
			fileEvent = (FileEvent) inStream.readObject();
			if (fileEvent.getValid().equalsIgnoreCase("No")) {
				System.out.println("Some Error has occurred. Exiting!!!");
				System.exit(0);
			}
			
			// get file name and create the file
			String fileName = fileEvent.getServerPath()+fileEvent.getFilename();
			file = new File(fileName);
			
			// write the file, close the file and exit
			outStream = new FileOutputStream(file);
			outStream.write(fileEvent.getFileData());
			outStream.flush();
			outStream.close();
			
			System.out.println("File " + fileName + 
									" has been received by the server.");
			Thread.sleep(5000);
			System.exit(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		tcpServer server = new tcpServer();
		server.makeConnection();
		server.receiveFile();
	}
}

