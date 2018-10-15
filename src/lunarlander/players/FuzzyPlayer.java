package lunarlander.players;

import com.fuzzylite.Engine;

import lunarlander.fuzzy.FuzzySystem;
import lunarlander.game.Conf;
import lunarlander.game.Rocket;

public class FuzzyPlayer implements Player {

	private FuzzySystem fs = new FuzzySystem();
	private Rocket rocket;
	private int currentMove;

	public FuzzyPlayer(Rocket rocket) {
		this.rocket = rocket;
	}

	@Override
	public int nextMove() {

		int x = rocket.x - rocket.landerRocketWidth;
		int y = rocket.y - rocket.landerRocketHeight;

		Engine engine = fs.getEngine();
		engine.setInputValue("rightWall", Conf.SCREEN_WIDTH - x);
		engine.setInputValue("leftWall", x);
		engine.setInputValue("upperWall", y);
		engine.setInputValue("lowerWall", Conf.SCREEN_HEIGHT - y);

		engine.process();

		Double outputValue = engine.getOutputValue("outputMove");
		this.currentMove = outputValue.intValue();
		System.out.println(this.currentMove);
		engine.restart();
		
		return this.currentMove;
	}

	@Override
	public int currentMove() {
		return this.currentMove;
	}

}
