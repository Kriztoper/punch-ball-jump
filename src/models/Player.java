package models;

public class Player {

	private String playerName;
	private int x;
	private int y;
		
	
	public Player() {
		loadImages();
	}
	
	public void loadImages() {
		
	}
	
	/* the method must return a boolean value
	   to determine if the player was able to jump
	   true for jump succes, false for jump failed. */
	public boolean jump() {
		//TODO: player must virtually jump using sprite
		
		return false;
	}
	
	/* same with jump the boolean value returned 
	   determines if the punch execution was a success. */
	public boolean punch() {
		//TODO: player must virtually punch using sprite
		return false;
	}

}
