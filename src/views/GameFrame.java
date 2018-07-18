package views;

import java.awt.CardLayout;
import java.awt.Color;
import java.net.InetAddress;
import java.util.Arrays;

import javax.swing.JPanel;

import network.entities.GameClient;
import network.entities.GameServer;
import punchballjump.Board;
import punchballjump.ClientBoard;

public class GameFrame {

	private Frame frame;
	private CardLayout cards;
	private JPanel cardsPanel;
	private MenuPanel menuPanel;
	private PromptRolePanel promptRolePanel;
	private HowToPlayPanel howToPlayPanel;
	private CreditsPanel creditsPanel;
	private Board board;

	public GameFrame() {
		frame = new Frame();
		initPanels();
		frame.setContentPane(cardsPanel);
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public HowToPlayPanel getHowToPlayPanel() {
		return howToPlayPanel;
	}

	public PromptRolePanel getPromptRolePanel() {
		return promptRolePanel;
	}

	public CreditsPanel getCreditsPanel() {
		return creditsPanel;
	}

	public void setCurrentPanel(String panelName) {
		if (panelName.equals("board")) {
			if (Arrays.asList(cardsPanel.getComponents()).contains(board)) {
				cardsPanel.remove(board);
			}
			Frame clientFrame = new Frame();
			ClientBoard clientBoard = new ClientBoard(this);
			clientBoard.setBackground(Color.BLACK);
			clientBoard.setVisible(true);
			// clientFrame.add(clientBoard);
			clientFrame.setContentPane(clientBoard);
			clientFrame.setVisible(true);
			board = new Board(this, clientBoard);
			board.setVisible(true);
			cardsPanel.add(board, "board");
			cards.show(cardsPanel, panelName);
			board.requestFocus();
		} else {
			cards.show(cardsPanel, panelName);
		}
		frame.repaint();
	}

	public void startGameServer() {
		GameServer gameServer = new GameServer(this);
	}

	public void startGameClient(InetAddress serverIPAddress) {

		GameClient gameClient = new GameClient(serverIPAddress, this);
	}

	public void initPanels() {
		cards = new CardLayout();

		menuPanel = new MenuPanel();

		howToPlayPanel = new HowToPlayPanel();

		creditsPanel = new CreditsPanel();

		promptRolePanel = new PromptRolePanel();

		cardsPanel = new JPanel();
		cardsPanel.setLayout(cards);

		cardsPanel.add(menuPanel, "menuPanel");
		cardsPanel.add(howToPlayPanel, "howToPlayPanel");
		cardsPanel.add(creditsPanel, "creditsPanel");
		cardsPanel.add(promptRolePanel, "promptRolePanel");
		cards.show(cardsPanel, "menuPanel");
	}

	public void show() {
		frame.setVisible(true);
	}

	public JPanel getCardsPanel() {
		return cardsPanel;
	}
}
