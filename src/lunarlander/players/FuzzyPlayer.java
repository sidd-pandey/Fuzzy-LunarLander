package lunarlander.players;

import java.awt.Point;

import com.fuzzylite.Engine;

import lunarlander.fuzzy.FuzzySystem;
import lunarlander.game.Conf;
import lunarlander.game.Landingspace;
import lunarlander.game.Obstacles;
import lunarlander.game.Rocket;

public class FuzzyPlayer implements Player {

	private Rocket rocket;
	private Landingspace landingSpace;
	private FuzzySystem fs;
	private int[] currentMove;
	private double landingPlatformMid; 
	private Obstacles obstacles;
	
	private boolean landingMode = false;
	
	public FuzzyPlayer(Rocket rocket) {

		this.rocket = rocket;
		this.landingSpace = rocket.getLandingSpace();
		this.obstacles = rocket.getObstacles();

		this.landingPlatformMid = landingSpace.x + 0.5*landingSpace.landingSpaceWidth;
		fs = new FuzzySystem(rocket.landerRocketWidth, rocket.landerRocketHeight, landingPlatformMid, 
				this.landingSpace.landingSpaceWidth);
	}

	@Override
	public int[] nextMove() {

		int x = rocket.x;
		int y = rocket.y;
		double rocketMid = x + 0.5*rocket.landerRocketWidth;
		
		Engine engine = fs.getEngine();
		engine.setInputValue("rightWall", Conf.SCREEN_WIDTH - x - rocket.landerRocketWidth);
		engine.setInputValue("leftWall", x);
		engine.setInputValue("upperWall", y);
		engine.setInputValue("lowerWall", Conf.SCREEN_HEIGHT - y - rocket.landerRocketHeight);
		engine.setInputValue("yspeed", rocket.speedY);
		engine.setInputValue("xspeed", rocket.speedX);
		engine.setInputValue("xlandingPlatform", landingPlatformMid - rocketMid);
		engine.setInputValue("ylandingPlatform", y + rocket.landerRocketHeight);
		engine.setInputValue("landingMode", landingMode ? 1 : 0);
		
		if(obstacles != null) {
			Point nearestObstacle = obstacles.nearestObstacle(x + 0.5*rocket.landerRocketWidth, 
					y + 0.5*rocket.landerRocketHeight);
			double obstacleX = Double.POSITIVE_INFINITY;
			double obstacleY = Double.POSITIVE_INFINITY;
			if (nearestObstacle != null) {
				obstacleX = nearestObstacle.x - x + 0.5*rocket.landerRocketWidth;
				obstacleY = nearestObstacle.y - y + 0.5*rocket.landerRocketHeight;
				System.out.println(obstacleX + " " + obstacleY + " ,actual: " + nearestObstacle.x + " " + nearestObstacle.y);
			}
			engine.setInputValue("nearestObstacleX", obstacleX);
			engine.setInputValue("nearestObstacleY", obstacleY);
		}
		
		engine.process();

		Double yOutputValue = engine.getOutputValue("yOutputMove");
		Double xOutputValue = engine.getOutputValue("xOutputMove");
		
		Double landinModeStatus = engine.getOutputValue("landingModeStatus");
		
		if (landinModeStatus == 0) this.landingMode = false;
		else if (landinModeStatus == 1) this.landingMode = true;
		
		this.currentMove = new int[] {yOutputValue.intValue(), xOutputValue.intValue()};

		engine.restart();
		return this.currentMove;
	}

	@Override
	public int[] currentMove() {
		return this.currentMove;
	}

}
