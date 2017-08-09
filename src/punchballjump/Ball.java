package punchballjump;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ball extends Sprite implements Commons {
	private int xdir;
	private int ydir;
	private ArrayList<Point> points;
	private int pointsIndex;
	private int direction;
	
	public Ball() {
		pointsIndex = 0;
		direction = 1;
		
		xdir = 1;
		ydir = -1;
		
		ImageIcon ii = new ImageIcon("images/breakout/ball.png");
		image = ii.getImage();
		
		i_width = image.getWidth(null);
		i_height = image.getHeight(null);
		
		resetState();
		initBallPoints(40, 100, new Point(150, 200));
	}
	
	public void move() {
		Point point = getCurrentPoint();
		
		x = point.x;
		y = point.y;
/*		x += xdir;
		y += ydir;
		
		if (x == 0) {
			setXDir(1);
		}
		
		if (x == WIDTH - i_width) {
			setXDir(-1);
		}
		
		if (y == 0) {
			setYDir(1);
		}
*/	}
	
	private void resetState() {
		x = INIT_BALL_X;
		y = INIT_BALL_Y;
	}
	
	public void setXDir(int x) {
		xdir = x;
	}
	
	public void setYDir(int y) {
		ydir = y;
	}
	
	public int getYDir() {
		return ydir;
	}
	
	private void initBallPoints(int p, double radius, Point center)
    {
		points = new ArrayList<>();
        double slice = 2 * Math.PI / p;
        for (int i = 0; i < p; i++)
        {
            double angle = slice * i;
            int newX = (int)(center.x + radius * Math.cos(angle));
            int newY = (int)(center.y + radius * Math.sin(angle));
            Point temp = new Point(newX, newY);
            //System.out.println(temp);
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
		return points.get(p);
	}
}
