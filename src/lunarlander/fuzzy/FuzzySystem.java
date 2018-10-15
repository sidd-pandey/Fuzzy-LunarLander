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
		rightWall.addTerm(new Trapezoid("near", 0, 0, w/3, 2*w/3));
		rightWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		this.engine.addInputVariable(rightWall);
		
		InputVariable leftWall = new InputVariable();
		leftWall.setName("leftWall");
		leftWall.setEnabled(true);
		leftWall.setRange(0, Conf.SCREEN_WIDTH);
		leftWall.setLockValueInRange(false);
		leftWall.addTerm(new Trapezoid("near", 0, 0, w/3, 2*w/3));
		leftWall.addTerm(new Trapezoid("far", w/3, 2*w/3, w, w));
		this.engine.addInputVariable(leftWall);
		
		InputVariable upperWall = new InputVariable();
		upperWall.setName("upperWall");
		upperWall.setEnabled(true);
		upperWall.setRange(0, Conf.SCREEN_HEIGHT);
		upperWall.setLockValueInRange(false);
		upperWall.addTerm(new Trapezoid("near", 0, 0, h/3, 2*h/3));
		upperWall.addTerm(new Trapezoid("far", h/3, 2*h/3, h, h));
		this.engine.addInputVariable(upperWall);
		
		InputVariable lowerWall = new InputVariable();
		lowerWall.setName("lowerWall");
		lowerWall.setEnabled(true);
		lowerWall.setRange(0, Conf.SCREEN_HEIGHT);
		lowerWall.setLockValueInRange(false);
		lowerWall.addTerm(new Trapezoid("near", 0, 0, h/2, 2*h/3));
		lowerWall.addTerm(new Trapezoid("far", h/3, 2*h/3, h, h));
		this.engine.addInputVariable(lowerWall);
		
		OutputVariable outputMove = new OutputVariable();
		outputMove.setName("outputMove");
		outputMove.setDescription("");
		outputMove.setEnabled(true);
		outputMove.setRange(0, 525);
		outputMove.setLockValueInRange(false);
		outputMove.setAggregation(null);
		outputMove.setDefuzzifier(new LargestWeightedValue());
		outputMove.setDefaultValue(Double.NaN);
		outputMove.setLockPreviousValue(true);
		outputMove.addTerm(new Constant("up", KeyEvent.VK_UP));
		outputMove.addTerm(new Constant("down", KeyEvent.VK_DOWN));
		outputMove.addTerm(new Constant("left", KeyEvent.VK_LEFT));
		outputMove.addTerm(new Constant("right", KeyEvent.VK_RIGHT));
		getEngine().addOutputVariable(outputMove);
		
		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setName("");
		ruleBlock.setDescription("");
		ruleBlock.setEnabled(true);
		ruleBlock.setConjunction(null);
		ruleBlock.setDisjunction(null);
		ruleBlock.setImplication(null);
		ruleBlock.setActivation(new General());
//		ruleBlock.addRule(Rule.parse("if rightWall is near then outputMove is left", this.engine));
//		ruleBlock.addRule(Rule.parse("if leftWall is near then outputMove is right", this.engine));
		ruleBlock.addRule(Rule.parse("if upperWall is near then outputMove is down", this.engine));
		ruleBlock.addRule(Rule.parse("if lowerWall is near then outputMove is up", this.engine));
		
		getEngine().addRuleBlock(ruleBlock);

	}
	
	public static void main(String[] args) {
		
		FuzzySystem fz = new FuzzySystem();
		Engine engine = fz.getEngine();
		
		int x = 100, y = 100;
		engine.setInputValue("rightWall", Conf.SCREEN_WIDTH - x);
		engine.setInputValue("leftWall", x);
		engine.setInputValue("upperWall", y);
		engine.setInputValue("lowerWall", Conf.SCREEN_HEIGHT - y);
		
		engine.process();
		
		System.out.println(engine.getInputVariable("rightWall").getValue());
		
		double outputValue = engine.getOutputValue("outputMove");
		System.out.println(outputValue);
		
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
