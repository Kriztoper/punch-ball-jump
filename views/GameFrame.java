package views;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class GameFrame {

	private Frame frame;
	private CardLayout cards;
	private JPanel cardsPanel;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	
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
	
	public void setCurrentPanel(String panelName) {
		cards.show(cardsPanel, panelName);
	}
	
	public void initPanels() {
		cards = new CardLayout();
		
		menuPanel = new MenuPanel();
		
		gamePanel = new GamePanel();
		
		cardsPanel = new JPanel();
		cardsPanel.setLayout(cards);
		
		cardsPanel.add(menuPanel, "menuPanel");
		cardsPanel.add(gamePanel, "gamePanel");
		cards.show(cardsPanel, "menuPanel");
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
