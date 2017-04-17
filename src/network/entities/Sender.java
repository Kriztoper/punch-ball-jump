package network.entities;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sender extends Thread {

	public ObjectOutputStream outputStream;
	private boolean terminate;
	
	public Sender(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
		terminate = false;
	}
	
	@Override
	public void run() {
		while (!terminate) {
			try {
				outputStream.writeObject(new Message());
			} catch (IOException e) {
				System.out.println("Can't send object!");
				e.printStackTrace();
			}
		}
	}
}
