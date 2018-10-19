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
		double rocketMidY = y + 0.5*rocket.landerRocketHeight;
		int landingAllowed = 0;
		double obstacleWidth = 32, obstacleHeight = 32;
		
		Engine engine = fs.getEngine();
		engine.setInputValue("rightWall", Conf.SCREEN_WIDTH - x - rocket.landerRocketWidth);
		engine.setInputValue("leftWall", x);
		engine.setInputValue("upperWall", y);
		engine.setInputValue("lowerWall", Conf.SCREEN_HEIGHT - y - rocket.landerRocketHeight);
		engine.setInputValue("yspeed", rocket.speedY);
		engine.setInputValue("xspeed", rocket.speedX);
		engine.setInputValue("xlandingPlatform", landingPlatformMid - rocketMid);
//		engine.setInputValue("ylandingPlatform", y + rocket.landerRocketHeight);
		
		if (rocketMid >= (landingSpace.x + 0.25*landingSpace.landingSpaceWidth) && 
				(rocketMid <= landingSpace.x + 0.75*landingSpace.landingSpaceWidth)) 
			landingAllowed = 1;
		else	
			landingAllowed = -1;
		
		engine.setInputValue("landingAllowed", landingAllowed);
		
//		engine.setInputValue("landingMode", landingMode ? 1 : 0);
		
		if(obstacles != null) {
			Point nearestObstacle = obstacles.nearestObstacle(x + 0.5*rocket.landerRocketWidth, 
					y + 0.5*rocket.landerRocketHeight);
			double obstacleX = Double.POSITIVE_INFINITY;
			double obstacleY = Double.POSITIVE_INFINITY;
			int top = 0, bottom = 0, left = 0, right = 0;
			
			if (nearestObstacle != null) {
				obstacleX = nearestObstacle.x - x + 0.5*rocket.landerRocketWidth;
				obstacleY = nearestObstacle.y - y + 0.5*rocket.landerRocketHeight;
//				System.out.println(obstacleX + " " + obstacleY + " ,actual: " + nearestObstacle.x + " " + nearestObstacle.y);
				if ((y + rocket.landerRocketHeight < (nearestObstacle.y - obstacleHeight/2)) && (rocketMid > (nearestObstacle.x - (obstacleWidth/2 + rocket.landerRocketWidth/2)))
						&& (rocketMid < (nearestObstacle.x + (obstacleWidth/2 + rocket.landerRocketWidth/2)))) 
					top = 1;
				else	
					top = -1;
				if ((y > nearestObstacle.y + obstacleHeight/2) && (rocketMid > (nearestObstacle.x - (obstacleWidth/2 + rocket.landerRocketWidth/2)))
						&& (rocketMid < (nearestObstacle.x + (obstacleWidth/2 + rocket.landerRocketWidth/2)))) 
					bottom = 1;
				else	
					bottom = -1;
				if ((x + 0.5*rocket.landerRocketWidth < (nearestObstacle.x - obstacleWidth/2)) && (rocketMidY > (nearestObstacle.y - (obstacleHeight/2 + rocket.landerRocketHeight/2)))
						&& (rocketMidY < (nearestObstacle.y + (obstacleHeight/2 + rocket.landerRocketHeight/2)))) 
					left = 1;
				else	
					left = -1;
				if ((x + 0.5*rocket.landerRocketWidth > nearestObstacle.x + obstacleWidth/2) && (rocketMidY > (nearestObstacle.y - (obstacleHeight/2 + rocket.landerRocketHeight/2)))
						&& (rocketMidY < (nearestObstacle.y + (obstacleHeight/2 + rocket.landerRocketHeight/2)))) 
					right = 1;
				else	
					right = -1;
			}
			
			engine.setInputValue("obstacleX", Math.abs(obstacleX));
			engine.setInputValue("obstacleY", Math.abs(obstacleY));
			
			engine.setInputValue("isTop", top);
			engine.setInputValue("isBottom", bottom);
			engine.setInputValue("isLeft", left);
			engine.setInputValue("isRight", right);
		}
		
		engine.process();

		Double yOutputValue = engine.getOutputValue("yOutputMove");
		Double xOutputValue = engine.getOutputValue("xOutputMove");
		
//		Double allowSpeeding = engine.getOutputValue("allowSpeeding");
		
//		if (landinModeStatus == 0) this.landingMode = false;
//		else if (landinModeStatus == 1) this.landingMode = true;
		
		this.currentMove = new int[] {yOutputValue.intValue(), xOutputValue.intValue()};

		engine.restart();
		return this.currentMove;
	}

	@Override
	public int[] currentMove() {
		return this.currentMove;
	}

}
