package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.GameFrame;

public class GameController {

	private GameFrame gameFrame;
	
	public GameController(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		addButtonListeners();
	}
	
	public void start() {
		gameFrame.show();
	}
	
	public void addButtonListeners() {
		
		gameFrame.getMenuPanel().getPlayButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("promptRolePanel");
			}
		});
		
		gameFrame.getPromptRolePanel().getServerButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("gamePanel");
			}
		});
		
		gameFrame.getPromptRolePanel().getClientButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("gamePanel");
			}
		});
	}
}
