package lunarlander.fuzzy;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fuzzylite.Engine;
import com.fuzzylite.activation.General;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Constant;
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import lunarlander.game.Conf;

public class FuzzySystem {

	private Engine engine;
	
	public FuzzySystem() {
		initialize();
	    Logger.getLogger("java.awt").setLevel(Level.OFF);
	    Logger.getLogger("sun.awt").setLevel(Level.OFF);
	    Logger.getLogger("javax.swing").setLevel(Level.OFF);
	}

	private void initialize() {
		this.setEngine(new Engine());
		this.engine.setName("Fuzzy Pilot Engine");
		
		int w = Conf.SCREEN_WIDTH, h = Conf.SCREEN_HEIGHT;
		
		InputVariable rightWall = new InputVariable();
		rightWall.setName("rightWall");
		rightWall.setEnabled(true);
		rightWall.setRange(0, Conf.SCREEN_WIDTH);
		rightWall.setLockValueInRange(false);
//		rightWall.addTerm(new Trapezoid("near", 0, 0, w/6, w/4));
//		rightWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		rightWall.addTerm(new Trapezoid("near", 0, 0, w/3, 2*w/3));
		rightWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		this.engine.addInputVariable(rightWall);
		
		InputVariable leftWall = new InputVariable();
		leftWall.setName("leftWall");
		leftWall.setEnabled(true);
		leftWall.setRange(0, Conf.SCREEN_WIDTH);
		leftWall.setLockValueInRange(false);
//		leftWall.addTerm(new Trapezoid("near", 0, 0, w/6, w/4));
//		leftWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		leftWall.addTerm(new Trapezoid("near", 0, 0, w/3, 2*w/3));
		leftWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		this.engine.addInputVariable(leftWall);
		
		InputVariable upperWall = new InputVariable();
		upperWall.setName("upperWall");
		upperWall.setEnabled(true);
		upperWall.setRange(0, Conf.SCREEN_HEIGHT);
		upperWall.setLockValueInRange(false);
//		upperWall.addTerm(new Trapezoid("near", 0, 0, h/4, h/3));
//		upperWall.addTerm(new Trapezoid("far", h/6, 2*h/3, h, h));
		upperWall.addTerm(new Trapezoid("near", 0, 0, h/3, 2*h/3));
		upperWall.addTerm(new Trapezoid("far", h/3, 2*h/3, h, h));
		this.engine.addInputVariable(upperWall);
		
		InputVariable lowerWall = new InputVariable();
		lowerWall.setName("lowerWall");
		lowerWall.setEnabled(true);
		lowerWall.setRange(0, Conf.SCREEN_HEIGHT);
		lowerWall.setLockValueInRange(false);
//		lowerWall.addTerm(new Trapezoid("near", 0, 0, h/3, h/2));
//		lowerWall.addTerm(new Trapezoid("far", h/3, 2*h/3, h, h));
		lowerWall.addTerm(new Trapezoid("near", 0, 0, h/3, 2*h/3));
		lowerWall.addTerm(new Trapezoid("far", h/3, 2*h/3, h, h));
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
		xspeed.addTerm(new Trapezoid("postiveLarge", 20, 30, 60, 60));
		xspeed.addTerm(new Trapezoid("negativeLarge", -60, -60, -30, -20));
		this.engine.addInputVariable(xspeed);
		
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
		xOutputMove.setRange(0, 525);
		xOutputMove.setLockValueInRange(false);
		xOutputMove.setAggregation(null);
		xOutputMove.setDefuzzifier(new LargestWeightedValue());
		xOutputMove.setDefaultValue(Double.NaN);
		xOutputMove.setLockPreviousValue(true);
		xOutputMove.addTerm(new Constant("left", KeyEvent.VK_LEFT));
		xOutputMove.addTerm(new Constant("right", KeyEvent.VK_RIGHT));
		getEngine().addOutputVariable(xOutputMove);

		
		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setName("");
		ruleBlock.setDescription("");
		ruleBlock.setEnabled(true);
		ruleBlock.setConjunction(null);
		ruleBlock.setDisjunction(null);
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
		
		getEngine().addRuleBlock(ruleBlock);

	}
	
//	public static void main(String[] args) {
//		
//		FuzzySystem fz = new FuzzySystem();
//		Engine engine = fz.getEngine();
//		
//		int x = 100, y = 100;
//		engine.setInputValue("rightWall", Conf.SCREEN_WIDTH - x);
//		engine.setInputValue("leftWall", x);
//		engine.setInputValue("upperWall", y);
//		engine.setInputValue("lowerWall", Conf.SCREEN_HEIGHT - y);
//		
//		engine.process();
//		
//		System.out.println(engine.getInputVariable("rightWall").getValue());
//		
//		double outputValue = engine.getOutputValue("outputMove");
//		System.out.println(outputValue);
//		
//	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
