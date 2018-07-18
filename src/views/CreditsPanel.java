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
		setLayout(new FlowLayout());
		setSize(Commons.WIDTH, Commons.HEIGHT);
		bg = new ImageIcon("images/test.png"); // TODO: change with new edited credits image bg
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, null);
	}

	public void initComponents() {
		setBackButton(new JButton("Back"));
	}

	public void addComponents() {
		add(backButton);
	}

	public JButton getBackButton() {
		return backButton;
	}

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}
}