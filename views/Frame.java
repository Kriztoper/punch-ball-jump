package views;

import java.awt.Color;

import javax.swing.JFrame;

import utils.Constants;

public class Frame extends JFrame {

	public Frame() {
		setTitle("Punch Ball Jump");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		//setLocationRelativeTo(null);
		setBackground(Color.GREEN);
	}
}
