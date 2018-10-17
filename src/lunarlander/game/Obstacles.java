package lunarlander.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Obstacles {

	private List<Point> coordinates;
	private BufferedImage image;
	private int width, height;
	
	
	public Obstacles() {
		this.coordinates = loadObstacles();
		this.image = loadImage();
		if (image != null) {
			this.width = image.getWidth();
			this.height = image.getHeight();
		}
	}
	
	public void draw(Graphics2D g2d) {
		for (Point p : coordinates) {
			g2d.drawImage(image, p.x - width/2, p.y - height/2, null);
		}
	}

	private BufferedImage loadImage() {
		BufferedImage img = null;
		try {
			URL obstacleUrl = this.getClass().getResource("resources/img/obstacle_small.png");
			 img = ImageIO.read(obstacleUrl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return img;
	}

	private List<Point> loadObstacles() {
		
		List<Point> centerPoints = new ArrayList<>();
		centerPoints.add(new Point(Conf.SCREEN_WIDTH/2, Conf.SCREEN_HEIGHT/2));
		centerPoints.add(new Point(Conf.SCREEN_WIDTH/3, Conf.SCREEN_HEIGHT/3));
		centerPoints.add(new Point(Conf.SCREEN_WIDTH - 600, Conf.SCREEN_HEIGHT/2));
		return centerPoints;
	}
	
	public boolean checkForCollision(int x, int y, int actorWidth,  int actorHeight) {
		
		boolean collided = false;

		for (Point box : coordinates) {
			if(checkForCollision(x, y, actorWidth, actorHeight, box.x, box.y))
				return true;
		}
		
		return collided;
	}
	
	public Point nearestObstacle(double e, double f) {
		Point nearest = null;
		double smallestDistance = Integer.MAX_VALUE;
		for (Point box : coordinates) {
			double d = box.distanceSq(e, f);
			if ( d < smallestDistance) {
				nearest = box;
				smallestDistance = d;
			}
		}
		return nearest;
	}

	private boolean checkForCollision(int x, int y, int actorWidth, int actorHeight, int boxX, int boxY) {
		
		Rectangle actorLowerRectangle = new Rectangle(x,  y+actorHeight/2, actorWidth, actorHeight/2);
		Rectangle actorUpperRectangle = new Rectangle(x + 20,  y, actorWidth/3, actorHeight/2);
		
		Rectangle box = new Rectangle(boxX - width/2, boxY - height/2, width, height); 
		
		//check for intersection with x coordinates
		if (box.intersects(actorUpperRectangle) || box.intersects(actorLowerRectangle)) {
			return true;
		}
		
		return false;
	}

	
	
}
