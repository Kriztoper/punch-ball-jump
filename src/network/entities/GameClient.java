package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class GameClient implements PeerInterface {

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private static final int PORT = 2048;
	
	public GameClient(InetAddress serverIPAddress) {
		accessServer(serverIPAddress);
	}
	
	public void accessServer(InetAddress serverIPAddress) {
		try {
			socket = new Socket(serverIPAddress, PORT);

			outputStream = new ObjectOutputStream(
					socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(
					socket.getInputStream());

			Receiver receiver = new Receiver(inputStream);
			receiver.start();
			
			Sender sender = new Sender(outputStream);
			sender.start();
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
