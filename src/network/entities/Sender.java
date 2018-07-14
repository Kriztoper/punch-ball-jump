package network.entities;

import java.io.IOException;
import java.io.ObjectOutputStream;

import controllers.GameManager;

public class Sender extends Thread {

	public ObjectOutputStream outputStream;
	private boolean terminate;
	private GameManager gameManager;
	
	public Sender(ObjectOutputStream outputStream, GameManager gameManager) {
		this.outputStream = outputStream;
		this.gameManager = gameManager;
		terminate = false;
	}
	
	@Override
	public void run() {
		int i = 0;
		while (i++ < 5) {//!terminate) {
			try {
				outputStream.writeObject(new Message());
			} catch (IOException e) {
				System.out.println("Can't send object!");
				e.printStackTrace();
			}
		}
		gameManager.setIsPlaying(false);
	}
	
	public void terminate() {
		terminate = true;
	}
}
