package views;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import punchballjump.Commons;

public class GameLevelPanel extends JPanel implements Commons {

	private JButton easyButton;
	private JButton normalButton;
	private JButton hardButton;
	private JButton backButton;
	private ImageIcon bg;

	public GameLevelPanel() {
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(null);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		bg = new ImageIcon("images/GameLevelPanel.png");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, null);
	}

	public void initComponents() {
		easyButton = new JButton(new ImageIcon("images/easy_button.png"));
		easyButton.setBounds(85, 210, 185, 185);
		easyButton.setRolloverIcon(new ImageIcon("images/easy_hoverbutton.png"));
		modifyButton(easyButton);

		normalButton = new JButton(new ImageIcon("images/normal_button.png"));
		normalButton.setBounds(240, 210, 185, 185);
		normalButton.setRolloverIcon(new ImageIcon("images/normal_hoverbutton.png"));
		modifyButton(normalButton);

		hardButton = new JButton(new ImageIcon("images/hard_button.png"));
		hardButton.setBounds(395, 210, 185, 185);
		hardButton.setRolloverIcon(new ImageIcon("images/hard_hoverbutton.png"));
		modifyButton(hardButton);

		backButton = new JButton(new ImageIcon("images/back_button.png"));
		backButton.setBounds(245, 460, 120, 100);
		backButton.setRolloverIcon(new ImageIcon("images/back_hoverButton.png"));
		modifyButton(backButton);
	}

	public void addComponents() {
		add(easyButton);
		add(normalButton);
		add(hardButton);
		add(backButton);
	}

	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}

	public JButton getBackButton() {
		return this.backButton;
	}

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}

	public JButton getEasyButton() {
		return easyButton;
	}

	public void setEasyButton(JButton easyButton) {
		this.easyButton = easyButton;
	}

	public JButton getNormalButton() {
		return normalButton;
	}

	public void setNormalButton(JButton normalButton) {
		this.normalButton = normalButton;
	}

	public JButton getHardButton() {
		return hardButton;
	}

	public void setHardButton(JButton hardButton) {
		this.hardButton = hardButton;
	}

}
