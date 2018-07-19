package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JPanel;
import javax.xml.stream.events.StartDocument;

import controllers.GameManager;
import models.Ball;
import punchballjump.Board;
import views.GameFrame;

public class GameServer implements PeerInterface{

	private static final int PORT = 2048;
	private ServerSocket serverSocket;
	private Socket client;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private boolean listening;
	private GameManager gameManager;
	private Board board;
	private GameFrame gameFrame;
	
	public GameServer(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		listening = true;
		
		try {
			serverSocket = new ServerSocket(PORT);
			client = serverSocket.accept();
			System.out.println("New client accepted...");
		} catch (IOException e) {
			System.out.println("Socket closed");
			e.printStackTrace();
		}
		
		board = new Board(gameFrame);
		gameFrame.getCardsPanel().add(board, "board");
		gameFrame.getMenuPanel().setVisible(false);
		board.setVisible(true);
		board.requestFocus();
		
		Handler handler = new Handler();
		handler.start();
		
	}
	
	private class Handler extends Thread {
		private boolean terminate;
		
		public Handler() {
			terminate = false;
		}
		
		@Override
		public void run() {
			try {
				
				if (client.isConnected()) {
					outputStream = new ObjectOutputStream(
							client.getOutputStream());
					
					inputStream = new ObjectInputStream(
							client.getInputStream());
					ServerData serverData = new ServerData();
					while (listening) {
						if (board.getPlayers()[0].getHearts() == 0 || board.getPlayers()[1].getHearts() == 0) {
							listening = false;
						}
						ClientData clientData = (ClientData) inputStream.readObject();
						
						if (clientData != null)
							System.out.println("Client pressed " + clientData.getKeyPressed());
						
						System.out.println("Ball is at (" + board.getBall().getX() + ", " + board.getBall().getY() + ")");
						
						serverData.setBallX(board.getBall().getX());
						serverData.setBallY(board.getBall().getY());
						
						outputStream.writeObject(serverData);
						outputStream.flush();

					}
				} else {
					System.out.println("Client is Disconnected...");
				}
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		public void terminate() {
			terminate = true;
		}
	}
}
