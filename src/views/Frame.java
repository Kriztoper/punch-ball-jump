package views;

import java.awt.Color;

import javax.swing.JFrame;

import punchballjump.Commons;

public class Frame extends JFrame implements Commons {

	public Frame() {
		setTitle("Space Ninja");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(Color.GREEN);
	}
}