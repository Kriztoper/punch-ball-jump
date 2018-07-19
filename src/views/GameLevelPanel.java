package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import punchballjump.Commons;

public class GameLevelPanel extends JPanel implements Commons {

	private JButton easyButton;
	private JButton normalButton;
	private JButton hardButton;
	
	public GameLevelPanel() {
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(new FlowLayout());
		setBackground(new Color(250, 128, 114)); // TODO: change with new edited prompt role image bg
		setSize(Commons.WIDTH, Commons.HEIGHT);
	}

	public void initComponents() {
		easyButton = new JButton("EASY");
		normalButton = new JButton("NORMAL");
		hardButton = new JButton("HARD");
	}

	public void addComponents() {
		add(easyButton);
		add(normalButton);
		add(hardButton);
	}

	public JButton getEasyButton() {
		return this.easyButton;
	}
	
	public void setEasyButton(JButton easyButton) {
		this.easyButton = easyButton;
	}
	
	public JButton getNormalButton() {
		return this.normalButton;
	}
	
	public void setNormalButton(JButton normalButton) {
		this.normalButton = normalButton;
	}
	
	public JButton getHardButton() {
		return this.hardButton;
	}
	
	public void setHardButton(JButton hardButton) {
		this.hardButton = hardButton;
	}
	
	
	
}

