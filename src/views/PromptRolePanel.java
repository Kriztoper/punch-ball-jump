package views;

import java.awt.Graphics;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import spaceninja.Commons;

public class PromptRolePanel extends JPanel implements Commons {

	private ImageIcon bg;
	private JButton serverButton;
	private JButton clientButton;
	private JButton backButton;
	public InputIPDialog inputIPDialog;

	public PromptRolePanel() {
		inputIPDialog = new InputIPDialog();
		initPanel();
		initComponents();
		addComponents();
	}

	public InetAddress promptServerIPAddress() {
		String ipAddress = inputIPDialog.getIPAddress();

		if (ipAddress != null && !ipAddress.equals("")) {
			try {
				return (InetAddress.getByName(ipAddress));
			} catch (UnknownHostException e) {
			}
		}

		return null;
	}

	public void initPanel() {
		setLayout(null);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		bg = new ImageIcon(getClass().getClassLoader().getResource("images/PromptRolePanel.png"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, null);
	}

	public void initComponents() {
		backButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/back_button.png")));
		backButton.setBounds(245, 490, 120, 100);
		backButton
				.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("images/back_hoverButton.png")));
		modifyButton(backButton);

		serverButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/server_button.png")));
		serverButton.setBounds(85, 370, 210, 60);
		serverButton.setRolloverIcon(
				new ImageIcon(getClass().getClassLoader().getResource("images/server_hoverButton.png")));
		modifyButton(serverButton);

		clientButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/client_button.png")));
		clientButton.setBounds(345, 370, 210, 60);
		clientButton.setRolloverIcon(
				new ImageIcon(getClass().getClassLoader().getResource("images/client_hoverButton.png")));
		modifyButton(clientButton);
	}

	public void addComponents() {
		add(backButton);
		add(getServerButton());
		add(getClientButton());
	}

	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
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

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}
}
