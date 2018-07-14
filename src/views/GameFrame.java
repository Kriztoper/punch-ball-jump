package views;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class GameFrame {

	private Frame frame;
	private CardLayout cards;
	private JPanel cardsPanel;
	private MenuPanel menuPanel;
	private PromptRolePanel promptRolePanel;
	private GamePanel gamePanel;
	private CountdownPanel countdownPanel;
	
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
		cards.show(cardsPanel, panelName);
		frame.repaint();
	}
	
	public void initPanels() {
		cards = new CardLayout();
		
		menuPanel = new MenuPanel();
		
		gamePanel = new GamePanel();
		
		promptRolePanel = new PromptRolePanel();
		countdownPanel = new CountdownPanel();
		
		cardsPanel = new JPanel();
		cardsPanel.setLayout(cards);
		
		cardsPanel.add(menuPanel, "menuPanel");
		cardsPanel.add(gamePanel, "gamePanel");
		cardsPanel.add(promptRolePanel, "promptRolePanel");
		cardsPanel.add(countdownPanel, "countdownPanel");
		cards.show(cardsPanel, "menuPanel");
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
