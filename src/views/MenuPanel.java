package views;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import punchballjump.Commons;

public class MenuPanel extends JPanel implements Commons {

	private JButton howToPlayButton;
	private JButton creditsButton;
	private JButton onePlayerButton;
	private JButton twoPlayersButton;
	private ImageIcon bg;

	public MenuPanel() {
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(null);
		setOpaque(false);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		bg = new ImageIcon("images/MenuPanel.png");
		setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, this);
	}

	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}

	public void initComponents() {
		onePlayerButton = new JButton(new ImageIcon("images/1player_button.png"));
		onePlayerButton.setBounds(100, 410, 210, 60);
		onePlayerButton.setRolloverIcon(new ImageIcon("images/1player_hoverButton.png"));
		modifyButton(onePlayerButton);

		twoPlayersButton = new JButton(new ImageIcon("images/2players_button.png"));
		twoPlayersButton.setBounds(330, 410, 210, 60);
		twoPlayersButton.setRolloverIcon(new ImageIcon("images/2players_hoverButton.png"));
		modifyButton(twoPlayersButton);

		howToPlayButton = new JButton(new ImageIcon("images/help_button.png"));
		howToPlayButton.setBounds(100, 470, 210, 60);
		howToPlayButton.setRolloverIcon(new ImageIcon("images/help_hoverButton.png"));
		modifyButton(howToPlayButton);

		creditsButton = new JButton(new ImageIcon("images/credits_button.png"));
		creditsButton.setBounds(330, 470, 210, 60);
		creditsButton.setRolloverIcon(new ImageIcon("images/credits_hoverButton.png"));
		modifyButton(creditsButton);
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
