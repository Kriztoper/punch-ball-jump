package views;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import punchballjump.Commons;
import utils.Constants;

public class MenuPanel extends JPanel implements Commons {

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
		setBackground(new Color(100, 149, 237)); // TODO: change with new edited menu image bg
		setSize(Commons.WIDTH, Commons.HEIGHT);
	}

	public void initComponents() {
		onePlayerButton = new JButton("One Player");
		twoPlayersButton = new JButton("Two Players");
		howToPlayButton = new JButton("How to Play");
		creditsButton = new JButton("Credits");
		onePlayerButton.setBounds(Constants.WIDTH / 2 - 100, 100, 200, 60);
		twoPlayersButton.setBounds(Constants.WIDTH / 2 - 100, 160, 200, 60);
		howToPlayButton.setBounds(Constants.WIDTH / 2 - 100, 220, 200, 60);
		creditsButton.setBounds(Constants.WIDTH / 2 - 100, 280, 200, 60);
	}

	public void addComponents() {
		add(onePlayerButton);
		add(twoPlayersButton);
		add(howToPlayButton);
		add(creditsButton);
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
