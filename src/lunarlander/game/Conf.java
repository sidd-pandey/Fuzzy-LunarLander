package lunarlander.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Conf {

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final String NEW = "new"; 
	
	private int rocketX = -1, rocketY = -1;
	private int pltX = -1;
	private List<Point> obstalceCenters = null;
	
	private static Conf INSTANCE;
	
	private Conf() {
		
	}

	public static Conf get() {
		if (INSTANCE == null) {
			INSTANCE = new Conf();
		}
		return INSTANCE;
	}

	public int getRocketX() {
		if (this.rocketX <= 0) {
			return new Random().nextInt(Framework.frameWidth - 64); // top right corner
		}
		return rocketX;
	}

	public void setRocketX(int rocketX) {
		this.rocketX = rocketX;
	}

	public int getRocketY() {
		if (this.rocketY <= 0) {
			return 0;
		}
		return rocketY;
	}

	public void setRocketY(int rocketY) {
		this.rocketY = rocketY;
	}

	public int getPltX() {
		if (this.pltX <=0) {
			return new Random().nextInt(Framework.frameWidth - 135); // X random start
		}
		return pltX;
	}

	public void setPltX(int pltX) {
		this.pltX = pltX;
	}

	public List<Point> getObstalceCenters() {
		if (this.obstalceCenters == null) {
			ArrayList<Point> initCenters = new ArrayList<>();
			initCenters.add(new Point(Conf.SCREEN_WIDTH/4, 250));
			initCenters.add(new Point(3 * Conf.SCREEN_WIDTH/4, 250));
			initCenters.add(new Point(Conf.SCREEN_WIDTH/4, Conf.SCREEN_HEIGHT - 250));
			initCenters.add(new Point(3 * Conf.SCREEN_WIDTH/4, Conf.SCREEN_HEIGHT - 250));
			initCenters.add(new Point(Conf.SCREEN_WIDTH/2, Conf.SCREEN_HEIGHT/2));
			return initCenters;
		}
		return obstalceCenters;
	}

	public void setObstalceCenters(List<Point> obstalceCenters) {
		this.obstalceCenters = obstalceCenters;
	}
	
}