package views;

import java.awt.CardLayout;

import javax.swing.JPanel;

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
			board.setVisible(true);
			board.requestFocus();
		} else {
			cards.show(cardsPanel, panelName);
			board.setVisible(false);
		}
		frame.repaint();
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
}
