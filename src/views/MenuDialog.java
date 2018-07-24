package views;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class MenuDialog extends JDialog {

	private JLabel jLabel;
	private JButton yesButton;
	private JButton noButton;
	private JLabel iconLabel;

	public MenuDialog() {
		setResizable(false);
		setUndecorated(true);
		setLayout(new FlowLayout());
		setSize(200, 200);
		setLocationRelativeTo(null);
		setVisible(false);
		jLabel = new JLabel("Return to Main Menu?");
		yesButton = new JButton("YES");
		noButton = new JButton("NO");
		iconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/small_jar_icon.png")));
		add(iconLabel);
		add(jLabel);
		add(yesButton);
		add(noButton);
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
