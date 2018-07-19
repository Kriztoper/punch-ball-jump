package network.entities;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import controllers.GameManager;
import punchballjump.Board;
import views.GameFrame;

public class GameClient implements PeerInterface {

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private static final int PORT = 2048;
	private GameFrame gameFrame;
	private boolean isGameOver;
	private Board board;
	
	public GameClient(InetAddress serverIPAddress, GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		accessServer(serverIPAddress);
		
	}
	
	public void accessServer(InetAddress serverIPAddress) {
		try {
			socket = new Socket(serverIPAddress, PORT);
			isGameOver = false;

			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		 	ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			
		 	while (!isGameOver) {
		 		System.out.println("Send object to server");
		 		outputStream.writeObject(new ClientData(KeyEvent.VK_0));
		 		ServerData serverData = (ServerData) inputStream.readObject();
		 		System.out.println("Ball: " + "(" + serverData.getBallX() + ", " + serverData.getBallY() + ")");
		 	}
		 	
		} catch (IOException e) {
			System.out.println("Can't connect to server!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
