package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuDialog extends JDialog {

	private JLabel jLabel;
	private JButton yesButton;
	private JButton noButton;
	private JLabel iconLabel;
	private ImageIcon bg;
	private JPanel panel;

	public MenuDialog() {
		setResizable(false);
		setUndecorated(true);
		setSize(420, 400);
		setLocationRelativeTo(null);
		setVisible(false);
		
		bg = new ImageIcon(getClass().getClassLoader().getResource("images/menudialog.png"));
		panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
		};
		panel.setLayout(null);
		panel.setSize(420, 400);
	
		yesButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/yes_button.png")));
		yesButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/yes_hoverbutton.png")));
		yesButton.setBounds(130, 180, 155, 40);
		modifyButton(yesButton);
		
		noButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/no_button.png")));
		noButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/no_hoverbutton.png")));
		noButton.setBounds(130, 230, 155, 40);
		modifyButton(noButton);
		
		panel.add(yesButton);
		panel.add(noButton);
		add(panel);
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
