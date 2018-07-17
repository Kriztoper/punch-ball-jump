package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import controllers.GameManager;

public class GameClient implements PeerInterface {

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private static final int PORT = 2048;
	private GameManager gameManager;
	
	public GameClient(InetAddress serverIPAddress, GameManager gameManager) {
		this.gameManager = gameManager;
		accessServer(serverIPAddress);
	}
	
	public void accessServer(InetAddress serverIPAddress) {
		try {
			socket = new Socket(serverIPAddress, PORT);

			System.out.println("Client Connected...");
			outputStream = new ObjectOutputStream(
					socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(
					socket.getInputStream());
			
			//Sender sender = new Sender(outputStream, gameManager);
			//sender.start();
			
			Receiver receiver = new Receiver(inputStream, gameManager);
			receiver.start();
			
			
			
			
			
			
			while(gameManager.isPlaying());
			receiver.terminate();
			//sender.terminate();
		} catch (IOException e) {
			System.out.println("Can't connect to server!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Can't close client...");
				e.printStackTrace();
			}
		}
	}
}
