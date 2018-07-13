package punchballjump;

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
			powerupPath = "res/sharingan.png";
		} else if (name.equals(INVINCIBLE)) {
			powerupPath = "res/rinnegan.png";
		} else if (name.equals(SWAP)) {
			powerupPath = "res/byakuugan.png";
		}

		ImageIcon ii = new ImageIcon(powerupPath);
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
