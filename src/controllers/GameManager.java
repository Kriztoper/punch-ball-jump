package controllers;

import network.entities.GameClient;
import network.entities.GameServer;

public class GameManager extends Thread {

	private GameServer gameServer;
	private GameClient gameClient;
	
	public GameManager(GameServer gameServer) {
		this.gameServer = gameServer;
	}
	
	public GameManager(GameClient gameClient) {
		this.gameClient = gameClient;
	}
	
	@Override
	public void run() {
		
	}
}
