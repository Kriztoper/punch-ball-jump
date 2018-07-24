package views;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import spaceninja.Commons;

public class Frame extends JFrame implements Commons {

	public Frame() {
		setTitle("Space Ninja");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		setIconImage(Toolkit.getDefaultToolkit().
				getImage(Frame.class.getResource("/images/jar_icon.png")));
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(Color.GREEN);
	}

}