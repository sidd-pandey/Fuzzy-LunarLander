package lunarlander.players;

import com.fuzzylite.Engine;

import lunarlander.fuzzy.FuzzySystem;
import lunarlander.game.Conf;
import lunarlander.game.Rocket;

public class FuzzyPlayer implements Player {

	private FuzzySystem fs = new FuzzySystem();
	private Rocket rocket;
	private int[] currentMove; 

	public FuzzyPlayer(Rocket rocket) {
		this.rocket = rocket;
	}

	@Override
	public int[] nextMove() {

		int x = rocket.x - rocket.landerRocketWidth;
		int y = rocket.y - rocket.landerRocketHeight;

		Engine engine = fs.getEngine();
		engine.setInputValue("rightWall", Conf.SCREEN_WIDTH - x);
		engine.setInputValue("leftWall", x);
		engine.setInputValue("upperWall", y);
		engine.setInputValue("lowerWall", Conf.SCREEN_HEIGHT - y);

		engine.process();

		Double yOutputValue = engine.getOutputValue("yOutputMove");
		Double xOutputValue = engine.getOutputValue("xOutputMove");
		
		this.currentMove = new int[] {yOutputValue.intValue(), xOutputValue.intValue()};

		engine.restart();
		
		return this.currentMove;
	}

	@Override
	public int[] currentMove() {
		return this.currentMove;
	}

}
