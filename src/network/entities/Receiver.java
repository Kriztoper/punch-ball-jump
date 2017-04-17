package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Receiver extends Thread {
	
	private ObjectInputStream inputStream;
	private boolean terminate;
	
	public Receiver(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
		terminate = false;
	}
	
	@Override
	public void run() {
		while (!terminate) {
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
	}
}
