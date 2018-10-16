package lunarlander.players;

import com.fuzzylite.Engine;

import lunarlander.fuzzy.FuzzySystem;
import lunarlander.game.Conf;
import lunarlander.game.Landingspace;
import lunarlander.game.Rocket;

public class FuzzyPlayer implements Player {

	private Rocket rocket;
	private Landingspace landingSpace;
	private FuzzySystem fs;
	private int[] currentMove;
	private double landingPlatformMid; 
	
	private boolean landingMode = false;
	
	public FuzzyPlayer(Rocket rocket) {
		this.rocket = rocket;
		this.landingSpace = rocket.getLandingSpace();
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
		engine.setInputValue("landingMode", landingMode ? 1 : 0);
		
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
