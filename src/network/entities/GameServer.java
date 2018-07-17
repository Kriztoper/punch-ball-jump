package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import controllers.GameManager;
import punchballjump.Board;

public class GameServer implements PeerInterface {

	private ServerSocket serverSocket;
	private static final int PORT = 2048;
	private Socket client;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private boolean listening;
	private GameManager gameManager;
	private Board board;
	
	public GameServer(GameManager gameManager) {
		this.gameManager = gameManager;
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
			client = serverSocket.accept();
			System.out.println("New client accepted..."); // temp, delete after
			
			handleClient();
			listening = false;
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
			
			/*Receiver receiver = new Receiver(inputStream, gameManager);
			receiver.start();
	
			//Sender sender  = new Sender(outputStream, gameManager);
			//sender.start();
			
			
			
			
			
			
			while(gameManager.isPlaying());
			receiver.terminate();*/
			//sender.terminate();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public Board getBoard() {
		return board;
	}
}
