package controllers;

public class GameManager extends Thread {

	private boolean isPlaying;
	
	public GameManager() {
		isPlaying = false;
	}
	
	@Override
	public void run() {
		isPlaying = true;
	}

	public boolean isPlaying() {
		return isPlaying;
	}
	
	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
}
