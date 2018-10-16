package lunarlander.fuzzy;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fuzzylite.Engine;
import com.fuzzylite.activation.General;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Binary;
import com.fuzzylite.term.Constant;
import com.fuzzylite.term.Rectangle;
import com.fuzzylite.term.Spike;
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
	
	public FuzzySystem(int landerRocketWidth, int landerRocketHeight, double landingPlatformMid, double landingSpaceWidth) {
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
		
		InputVariable rightWall = new InputVariable();
		rightWall.setName("rightWall");
		rightWall.setEnabled(true);
		rightWall.setRange(0, Conf.SCREEN_WIDTH);
		rightWall.setLockValueInRange(false);
		rightWall.addTerm(new Trapezoid("near", 0, 0, w/8 < w-landingPlatformMid ? w/8 : (w-landingPlatformMid)/3
				, w/8 < w-landingPlatformMid ? w/6 : w - landingPlatformMid));
		rightWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
//		rightWall.addTerm(new Trapezoid("near", 0, 0, w/3, 2*w/3));
//		rightWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		this.engine.addInputVariable(rightWall);
		
		InputVariable leftWall = new InputVariable();
		leftWall.setName("leftWall");
		leftWall.setEnabled(true);
		leftWall.setRange(0, Conf.SCREEN_WIDTH);
		leftWall.setLockValueInRange(false);
		leftWall.addTerm(new Trapezoid("near", 0, 0, w/8 < landingPlatformMid ? w/8 : landingPlatformMid/3
				, w/8 < landingPlatformMid ? w/6 : landingPlatformMid));
		leftWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
//		leftWall.addTerm(new Trapezoid("near", 0, 0, w/3, 2*w/3));
//		leftWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		this.engine.addInputVariable(leftWall);
		
		InputVariable upperWall = new InputVariable();
		upperWall.setName("upperWall");
		upperWall.setEnabled(true);
		upperWall.setRange(0, Conf.SCREEN_HEIGHT);
		upperWall.setLockValueInRange(false);
		upperWall.addTerm(new Trapezoid("near", 0, 0, h/4, h/3));
		upperWall.addTerm(new Trapezoid("far", h/6, 2*h/3, h, h));
//		upperWall.addTerm(new Trapezoid("near", 0, 0, h/3, 2*h/3));
//		upperWall.addTerm(new Trapezoid("far", h/3, 2*h/3, h, h));
		this.engine.addInputVariable(upperWall);
		
		InputVariable lowerWall = new InputVariable();
		lowerWall.setName("lowerWall");
		lowerWall.setEnabled(true);
		lowerWall.setRange(0, Conf.SCREEN_HEIGHT);
		lowerWall.setLockValueInRange(false);
		lowerWall.addTerm(new Trapezoid("near", 0, 0, h/4, h/3));
		lowerWall.addTerm(new Trapezoid("far", h/6, 2*h/3, h, h));
//		lowerWall.addTerm(new Trapezoid("near", 0, 0, h/3, 2*h/3));
//		lowerWall.addTerm(new Trapezoid("far", h/3, 2*h/3, h, h));
		this.engine.addInputVariable(lowerWall);
		
		InputVariable yspeed = new InputVariable();
		yspeed.setName("yspeed");
		yspeed.setEnabled(true);
		yspeed.setRange(-40, 40);
		yspeed.setLockValueInRange(false);
		yspeed.addTerm(new Trapezoid("postiveLarge", 10, 15, 40, 40));
		yspeed.addTerm(new Trapezoid("negativeLarge", -40, -40, -15, -10));
		this.engine.addInputVariable(yspeed);
		
		InputVariable xspeed = new InputVariable();
		xspeed.setName("xspeed");
		xspeed.setEnabled(true);
		xspeed.setRange(-60, 60);
		xspeed.setLockValueInRange(false);
		xspeed.addTerm(new Trapezoid("postiveLarge", 5, 15, 40, 40));
		xspeed.addTerm(new Trapezoid("negativeLarge", -40, -40, -15, -5));
		xspeed.addTerm(new Rectangle("small", -5, 5));
		this.engine.addInputVariable(xspeed);
		
		InputVariable xlandingPlatform = new InputVariable();
		xlandingPlatform.setName("xlandingPlatform");
		xlandingPlatform.setEnabled(true);
		xlandingPlatform.setRange(-Conf.SCREEN_WIDTH, Conf.SCREEN_WIDTH);
		xlandingPlatform.setLockValueInRange(false);
		xlandingPlatform.addTerm(new Triangle("negativeNear", -w, -landingPlatformMid, 0));
		xlandingPlatform.addTerm(new Triangle("positiveNear", 0, landingPlatformMid, w));
		xlandingPlatform.addTerm(new Rectangle("close", -landingSpaceWidth/2, landingSpaceWidth/2));
		this.engine.addInputVariable(xlandingPlatform);
		
		InputVariable landingMode = new InputVariable();
		landingMode.setName("landingMode");
		landingMode.setEnabled(true);
		landingMode.setRange(0, 1);
		landingMode.setLockValueInRange(false);
		landingMode.addTerm(new Rectangle("on", 1, 1));
		landingMode.addTerm(new Rectangle("off",0, 0));
		this.engine.addInputVariable(landingMode);
		
		OutputVariable yOutputMove = new OutputVariable();
		yOutputMove.setName("yOutputMove");
		yOutputMove.setDescription("");
		yOutputMove.setEnabled(true);
		yOutputMove.setRange(0, 525);
		yOutputMove.setLockValueInRange(false);
		yOutputMove.setAggregation(null);
		yOutputMove.setDefuzzifier(new LargestWeightedValue());
		yOutputMove.setDefaultValue(Double.NaN);
		yOutputMove.setLockPreviousValue(true);
		yOutputMove.addTerm(new Constant("up", KeyEvent.VK_UP));
		yOutputMove.addTerm(new Constant("down", KeyEvent.VK_DOWN));
		getEngine().addOutputVariable(yOutputMove);
		
		OutputVariable xOutputMove = new OutputVariable();
		xOutputMove.setName("xOutputMove");
		xOutputMove.setDescription("");
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
		
		OutputVariable landingModeStatus = new OutputVariable();
		landingModeStatus.setName("landingModeStatus");
		landingModeStatus.setDescription("");
		landingModeStatus.setEnabled(true);
		landingModeStatus.setRange(0, 1);
		landingModeStatus.setLockValueInRange(false);
		landingModeStatus.setAggregation(null);
		landingModeStatus.setDefuzzifier(new LargestWeightedValue());
		landingModeStatus.setDefaultValue(-1);
		landingModeStatus.setLockPreviousValue(true);
		landingModeStatus.addTerm(new Constant("on", 1));
		landingModeStatus.addTerm(new Constant("off", 0));
		getEngine().addOutputVariable(landingModeStatus);

		
		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setName("rule block");
		ruleBlock.setDescription("");
		ruleBlock.setEnabled(true);
		ruleBlock.setConjunction(new Minimum());
		ruleBlock.setDisjunction(new Maximum());
		ruleBlock.setImplication(null);
		ruleBlock.setActivation(new General());
		ruleBlock.addRule(Rule.parse("if rightWall is near then xOutputMove is left", this.engine));
		ruleBlock.addRule(Rule.parse("if leftWall is near then xOutputMove is right", this.engine));
		ruleBlock.addRule(Rule.parse("if upperWall is near then yOutputMove is down", this.engine));
		ruleBlock.addRule(Rule.parse("if lowerWall is near then yOutputMove is up", this.engine));
		
		ruleBlock.addRule(Rule.parse("if yspeed is postiveLarge then yOutputMove is up", this.engine));
		ruleBlock.addRule(Rule.parse("if yspeed is negativeLarge then yOutputMove is down", this.engine));
		
		ruleBlock.addRule(Rule.parse("if xspeed is postiveLarge then xOutputMove is left", this.engine));
		ruleBlock.addRule(Rule.parse("if xspeed is negativeLarge then xOutputMove is right", this.engine));
		
		ruleBlock.addRule(Rule.parse("if xlandingPlatform is negativeNear then "
				+ "xOutputMove is left and yOutputMove is down", this.engine));
		ruleBlock.addRule(Rule.parse("if xlandingPlatform is positiveNear then "
				+ "xOutputMove is right and yOutputMove is down", this.engine));
		
		ruleBlock.addRule(Rule.parse("if xlandingPlatform is close and xspeed is small then landingModeStatus is on", this.engine));
		
		getEngine().addRuleBlock(ruleBlock);

	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
