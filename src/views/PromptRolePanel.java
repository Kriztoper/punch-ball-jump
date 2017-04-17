package views;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PromptRolePanel extends JPanel{

	private JLabel promptLabel;
	private JButton serverButton;
	private JButton clientButton;
	
	public PromptRolePanel() {
		initPanel();
		initComponents();
		addComponents();
	}
	
	public void initPanel() {
		setLayout(new FlowLayout());
		setBackground(new Color(250, 128, 114));
	}
	
	public void initComponents() {
		promptLabel = new JLabel("Choose role:");
		
		setServerButton(new JButton("Server"));
		
		setClientButton(new JButton("Client"));
	}
	
	public void addComponents() {
		add(promptLabel);
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
}
