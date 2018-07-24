package views;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.util.Arrays;

import javax.swing.JPanel;

import spaceninja.Board;
import spaceninja.ClientBoard;
import spaceninja.Commons;

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
	private int difficulty;

	public GameFrame() {
		frame = new Frame();
		java.net.URL url = ClassLoader.getSystemResource("images/jar_icon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		frame.setIconImage(img);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (board != null && board.isVisible() && board.socket != null) {
					System.out.println("Server socket closed before exit!");
					board.socket.close();
				}
				if (clientBoard != null && clientBoard.isVisible() && clientBoard.socket != null) {
					System.out.println("Client socket closed before exit!");
					clientBoard.socket.close();
				}
			}
		});
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
			board = new Board(this, IS_COMPUTER, difficulty);// , clientBoard);
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
		board = new Board(this, IS_NOT_COMPUTER, HUMAN);// , clientBoard);
		board.setVisible(true);
		cardsPanel.add(board, "board");
		cards.show(cardsPanel, "board");
		board.requestFocus();
	}

	public void startGameClient(InetAddress serverIPAddress) {
		clientBoard = new ClientBoard(this, serverIPAddress);
		clientBoard.setVisible(true);
		cardsPanel.add(clientBoard, "clientBoard");
		cards.show(cardsPanel, "clientBoard");
		clientBoard.requestFocus();
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

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
