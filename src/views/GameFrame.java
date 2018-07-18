package views;

import java.awt.CardLayout;
import java.net.InetAddress;

import javax.swing.JPanel;

import network.entities.GameClient;
import network.entities.GameServer;
import punchballjump.Board;

public class GameFrame {

	private Frame frame;
	private CardLayout cards;
	private JPanel cardsPanel;
	private MenuPanel menuPanel;
	private PromptRolePanel promptRolePanel;
	private GamePanel gamePanel;
	private Board board;

	public GameFrame() {
		frame = new Frame();
		initPanels();
		frame.setContentPane(cardsPanel);
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public PromptRolePanel getPromptRolePanel() {
		return promptRolePanel;
	}

	public void setCurrentPanel(String panelName) {
		if (panelName.equals("board")) {
			board = new Board(this);
			cardsPanel.add(board, "board");
			menuPanel.setVisible(false);
			board.setVisible(true);
			board.requestFocus();
		} else {
			cards.show(cardsPanel, panelName);
			if (panelName.equals("menuPanel")) {
				menuPanel.setVisible(true);
			}
			if (board != null) {
				board.setVisible(false);
			}
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

		gamePanel = new GamePanel();

		promptRolePanel = new PromptRolePanel();

		cardsPanel = new JPanel();
		cardsPanel.setLayout(cards);

		cardsPanel.add(menuPanel, "menuPanel");
		cardsPanel.add(gamePanel, "gamePanel");
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
