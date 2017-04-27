package controllers;

import network.entities.PeerInterface;

public class GameManager extends Thread {

	private PeerInterface gameNetworkEntity;
	
	public GameManager(PeerInterface gameNetworkEntity) {
		this.gameNetworkEntity = gameNetworkEntity;
	}
	
	@Override
	public void run() {
		
	}
}
