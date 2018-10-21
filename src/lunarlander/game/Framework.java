/*
 * Game functions and actions
 */
package lunarlander.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.fuzzylite.FuzzyLite;


@SuppressWarnings("serial")
public class Framework extends Control {

	public static int frameWidth;

	public static int frameHeight;

	public static final long SECINNANO = 1000000000L;

	public static final long MILISECINNANO = 1000000L;

	private final double GAME_FPS = 30; // Lower value = easy; Higher value = hard

	private final double GAME_UPDATE = SECINNANO / GAME_FPS;

	public static enum GameState {
		STARTING, DISPLAY, MENU, RUNNING, GAMEOVER
	}

	public static GameState gameState;

	private double gameTime; // "Scientific" time

	private double lastTime;


	private Main game;
	private JFrame mainFrame;


	private BufferedImage lunarLander;

	public Framework(JFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		gameState = GameState.DISPLAY;
		setLogging();
		Thread gameThread = new Thread() {

			@Override
			public void run() {
				gameloop();
			}
		};
		gameThread.start();
	}

	private void initialize() {

	}
	

	private void loadcontent() {
		try {

			URL lunarLanderUrl = this.getClass().getResource("resources/img/menu.png");
			lunarLander = ImageIO.read(lunarLanderUrl);
		} catch (IOException ex) {
			Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null,
					ex);
		}

	}

	private void gameloop() {
		long visualizingTime = 0, lastVisualizingTime = System.nanoTime();

		long beginTime, 
		timeTaken;

		double timeLeft;

		while (true) {
			beginTime = System.nanoTime();
			
			if (gameState == GameState.MENU) {
				JMenu jMenu = mainFrame.getJMenuBar().getMenu(0);
				JMenuItem configItem = (JMenuItem)jMenu.getMenuComponent(0);
				configItem.setEnabled(true);
			}else {
				JMenu jMenu = mainFrame.getJMenuBar().getMenu(0);
				JMenuItem configItem = (JMenuItem)jMenu.getMenuComponent(0);
				configItem.setEnabled(false);
			}
			
			if (gameState == GameState.RUNNING) {
				JMenu jMenu = mainFrame.getJMenuBar().getMenu(0);
				JMenuItem configItem = (JMenuItem)jMenu.getMenuComponent(1);
				configItem.setEnabled(true);
			}else {
				JMenu jMenu = mainFrame.getJMenuBar().getMenu(0);
				JMenuItem configItem = (JMenuItem)jMenu.getMenuComponent(1);
				configItem.setEnabled(false);
			}
			
			switch (gameState) {
			case RUNNING:
				gameTime += System.nanoTime() - lastTime;
				game.UpdateGame(gameTime, mousePosition());
				lastTime = System.nanoTime();
				break;
			case GAMEOVER:
				break;
			case MENU:
				break;
			case STARTING:
				initialize();
				loadcontent();
				gameState = GameState.MENU;
				break;
			case DISPLAY:
				if (this.getWidth() > 1 && visualizingTime > SECINNANO) {
					frameWidth = this.getWidth();
					frameHeight = this.getHeight();
					gameState = GameState.STARTING;
				} else {
					visualizingTime += System.nanoTime() - lastVisualizingTime;
					lastVisualizingTime = System.nanoTime();
				}
				break;
			}

			repaint();

			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (GAME_UPDATE - timeTaken) / MILISECINNANO;

			if (timeLeft < 10)
				timeLeft = 10;
			try {
				Thread.sleep((long) timeLeft);
			} catch (InterruptedException ex) {
			}
		}
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public void draw(Graphics2D g2d) {
		switch (gameState) {
		case RUNNING:
			game.draw(g2d, mousePosition());
			break;
		case GAMEOVER:
			game.drawgameover(g2d, mousePosition(), gameTime);
			break;
		case MENU:
			g2d.drawImage(lunarLander, 0, 0, frameWidth, frameHeight, null);
			break;
		}
	}

	private void newGame() {
		gameTime = 0;
		lastTime = System.nanoTime();

		game = new Main();


	}

	private void restartGame() {
		gameState = GameState.MENU;
	}

	private Point mousePosition() {
		try {
			Point mp = this.getMousePosition();

			if (mp != null)
				return this.getMousePosition();
			else
				return new Point(0, 0);
		} catch (Exception e) {
			return new Point(0, 0);
		}
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public void keyReleasedFramework(KeyEvent e) {
		switch (gameState) {
		case MENU:
			newGame();
			break;
		case GAMEOVER:
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				restartGame();
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	
	private void setLogging() {
		FuzzyLite.setLogging(true);
	    FuzzyLite.setDebugging(true);
	    FuzzyLite.logger().setFilter(new Filter() {
			@Override
			public boolean isLoggable(LogRecord record) {
				return record.getSourceClassName().equals("com.fuzzylite.rule.Rule");
			}
		});
	    FuzzyLite.logger().setUseParentHandlers(false);
	    final JLabel logLabel = (JLabel)mainFrame.getJMenuBar().getComponent(1);
	    FuzzyLite.logger().addHandler(new LogHandler(logLabel));
	    Logger.getLogger("java.awt").setLevel(Level.OFF);
	    Logger.getLogger("sun.awt").setLevel(Level.OFF);
	    Logger.getLogger("javax.swing").setLevel(Level.OFF);	
	}
}