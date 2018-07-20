package views;

import java.awt.CardLayout;
import java.net.InetAddress;
import java.util.Arrays;

import javax.swing.JPanel;

import punchballjump.Board;
import punchballjump.ClientBoard;
import punchballjump.Commons;

public class GameFrame implements Commons {

	private Frame frame;
	private CardLayout cards;
	private JPanel cardsPanel;
	private MenuPanel menuPanel;
	private PromptRolePanel promptRolePanel;
	private HowToPlayPanel howToPlayPanel;
	private CreditsPanel creditsPanel;
	private GameLevelPanel gameLevelPanel;
	private Board board;
	private ClientBoard clientBoard;

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
	
	public GameLevelPanel getGameLevelPanel() {
		return gameLevelPanel;
	}

	public void setCurrentPanel(String panelName) {
		if (panelName.equals("board")) {
			if (Arrays.asList(cardsPanel.getComponents()).contains(board)) {
				cardsPanel.remove(board);
			}
			// Frame clientFrame = new Frame();
			// ClientBoard clientBoard = new ClientBoard(this);
			// clientBoard.setBackground(Color.BLACK);
			// clientBoard.setVisible(true);
			// // clientFrame.add(clientBoard);
			// clientFrame.setContentPane(clientBoard);
			// clientFrame.setVisible(true);
			board = new Board(this, IS_COMPUTER);// , clientBoard);
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
		board = new Board(this, IS_NOT_COMPUTER);// , clientBoard);
		board.setVisible(true);
		cardsPanel.add(board, "board");
		cards.show(cardsPanel, "board");
		board.requestFocus();
		// GameServer gameServer = new GameServer(this);
	}

	public void startGameClient(InetAddress serverIPAddress) {
		clientBoard = new ClientBoard(this, serverIPAddress);
		clientBoard.setVisible(true);
		cardsPanel.add(clientBoard, "clientBoard");
		cards.show(cardsPanel, "clientBoard");
		clientBoard.requestFocus();
		// GameClient gameClient = new GameClient(serverIPAddress, this, clientBoard);
	}

	public void initPanels() {
		cards = new CardLayout();

		menuPanel = new MenuPanel();

		howToPlayPanel = new HowToPlayPanel();

		creditsPanel = new CreditsPanel();

		promptRolePanel = new PromptRolePanel();
		
		gameLevelPanel = new GameLevelPanel();
		cardsPanel = new JPanel();
		cardsPanel.setLayout(cards);

		cardsPanel.add(menuPanel, "menuPanel");
		cardsPanel.add(howToPlayPanel, "howToPlayPanel");
		cardsPanel.add(creditsPanel, "creditsPanel");
		cardsPanel.add(promptRolePanel, "promptRolePanel");
		cardsPanel.add(gameLevelPanel, "gameLevelPanel");
		cards.show(cardsPanel, "menuPanel");
	}

	public void show() {
		frame.setVisible(true);
	}

	public JPanel getCardsPanel() {
		return cardsPanel;
	}

	public CardLayout getCards() {
		return cards;
	}
}
