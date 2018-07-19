package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import views.CreditsPanel;
import views.GameFrame;
import views.HowToPlayPanel;
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
		HowToPlayPanel howToPlayPanel = gameFrame.getHowToPlayPanel();
		CreditsPanel creditsPanel = gameFrame.getCreditsPanel();
		PromptRolePanel promptRolePanel = gameFrame.getPromptRolePanel();

		menuPanel.getOnePlayerButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setCurrentPanel("board");
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
	}
}
