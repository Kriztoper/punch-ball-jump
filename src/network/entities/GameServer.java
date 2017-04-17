package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

	private ServerSocket serverSocket;
	private static final int PORT = 2048;
	private Socket client;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private boolean listening;
	
	public GameServer() {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("\nUnable to set up port!");
			System.exit(1);
			//e.printStackTrace();
		}
		listening = true;
		
		acceptClient();
	}
	
	public void acceptClient() {
		try {
			do {
				client = serverSocket.accept();
				System.out.println("New client accepted..."); // temp, delete after
				
				handleClient();
			} while (listening);
		} catch (IOException e) {
			System.out.println("Unable to accept a client!");
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
				client.close();
			} catch (IOException e) {
				System.out.println("Can't close server connection!");
				e.printStackTrace();
			}
			
		}
	}
	
	public void handleClient() {
		try {
			outputStream = new ObjectOutputStream(
					client.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(
					client.getInputStream());

			Receiver receiver = new Receiver(inputStream);
			receiver.start();
			
			Sender sender = new Sender(outputStream);
			sender.start();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
