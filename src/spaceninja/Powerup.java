package spaceninja;

import javax.swing.ImageIcon;

public class Powerup extends Sprite implements Commons {
	private String name;
	private boolean isVisible;

	public Powerup(int initX, int initY, String name) {
		x = initX;
		y = initY;
		setName(name);

		// assign the corresponding image for the designated powerup
		String powerupPath = "";
		if (name.equals(RESTORE)) {
			powerupPath = "images/+1heart_powerup.png";
		} else if (name.equals(INVINCIBLE)) {
			powerupPath = "images/invincible_powerup.png";
		} else if (name.equals(SWAP)) {
			powerupPath = "images/swap_powerup.png";
		}

		ImageIcon ii = new ImageIcon(getClass().getClassLoader().getResource(powerupPath));
		image = ii.getImage();

		i_width = image.getWidth(null);
		i_height = image.getHeight(null);

		setVisible(false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
