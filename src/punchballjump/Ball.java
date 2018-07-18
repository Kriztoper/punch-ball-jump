package punchballjump;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ball extends Sprite implements Commons, Serializable {
	private ArrayList<Point> points;
	private int pointsIndex;
	private int direction;

	public Ball() {
		pointsIndex = 0;
		direction = 1;

		ImageIcon ii = new ImageIcon("images/star1.png");
		image = ii.getImage();

		i_width = image.getWidth(null);
		i_height = image.getHeight(null);

		resetState();
		initBallPoints(40, 180, new Point(315, 325));
	}

	public void move() {
		Point point = getCurrentPoint();

		x = point.x;
		y = point.y;
	}

	private void resetState() {
		x = INIT_BALL_X;
		y = INIT_BALL_Y;
	}

	private void initBallPoints(int p, double radius, Point center) {
		points = new ArrayList<>();
		double slice = 2 * Math.PI / p;
		for (int i = 0; i < p; i++) {
			double angle = slice * i;
			int newX = (int) (center.x + radius * Math.cos(angle));
			int newY = (int) (center.y + radius * Math.sin(angle));
			Point temp = new Point(newX, newY);
			// System.out.println(temp);
			points.add(temp);
		}
	}

	public void reverseDirection() {
		direction = ((direction == 1) ? (-1) : (1));
	}

	public Point getCurrentPoint() {
		int p = pointsIndex;
		pointsIndex += direction;
		if (pointsIndex == points.size()) {
			pointsIndex = 0;
		} else if (pointsIndex == -1) {
			pointsIndex = points.size() - 1;
		}
		// System.out.println("Ball: " + points.get(p).x + ", " + points.get(p).y);
		return points.get(p);
	}

	public boolean isSpeedDecreasable(int playerX) {
		if ((direction == 1 && x + 20 < playerX) || (direction == -1 && x + 20 > playerX)) {
			return true;
		}

		return false;
	}
}
