package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class MenuDialog extends JDialog {

	private JLabel jLabel;
	private JButton yesButton;
	private JButton noButton;
	
	public MenuDialog() {
		setUndecorated(true);
		setSize(200, 200);
		setLocationRelativeTo(null);
		setVisible(false);
		setBackground(new Color(8,31,39));
		
		jLabel = new JLabel("Return to Main Menu?");
		jLabel.setBounds(24, 63, 151, 21);
		jLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		yesButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/yes_button.png")));
		yesButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/yes_hoverButton.png")));
		yesButton.setBounds(48, 114, 50, 30);
		modifyButton(yesButton);
		
		noButton = new JButton("NO");
		noButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/no_button.png")));
		noButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/no_hoverButton.png")));
		noButton.setBounds(104, 114, 50, 30);
		modifyButton(noButton);
		
		getContentPane().setLayout(null);
		getContentPane().add(jLabel);
		getContentPane().add(yesButton);
		getContentPane().add(noButton);
	}

	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}
	
	public JButton getYesButton() {
		return yesButton;
	}

	public void setYesButton(JButton yesButton) {
		this.yesButton = yesButton;
	}

	public JButton getNoButton() {
		return noButton;
	}

	public void setNoButton(JButton noButton) {
		this.noButton = noButton;
	}
}
