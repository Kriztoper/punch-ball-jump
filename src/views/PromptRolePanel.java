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

public class PromptRolePanel extends JPanel implements Commons {

	private JLabel promptLabel;
	private JButton serverButton;
	private JButton clientButton;
	private JButton backButton;

	public PromptRolePanel() {
		initPanel();
		initComponents();
		addComponents();
	}

	public InetAddress promptServerIPAddress() {
		String ipAddress = JOptionPane.showInputDialog(this, "Enter Server IP Address: ");

		try {
			return (InetAddress.getByName(ipAddress));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void initPanel() {
		setLayout(new FlowLayout());
		setBackground(new Color(250, 128, 114));
		setSize(Commons.WIDTH, Commons.HEIGHT);
	}

	public void initComponents() {
		promptLabel = new JLabel("Choose role:");
		backButton = new JButton("Back");
		setServerButton(new JButton("Server"));
		setClientButton(new JButton("Client"));
	}

	public void addComponents() {
		add(promptLabel);
		add(backButton);
		add(getServerButton());
		add(getClientButton());
	}

	public JButton getServerButton() {
		return serverButton;
	}

	public void setServerButton(JButton serverButton) {
		this.serverButton = serverButton;
	}

	public JButton getClientButton() {
		return clientButton;
	}

	public void setClientButton(JButton clientButton) {
		this.clientButton = clientButton;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public void setBackButton() {
		this.backButton = backButton;
	}
}
