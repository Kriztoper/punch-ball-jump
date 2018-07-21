package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import spaceninja.Commons;
import views.CreditsPanel;
import views.GameFrame;
import views.GameLevelPanel;
import views.HowToPlayPanel;
import views.MenuPanel;
import views.PromptRolePanel;

public class GameController implements Commons {

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
		HowToPlayPanel howToPlayPanel = gameFrame.getHowToPlayPanel();
		CreditsPanel creditsPanel = gameFrame.getCreditsPanel();
		PromptRolePanel promptRolePanel = gameFrame.getPromptRolePanel();
		GameLevelPanel gameLevelPanel = gameFrame.getGameLevelPanel();

		menuPanel.getTwoPlayersButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("promptRolePanel");
			}
		});

		menuPanel.getOnePlayerButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("gameLevelPanel");
			}
		});

		menuPanel.getTwoPlayersButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("promptRolePanel");
			}
		});

		menuPanel.getHowToPlayButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("howToPlayPanel");
			}
		});

		menuPanel.getCreditsButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("creditsPanel");
			}
		});

		promptRolePanel.getServerButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.startGameServer();
			}
		});

		promptRolePanel.getClientButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InetAddress serverIPAddress = promptRolePanel.promptServerIPAddress();
				gameFrame.startGameClient(serverIPAddress);
			}
		});

		promptRolePanel.getBackButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("menuPanel");
			}
		});

		howToPlayPanel.getBackButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("menuPanel");
			}
		});

		creditsPanel.getBackButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("menuPanel");
			}
		});

		promptRolePanel.getBackButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("menuPanel");
			}
		});

		gameLevelPanel.getEasyButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setDifficulty(EASY);
				gameFrame.setCurrentPanel("board");
			}
		});

		gameLevelPanel.getNormalButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setDifficulty(NORMAL);
				gameFrame.setCurrentPanel("board");
			}
		});

		gameLevelPanel.getHardButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setDifficulty(HARD);
				gameFrame.setCurrentPanel("board");
			}
		});

		gameLevelPanel.getBackButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("menuPanel");
			}
		});

	}
}
