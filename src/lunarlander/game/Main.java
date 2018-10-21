/**
 * @author Rey Koxha 
 * Main game
 */
package lunarlander.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Main {

	private Rocket rocket;

	private Landingspace landingSpace; // Area to land

	private BufferedImage background; // Background image

	private BufferedImage failed; // Failed image
	
	private Obstacles obstacles;

	public Main() {

		Thread threadForInitGame = new Thread() {

			@Override
			public void run() {
				initialize(); // Set all variables

				loadcontent(); // Get game files

				Framework.gameState = Framework.GameState.RUNNING;
			}
		};
		threadForInitGame.start();
	}

	private void initialize() { // Start new game
		landingSpace = new Landingspace();
		obstacles = new Obstacles();
		rocket = new Rocket(landingSpace, obstacles);
	}

	private void loadcontent() {
		try {

			URL backgroundUrl = this.getClass().getResource("resources/img/background.png");
			background = ImageIO.read(backgroundUrl);

			URL failedUrl = this.getClass().getResource("resources/img/failed.png");
			failed = ImageIO.read(failedUrl);

		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void UpdateGame(double gameTime, Point mousePosition) // Get position of rocket
	{
		rocket.Update();
		//check for x coordinate
		if (rocket.x < 0 || rocket.x > Framework.frameWidth - rocket.landerRocketWidth || 
				rocket.y < 0 || rocket.y > Framework.frameHeight-10) {
			rocket.crashed = true;
			Framework.gameState = Framework.GameState.GAMEOVER;
		}
		
		if (obstacles.checkForCollision(rocket.x+2, rocket.y+2, rocket.landerRocketWidth-2, rocket.landerRocketHeight-2)) {
			rocket.crashed = true;
			Framework.gameState = Framework.GameState.GAMEOVER;
		}

		if (rocket.y + rocket.landerRocketHeight - 10 > landingSpace.y) {
			if ((rocket.x > landingSpace.x)
					&& (rocket.x < landingSpace.x + landingSpace.landingSpaceWidth - rocket.landerRocketWidth)) {
				if (rocket.speedY <= rocket.maxLandingSpeed)
					rocket.landed = true;
				else rocket.crashed = true;
			}
			Framework.gameState = Framework.GameState.GAMEOVER;
		}
		
		if (Framework.gameState == Framework.GameState.GAMEOVER) {
			Conf.reset();
		}
	}

	public void draw(Graphics2D g2d, Point mousePosition) {
		g2d.drawImage(background, 0, 0, Framework.frameWidth,
				Framework.frameHeight, null);
		landingSpace.draw(g2d);
		rocket.draw(g2d);
		obstacles.draw(g2d);
	}

	public void drawgameover(Graphics2D g2d, Point mousePosition,
			double gameTime) // Outputs if landed or crashed
	{
		draw(g2d, mousePosition);

		if (rocket.landed) {
			g2d.setColor(Color.green);
			g2d.drawString("Congrats!", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3);
			g2d.drawString("You landed in " + gameTime / Framework.SECINNANO + " seconds.", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 20);
			g2d.drawString("Press Enter to return to the menu.", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 60);
		} else {
			g2d.drawImage(failed, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
			g2d.setColor(Color.red);
			g2d.drawString("Fail!", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 70);
			g2d.drawString("Press Enter to return to the main menu.", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 90);
		}
	}
}