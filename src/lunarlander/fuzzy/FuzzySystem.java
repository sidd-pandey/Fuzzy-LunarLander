package lunarlander.fuzzy;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fuzzylite.Engine;
import com.fuzzylite.activation.General;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Constant;
import com.fuzzylite.term.Rectangle;
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import lunarlander.game.Conf;

public class FuzzySystem {

	private Engine engine;
	
	private int landerRocketWidth, landerRocketHeight;
	
	private double landingPlatformMid;
	private double landingSpaceWidth;
	
	public FuzzySystem(int landerRocketWidth, int landerRocketHeight, 
			double landingPlatformMid, double landingSpaceWidth) {
		
		this.landerRocketWidth = landerRocketWidth;
		this.landerRocketHeight = landerRocketHeight;
		this.landingPlatformMid = landingPlatformMid;
		this.landingSpaceWidth = landingSpaceWidth;
		
		initialize();
	    Logger.getLogger("java.awt").setLevel(Level.OFF);
	    Logger.getLogger("sun.awt").setLevel(Level.OFF);
	    Logger.getLogger("javax.swing").setLevel(Level.OFF);
	}

	private void initialize() {
		this.setEngine(new Engine());
		this.engine.setName("Fuzzy Pilot Engine");
		
		int w = Conf.SCREEN_WIDTH - landerRocketWidth, h = Conf.SCREEN_HEIGHT - landerRocketHeight; 
		double wLandingPlatform = Conf.SCREEN_WIDTH - (landerRocketWidth + landingSpaceWidth)/2;
		
		InputVariable lowerWall = new InputVariable();
		lowerWall.setName("lowerWall");
		lowerWall.setEnabled(true);
		lowerWall.setRange(0, h);
		lowerWall.setLockValueInRange(false);
		lowerWall.addTerm(new Trapezoid("near", 0, 0, h/10, h/8));
		lowerWall.addTerm(new Trapezoid("far", h/10, h/8, h, h));
		this.engine.addInputVariable(lowerWall);

		InputVariable upperWall = new InputVariable();
		upperWall.setName("upperWall");
		upperWall.setEnabled(true);
		upperWall.setRange(0, h);
		upperWall.setLockValueInRange(false);
		upperWall.addTerm(new Trapezoid("near", 0, 0, h/10, h/8));
		upperWall.addTerm(new Trapezoid("far", h/10, h/8, h, h));
		this.engine.addInputVariable(upperWall);
		
		InputVariable leftWall = new InputVariable();
		leftWall.setName("leftWall");
		leftWall.setEnabled(true);
		leftWall.setRange(0, w);
		leftWall.setLockValueInRange(false);
		leftWall.addTerm(new Trapezoid("near", 0, 0, w/25, w/20));
		leftWall.addTerm(new Trapezoid("far", w/25, w/20, w, w));
		this.engine.addInputVariable(leftWall);
		
		InputVariable rightWall = new InputVariable();
		rightWall.setName("rightWall");
		rightWall.setEnabled(true);
		rightWall.setRange(0, w);
		rightWall.setLockValueInRange(false);
		rightWall.addTerm(new Trapezoid("near", 0, 0, w/25, w/20));
		rightWall.addTerm(new Trapezoid("far", w/25, w/20, w, w));
		this.engine.addInputVariable(rightWall);
		
		InputVariable yspeed = new InputVariable();
		yspeed.setName("yspeed");
		yspeed.setEnabled(true);
		yspeed.setRange(-40, 40);
		yspeed.setLockValueInRange(false);
		yspeed.addTerm(new Rectangle("positiveLarge", 5, 40));
		yspeed.addTerm(new Rectangle("negativeLarge", -40, -5));
		this.engine.addInputVariable(yspeed);
		
		InputVariable xspeed = new InputVariable();
		xspeed.setName("xspeed");
		xspeed.setEnabled(true);
		xspeed.setRange(-60, 60);
		xspeed.setLockValueInRange(false);
		xspeed.addTerm(new Rectangle("positiveLarge", 5, 60));
		xspeed.addTerm(new Rectangle("negativeLarge", -60, -5));
		this.engine.addInputVariable(xspeed);
		
		InputVariable landingAllowed = new InputVariable();
		landingAllowed.setName("landingAllowed");
		landingAllowed.setEnabled(true);
		landingAllowed.setRange(-1, 1);
		landingAllowed.setLockValueInRange(false);
		landingAllowed.addTerm(new Rectangle("true", 0, 1));
		landingAllowed.addTerm(new Rectangle("false", -1, 0));
		this.engine.addInputVariable(landingAllowed);
		
		InputVariable xlandingPlatform = new InputVariable();
		xlandingPlatform.setName("xlandingPlatform");
		xlandingPlatform.setEnabled(true);
		xlandingPlatform.setRange(-wLandingPlatform, wLandingPlatform);
		xlandingPlatform.setLockValueInRange(false);
//		xlandingPlatform.addTerm(new Trapezoid("negativeFar", -wLandingPlatform, -wLandingPlatform, -landingPlatformMid, 0));
//		xlandingPlatform.addTerm(new Trapezoid("positiveFar", 0, landingPlatformMid, wLandingPlatform, wLandingPlatform));
		xlandingPlatform.addTerm(new Triangle("negativeFar", -wLandingPlatform, -landingPlatformMid, 0));
		xlandingPlatform.addTerm(new Triangle("positiveFar", 0, landingPlatformMid, wLandingPlatform));
		this.engine.addInputVariable(xlandingPlatform);

		InputVariable obstacleX = new InputVariable();
		obstacleX.setName("obstacleX");
		obstacleX.setEnabled(true);
		obstacleX.setRange(0, w);
		obstacleX.setLockValueInRange(false);
		obstacleX.addTerm(new Trapezoid("near", 48, 48, 75, 90));
//		obstacleX.addTerm(new Trapezoid("nearRight", 48, 60, w, w));
		this.engine.addInputVariable(obstacleX);
	
		InputVariable obstacleY = new InputVariable();
		obstacleY.setName("obstacleY");
		obstacleY.setEnabled(true);
		obstacleY.setRange(0, h);
		obstacleY.setLockValueInRange(false);
		obstacleY.addTerm(new Trapezoid("near", 48, 48, 150, 150));
//		obstacleY.addTerm(new Trapezoid("nearBottom", -60, -48, 0, 0));
		this.engine.addInputVariable(obstacleY);
		
		InputVariable isTop = new InputVariable();
		isTop.setName("isTop");
		isTop.setEnabled(true);
		isTop.setRange(-1, 1);
		isTop.setLockValueInRange(false);
		isTop.addTerm(new Rectangle("true", 0, 1));
		isTop.addTerm(new Rectangle("false", -1, 0));
		this.engine.addInputVariable(isTop);
		
		InputVariable isBottom = new InputVariable();
		isBottom.setName("isBottom");
		isBottom.setEnabled(true);
		isBottom.setRange(-1, 1);
		isBottom.setLockValueInRange(false);
		isBottom.addTerm(new Rectangle("true", 0, 1));
		isBottom.addTerm(new Rectangle("false", -1, 0));
		this.engine.addInputVariable(isBottom);
		
		InputVariable isLeft = new InputVariable();
		isLeft.setName("isLeft");
		isLeft.setEnabled(true);
		isLeft.setRange(-1, 1);
		isLeft.setLockValueInRange(false);
		isLeft.addTerm(new Rectangle("true", 0, 1));
		isLeft.addTerm(new Rectangle("false", -1, 0));
		this.engine.addInputVariable(isLeft);
		
		InputVariable isRight = new InputVariable();
		isRight.setName("isRight");
		isRight.setEnabled(true);
		isRight.setRange(-1, 1);
		isRight.setLockValueInRange(false);
		isRight.addTerm(new Rectangle("true", 0, 1));
		isRight.addTerm(new Rectangle("false", -1, 0));
		this.engine.addInputVariable(isRight);
		
		OutputVariable yOutputMove = new OutputVariable();
		yOutputMove.setName("yOutputMove");
		yOutputMove.setEnabled(true);
		yOutputMove.setRange(-1, 525);
		yOutputMove.setLockValueInRange(false);
		yOutputMove.setAggregation(null);
		yOutputMove.setDefuzzifier(new LargestWeightedValue());
		yOutputMove.setDefaultValue(Double.NaN);
		yOutputMove.setLockPreviousValue(true);
		yOutputMove.addTerm(new Constant("up", KeyEvent.VK_UP));
		yOutputMove.addTerm(new Constant("down", KeyEvent.VK_DOWN));
		yOutputMove.addTerm(new Constant("nothing", -1));
		getEngine().addOutputVariable(yOutputMove);
		
		OutputVariable xOutputMove = new OutputVariable();
		xOutputMove.setName("xOutputMove");
		xOutputMove.setEnabled(true);
		xOutputMove.setRange(-1, 525);
		xOutputMove.setLockValueInRange(false);
		xOutputMove.setAggregation(null);
		xOutputMove.setDefuzzifier(new LargestWeightedValue());
		xOutputMove.setDefaultValue(Double.NaN);
		xOutputMove.setLockPreviousValue(true);
		xOutputMove.addTerm(new Constant("left", KeyEvent.VK_LEFT));
		xOutputMove.addTerm(new Constant("right", KeyEvent.VK_RIGHT));
		xOutputMove.addTerm(new Constant("nothing", -1));
		getEngine().addOutputVariable(xOutputMove);
				
		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setName("rule block");
		ruleBlock.setDescription("");
		ruleBlock.setEnabled(true);
		ruleBlock.setConjunction(new Minimum());
		ruleBlock.setDisjunction(new Maximum());
		ruleBlock.setImplication(null);
		ruleBlock.setActivation(new General());
		
		ruleBlock.addRule(Rule.parse("if lowerWall is near and landingAllowed is false then yOutputMove is up", this.engine));
		ruleBlock.addRule(Rule.parse("if upperWall is near then yOutputMove is down", this.engine));
		ruleBlock.addRule(Rule.parse("if leftWall is near then xOutputMove is right", this.engine));
		ruleBlock.addRule(Rule.parse("if rightWall is near then xOutputMove is left", this.engine));
		
		ruleBlock.addRule(Rule.parse("if yspeed is positiveLarge then yOutputMove is up", this.engine));
		ruleBlock.addRule(Rule.parse("if yspeed is negativeLarge then yOutputMove is down", this.engine));
		ruleBlock.addRule(Rule.parse("if xspeed is positiveLarge and isTop is false then xOutputMove is left", this.engine));
		ruleBlock.addRule(Rule.parse("if xspeed is negativeLarge and isTop is false then xOutputMove is right", this.engine));
		
		ruleBlock.addRule(Rule.parse("if xlandingPlatform is negativeFar then xOutputMove is left", this.engine));
		ruleBlock.addRule(Rule.parse("if xlandingPlatform is positiveFar then xOutputMove is right", this.engine));
			
		ruleBlock.addRule(Rule.parse("if isTop is true and obstacleY is near then yOutputMove is up", this.engine));
		ruleBlock.addRule(Rule.parse("if isBottom is true and obstacleY is near then yOutputMove is down", this.engine));
		ruleBlock.addRule(Rule.parse("if isLeft is true and obstacleX is near then xOutputMove is left", this.engine));
		ruleBlock.addRule(Rule.parse("if isRight is true and obstacleX is near then xOutputMove is right", this.engine));
		
//		Random random = new Random();
//		int direction = 0;
//		if (direction == 0) {
//			ruleBlock.addRule(Rule.parse("if landingAllowed is true and isTop is true and obstacleY is near "
//				+ "then xOutputMove is left and yOutputMove is up", this.engine));
//			ruleBlock.addRule(Rule.parse("if landingAllowed is true and isTop is true and obstacleY is near "
//				+ "then xOutputMove is right and yOutputMove is up", this.engine));
//		}
//		else
//			ruleBlock.addRule(Rule.parse("if landingAllowed is true and isTop is true and obstacleY is near "
//					+ "then xOutputMove is left and yOutputMove is up", this.engine));
		getEngine().addRuleBlock(ruleBlock);

	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
