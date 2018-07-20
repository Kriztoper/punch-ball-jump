package views;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import punchballjump.Commons;

public class CreditsPanel extends JPanel implements Commons {

	private JButton backButton;
	private ImageIcon bg;

	public CreditsPanel() {
		super();
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(null);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		bg = new ImageIcon("images/CreditsPanel.png"); // TODO: change with new edited credits image bg
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, null);
	}

	public void initComponents() {
		backButton = new JButton(new ImageIcon("images/back_button.png"));
		backButton.setBounds(510, 540, 120, 100);
		backButton.setRolloverIcon(new ImageIcon("images/back_hoverButton.png"));
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

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}
}