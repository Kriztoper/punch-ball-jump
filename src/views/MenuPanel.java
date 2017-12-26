package views;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private JButton playButton;
	private JButton howToPlayButton;
	private JButton creditsButton;
	
	public MenuPanel() {
		initPanel();
		initComponents();
		addComponents();
	}
	
	public void initPanel() {
		setLayout(new FlowLayout());
		setBackground(new Color(100, 149, 237));
	}
	
	public void initComponents() {
		playButton = new JButton("Play");
		
		howToPlayButton = new JButton("How to Play");
		
		creditsButton = new JButton("Credits");
		
	}

	public void addComponents() {
		add(playButton);
		add(howToPlayButton);
		add(creditsButton);
	}
	
	public JButton getPlayButton() {
		return playButton;
	}

	public void setPlayButton(JButton playButton) {
		this.playButton = playButton;
	}

	public JButton getHowToPlayButton() {
		return howToPlayButton;
	}

	public void setHowToPlayButton(JButton howToPlayButton) {
		this.howToPlayButton = howToPlayButton;
	}

	public JButton getCreditsButton() {
		return creditsButton;
	}

	public void setCreditsButton(JButton creditsButton) {
		this.creditsButton = creditsButton;
	}
}
