package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InputIPDialog extends JDialog {

	private JLabel jLabel;
	private JTextField jTextField;
	private JButton okButton;
	private JButton cancelButton;
	private String ip;

	public InputIPDialog(Frame frame) {
		getContentPane().setLayout(null);
		setResizable(false);
		setUndecorated(true);
		setSize(300, 200);
		setLocationRelativeTo(frame);
		setVisible(false);
		jLabel = new JLabel("Enter Server IP Address:");
		jLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
		jLabel.setBounds(60, 38, 200, 14);

		jTextField = new JTextField(20);
		jTextField.setFont(new Font("Open Sans", Font.PLAIN, 12));
		jTextField.setBounds(60, 63, 190, 30);

		okButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/ok_button.png")));
		okButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/ok_hoverButton.png")));
		okButton.setBounds(60, 126, 80, 30);
		modifyButton(okButton);

		cancelButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/cancel_button.png")));
		cancelButton.setRolloverIcon(
				new ImageIcon(getClass().getClassLoader().getResource("images/cancel_hoverButton.png")));
		cancelButton.setBounds(170, 126, 80, 30);
		modifyButton(cancelButton);

		getContentPane().add(jLabel);
		getContentPane().add(jTextField);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);

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
