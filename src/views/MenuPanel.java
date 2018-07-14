package views;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.Constants;

public class MenuPanel extends JPanel {

	private JButton playButton;
	private JButton howToPlayButton;
	private JButton creditsButton;
	private JButton onePlayerButton;
	private JButton twoPlayersButton;
	
	public MenuPanel() {
		initPanel();
		initComponents();
		addComponents();
	}
	
	public void initPanel() {
		setLayout(null);
		setBackground(new Color(100, 149, 237));
	}
	
	public void initComponents() {
		playButton = new JButton("Play");
		
		howToPlayButton = new JButton("How to Play");
		
		creditsButton = new JButton("Credits");
		onePlayerButton = new JButton("One Player");
		setTwoPlayersButton(new JButton("Two Players"));
		onePlayerButton.setBounds(Constants.WIDTH/2-100,100,200,60);
		getTwoPlayersButton().setBounds(Constants.WIDTH/2-100,160,200,60);
		howToPlayButton.setBounds(Constants.WIDTH/2-100, 220, 200, 60);
		creditsButton.setBounds(Constants.WIDTH/2-100,280,200,60);
	}

	public void addComponents() {
		add(playButton);
		add(howToPlayButton);
		add(creditsButton);
		add(onePlayerButton);
		add(twoPlayersButton);
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

	public JButton getOnePlayerButton() {
		return onePlayerButton;
	}

	public void setOnePlayerButton(JButton onePlayerButton) {
		this.onePlayerButton = onePlayerButton;
	}

	public JButton getTwoPlayersButton() {
		return twoPlayersButton;
	}

	public void setTwoPlayersButton(JButton twoPlayersButton) {
		this.twoPlayersButton = twoPlayersButton;
	}

	
}
