package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputIPDialog extends JDialog {

	private JLabel jLabel;
	private JTextField jTextField;
	private JButton okButton;
	private JButton cancelButton;
	private String ip;
	private ImageIcon bg;
	private JPanel panel;

	public InputIPDialog(Frame frame) {
		setResizable(false);
		setUndecorated(true);
		setSize(300, 200);
		setLocationRelativeTo(frame);
		setVisible(false);

		bg = new ImageIcon(getClass().getClassLoader().getResource("images/inputIPdialog.png"));
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		panel.setLayout(null);
		panel.setSize(300, 200);

		jLabel = new JLabel("Enter Server IP Address:");
		jLabel.setFont(new Font("Mathlete", Font.PLAIN, 14));
		jLabel.setForeground(Color.WHITE);
		jLabel.setBounds(60, 38, 200, 14);

		jTextField = new JTextField(20);
		jTextField.setFont(new Font("Open Sans", Font.PLAIN, 12));
		jTextField.setBounds(60, 63, 190, 30);

		okButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/ok_button.png")));
		okButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/ok_hoverButton.png")));
		okButton.setBounds(60, 126, 90, 30);
		modifyButton(okButton);

		cancelButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/cancel_button.png")));
		cancelButton.setRolloverIcon(
				new ImageIcon(getClass().getClassLoader().getResource("images/cancel_hoverButton.png")));
		cancelButton.setBounds(160, 126, 90, 30);
		modifyButton(cancelButton);

		panel.add(jLabel);
		panel.add(jTextField);
		panel.add(okButton);
		panel.add(cancelButton);
		add(panel);

		ip = "";
	}

	public String getIPAddress() {
		getOkButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jTextField != null && jTextField.getText() != null && !jTextField.getText().equals("")) {
					setModal(false);
					setVisible(false);
					ip = jTextField.getText();
				}
			}
		});
		getCancelButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ip = "";
			}
		});
		setModal(true);
		setVisible(true);
		requestFocus();

		return ip;
	}

	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JTextField getjTextField() {
		return jTextField;
	}

	public void setjTextField(JTextField jTextField) {
		this.jTextField = jTextField;
	}
}
