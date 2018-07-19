package views;

import java.awt.CardLayout;
import java.util.Arrays;

import javax.swing.JPanel;

import punchballjump.Board;

public class GameFrame {

	private Frame frame;
	private CardLayout cards;
	private JPanel cardsPanel;
	private MenuPanel menuPanel;
	private PromptRolePanel promptRolePanel;
	private HowToPlayPanel howToPlayPanel;
	private CreditsPanel creditsPanel;
	private GameLevelPanel gameLevelPanel;
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
			board = new Board(this);
			board.setVisible(true);
			cardsPanel.add(board, "board");
			cards.show(cardsPanel, panelName);
			board.requestFocus();
		} else {
			cards.show(cardsPanel, panelName);
		}
		frame.repaint();
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
}
