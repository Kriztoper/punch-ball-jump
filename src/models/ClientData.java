package models;

import java.io.Serializable;

public class ClientData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyPressed;
	
	
	public ClientData(int keyPressed) {
		this.keyPressed = keyPressed;
	}
	
	public int getKeyPressed() {
		return keyPressed;
	}

}
