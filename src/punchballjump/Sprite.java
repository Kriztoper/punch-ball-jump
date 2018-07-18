package punchballjump;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sprite {
	protected int x;
	protected int y;
	protected int i_width;
	protected int i_height;
	protected Image image;

	public Sprite() {

	}

	public Sprite(int x, int y, String imagePath) {
		setX(x);
		setY(y);

		ImageIcon ii = new ImageIcon(imagePath);
		image = ii.getImage();

		i_width = image.getWidth(null);
		i_height = image.getHeight(null);
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return i_width;
	}

	public int getHeight() {
		return i_height;
	}

	public Image getImage() {
		return image;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public Rectangle getLongBiggerRect(long rectBorder) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		return new Rectangle(x - width - (int) rectBorder, y - 3 * height, width * 3 + 2 * (int) rectBorder,
				height * 7);
	}

	public Rectangle getBiggerRect() {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		return new Rectangle(x - width, y - height, width * 3, height * 3);
	}

	public Rectangle getSlightlyBiggerRect() {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		return new Rectangle(x - 20, y - 20, width + 40, height + 40);
	}
}
