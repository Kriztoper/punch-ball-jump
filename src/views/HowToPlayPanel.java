package views;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import spaceninja.Commons;

public class HowToPlayPanel extends JPanel implements Commons {

	private JButton backButton;
	private ImageIcon bg;

	public HowToPlayPanel() {
		super();
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(null);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		bg = new ImageIcon(getClass().getClassLoader().getResource("images/HelpPanel.png"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, null);
	}

	public void initComponents() {
		backButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/back_button.png")));
		backButton.setBounds(510, 540, 120, 100);
		backButton
				.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/back_hoverButton.png")));
		modifyButton(backButton);
	}

	public void addComponents() {
		add(backButton);
	}

	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}

	public JButton getBackButton() {
		return backButton;
	}

	// public void setBackButton(JButton backButton) {
	// this.backButton = backButton;
	// }
}