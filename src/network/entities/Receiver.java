package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;

import controllers.GameManager;

public class Receiver extends Thread {
	
	private ObjectInputStream inputStream;
	private boolean terminate;
	private GameManager gameManager;
	
	public Receiver(ObjectInputStream inputStream, GameManager gameManager) {
		this.inputStream = inputStream;
		this.gameManager = gameManager;
		terminate = false;
	}
	
	@Override
	public void run() {
		int i = 0;
		while (i++ < 5) {//!terminate) {
			try {
				Message message = 
						(Message) inputStream.readObject();
			
				System.out.println(message);
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		gameManager.setIsPlaying(false);
	}
	
	public void terminate() {
		terminate = true;
	}
}
