package lunarlander.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lunarlander.fuzzy.RuleReader;

public class Conf {

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final String NEW = "new"; 
	
	private int rocketX = -1, rocketY = -1;
	private int pltX = -1;
	private List<Point> obstalceCenters = null;
	private boolean random = true;
	private List<String> rules = new RuleReader().read();
	private boolean playerMode = false;
	
	private static Conf INSTANCE = new Conf();
	
	private Conf() {
		randomize();
	}

	private void randomize() {
		if (random) {
			this.rocketX = new Random().nextInt(Framework.frameWidth - 64); // top right corner
			this.rocketY = 0;
			this.pltX = new Random().nextInt(Framework.frameWidth - 135); // X random start
			ArrayList<Point> initCenters = new ArrayList<>();
			initCenters.add(new Point(Conf.SCREEN_WIDTH/4, 250));
			initCenters.add(new Point(3 * Conf.SCREEN_WIDTH/4, 250));
			this.obstalceCenters = initCenters;
		}
	}

	public static Conf get() {
		return INSTANCE;
	}
	
	public static void reset() {
		if (INSTANCE.isRandom()) {
			List<String> prevRules = INSTANCE.getRules();
			boolean playerMode = INSTANCE.isPlayerMode();
			INSTANCE = new Conf();
			INSTANCE.setRules(prevRules);
			INSTANCE.setPlayerMode(playerMode);
		}
	}

	public int getRocketX() {
		return rocketX;
	}

	public void setRocketX(int rocketX) {
		this.rocketX = rocketX;
	}

	public int getRocketY() {
		return rocketY;
	}

	public void setRocketY(int rocketY) {
		this.rocketY = rocketY;
	}

	public int getPltX() {
		return this.pltX;
	}

	public void setPltX(int pltX) {
		this.pltX = pltX;
	}

	public List<Point> getObstalceCenters() {
		return obstalceCenters;
	}
	
	public String getObstaclesAsStr() {
		StringBuilder builder = new StringBuilder();
		for (Point p : obstalceCenters) {
			builder.append(p.x+","+p.y+"\n");
		}
		return builder.toString();
	}

	public void setObstalceCenters(List<Point> obstalceCenters) {
		this.obstalceCenters = obstalceCenters;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public List<String> getRules() {
		return rules;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
	}

	public boolean isPlayerMode() {
		return playerMode;
	}

	public void setPlayerMode(boolean playerMode) {
		this.playerMode = playerMode;
	}
	
}