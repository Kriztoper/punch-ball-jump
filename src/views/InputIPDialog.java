package views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public InputIPDialog() {
		// setModal(true);
		setResizable(false);
		setUndecorated(true);
		setLayout(new FlowLayout());
		setSize(300, 200);
		setLocationRelativeTo(null);
		setVisible(false);
		jLabel = new JLabel("Enter Server IP Address:");
		jTextField = new JTextField(20);
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		add(jLabel);
		add(jTextField);
		add(okButton);
		add(cancelButton);
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
