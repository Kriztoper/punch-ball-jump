package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Timer;

import network.entities.GameClient;
import network.entities.GameServer;
import views.GameFrame;
import views.GamePanel;
import views.MenuPanel;
import views.PromptRolePanel;

public class GameController {

	private GameFrame gameFrame;
	private GameManager gameManager;
	private Timer timer;
	
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
		
		menuPanel.getTwoPlayersButton().
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					gameFrame.setCurrentPanel("promptRolePanel");
			}		
		});
		
		
		promptRolePanel.getServerButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("gamePanel");
				gameManager = new GameManager(
						new GameServer());
				gameManager.start();
			}
		});
		
		promptRolePanel.getClientButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InetAddress serverIPAddress = 
						promptRolePanel.promptServerIPAddress();
				gameFrame.setCurrentPanel("gamePanel");
				gameManager = new GameManager(
						new GameClient(serverIPAddress));
				gameManager.start();
			}
		});
		
		promptRolePanel.getBackButton().
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					gameFrame.setCurrentPanel("menuPanel");
			}		
		});
		
		promptRolePanel.getPlayButton().
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					gameFrame.setCurrentPanel("countdownPanel");
			}
		});
		
	}
}
