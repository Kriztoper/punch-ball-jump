package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import network.entities.GameClient;
import network.entities.GameServer;
import views.GameFrame;
import views.GamePanel;
import views.MenuPanel;
import views.PromptRolePanel;

public class GameController {

	private GameFrame gameFrame;
	private GameManager gameManager;
	
	public GameController(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		addButtonListeners();
	}
	
	public void start() {
		gameFrame.show();
	}
	
	public void addButtonListeners() {
		MenuPanel menuPanel = gameFrame.getMenuPanel();
		GamePanel gamePanel = gameFrame.getGamePanel();
		PromptRolePanel promptRolePanel = gameFrame.getPromptRolePanel();
		
		menuPanel.getPlayButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("promptRolePanel");
			}
		});
		
		promptRolePanel.getServerButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("gamePanel");
				GameServer gameServer = new GameServer(gameManager);
				gamePanel.add(gameServer.getBoard());
			}
		});
		
		promptRolePanel.getClientButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InetAddress serverIPAddress = 
						promptRolePanel.promptServerIPAddress();
				gameFrame.setCurrentPanel("gamePanel");
				GameClient gameClient = new GameClient(serverIPAddress, gameManager);
			}
		});
	}
}
