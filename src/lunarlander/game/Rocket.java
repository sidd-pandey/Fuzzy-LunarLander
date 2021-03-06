/*
 * Rocket physics and controls.
 */
package lunarlander.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import lunarlander.players.FuzzyPlayer;
import lunarlander.players.Player;

public class Rocket {

	private Random random; // Random X start position

	public int x; // X coordinate (2D)

	public int y; // Y coordinate (2D)

	public boolean landed; // Check if landed

	public boolean crashed; // Check if crashed

	private int speedAccelerating; // Acceleration

	public int maxLandingSpeed; // Max speed for land

	public double speedX; // Horizontal speed

	public double speedY; // Vertical speed

	public double speedGrav; // Gravity

	private BufferedImage landerRocket; // Lunar Lander

	private BufferedImage landerLanded; // Landed Lander

	private BufferedImage landerCrashed; // Crashed Lander

	private BufferedImage landerFlyingUp; // Lander going up Lander
	
	private BufferedImage landerFlyingDown; // Accelerating Lander
	
	private BufferedImage landerFlyingRight; // Lander flying left
	
	private BufferedImage landerFlyingLeft; // Lander flying right

	public int landerRocketWidth; // Read image width

	public int landerRocketHeight; // Read image height
	
	private Player pilot;

	private Landingspace landingSpace;
	
	private Obstacles obstacles;

	public Rocket(Landingspace landingSpace, Obstacles obstacles) // Gather rocket dimensions
	{
		initialize();
		loadcontent();
		
		this.landingSpace = landingSpace;
		this.obstacles = obstacles;
		
		this.pilot = new FuzzyPlayer(this);

//		this.pilot = new RandomPlayer();
		x = random.nextInt(Framework.frameWidth - landerRocketWidth); // X random start
		x = Framework.frameWidth / 2;
//		x = Framework.frameWidth - landerRocketWidth; // X start from leftmost position
//		x = landerRocketWidth;
//		y = Framework.frameHeight/2 - 64;
	}

	private void initialize() {
	random = new Random(); // Initialize random start

		speedAccelerating = 1;
		speedY = 1;
		speedGrav = -0.16;
		maxLandingSpeed = 5;
	}


	private void loadcontent() // Load resources
	{
		try {
			URL landerRocketURL = this.getClass().getResource("resources/img/landerRocket.png");
			landerRocket = ImageIO.read(landerRocketURL);
			landerRocketWidth = landerRocket.getWidth();
			landerRocketHeight = landerRocket.getHeight();

			URL landerLandedURL = this.getClass().getResource("resources/img/lander_landed.png");
			landerLanded = ImageIO.read(landerLandedURL);

			URL landerCrashedURL = this.getClass().getResource("resources/img/lander_crash.png");
			landerCrashed = ImageIO.read(landerCrashedURL);

			URL landerFlyingUpURL = this.getClass().getResource("resources/img/lander_fire_up.png");
			landerFlyingUp = ImageIO.read(landerFlyingUpURL);
			
			URL landerFlyingDownURL = this.getClass().getResource("resources/img/lander_fire_down.png");
			landerFlyingDown = ImageIO.read(landerFlyingDownURL);
			
			URL landerFlyingRightURL = this.getClass().getResource("resources/img/lander_fire_right.png");
			landerFlyingRight = ImageIO.read(landerFlyingRightURL);
			
			URL landerFlyingLeftURL = this.getClass().getResource("resources/img/lander_fire_left.png");
			landerFlyingLeft = ImageIO.read(landerFlyingLeftURL);
			
		} catch (IOException ex) {
			Logger.getLogger(Rocket.class.getName())
					.log(Level.SEVERE, null, ex);
		}
	}

	public void Update(){
		
		// if player is not null, let it pilot the rocket, else use keyboard
		if (this.pilot != null) {
			updateByPlayer(pilot.nextMove());
		}else updateByKeys();

		x += speedX;
		y += speedY;

	}
	
	private void updateByPlayer(int[] moves) {
		int yDirMove = moves[0];
		int xDirMove = moves[1];
		if (yDirMove == KeyEvent.VK_UP) { 
			speedY -= speedAccelerating;
		}else {
			speedY -= speedGrav;
		}
		if (yDirMove == KeyEvent.VK_DOWN){
			speedY += speedAccelerating;
		}
		if (xDirMove == KeyEvent.VK_LEFT){ // Key RIGHT
			speedX -= speedAccelerating;
		}
		if (xDirMove == KeyEvent.VK_RIGHT){ // Key LEFT
			speedX += speedAccelerating;
	        }
	}
	
	private void updateByKeys() {
		if (Control.keyboardKeyState(KeyEvent.VK_UP)) { // Key DOWN
			speedY -= speedAccelerating;;
		}else {
			speedY -= speedGrav;
		}
		if (Control.keyboardKeyState(KeyEvent.VK_DOWN)){ // Key UP
			speedY += speedAccelerating;
		}
		if (Control.keyboardKeyState(KeyEvent.VK_LEFT)){ // Key RIGHT
			speedX -= speedAccelerating;
		}
		if (Control.keyboardKeyState(KeyEvent.VK_RIGHT)){ // Key LEFT
			speedX += speedAccelerating;
	    }
		if (Control.keyboardKeyState(KeyEvent.VK_0)){ // Cheat
			speedY = 0;
			speedX = 0;
		}
	}
	

	public void draw(Graphics2D g2d) {
		
		if(this.pilot != null) drawBotControlledRocket(g2d);
		else drawKeyControlledRocket(g2d);
		
	}

	private void drawBotControlledRocket(Graphics2D g2d) {
		
		int[] currentMove = this.pilot.currentMove();
		int yDirMove = currentMove[0];
		int xDirMove = currentMove[1];
		
		if (landed) // Check if landed
		{
			g2d.drawImage(landerLanded, x, y, null);
		} else if (crashed) // Check if crashed
		{
			g2d.drawImage(landerCrashed, x, y, null);
		} else {
			if (yDirMove == KeyEvent.VK_DOWN) // Draw fly image
			g2d.drawImage(landerFlyingDown, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);

			if (yDirMove == KeyEvent.VK_UP) // Draw fly image
			g2d.drawImage(landerFlyingUp, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);

			if (xDirMove == KeyEvent.VK_LEFT) // Draw fly image
			g2d.drawImage(landerFlyingLeft, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);

			if (xDirMove == KeyEvent.VK_RIGHT) // Draw fly image
			g2d.drawImage(landerFlyingRight, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);
		}

	}

	private void drawKeyControlledRocket(Graphics2D g2d) {
		if (landed) // Check if landed
		{
			g2d.drawImage(landerLanded, x, y, null);
		} else if (crashed) // Check if crashed
		{
			g2d.drawImage(landerCrashed, x, y, null);
		} else {
			if (Control.keyboardKeyState(KeyEvent.VK_DOWN)) // Draw fly image
			g2d.drawImage(landerFlyingDown, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);

			if (Control.keyboardKeyState(KeyEvent.VK_UP)) // Draw fly image
			g2d.drawImage(landerFlyingUp, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);

			if (Control.keyboardKeyState(KeyEvent.VK_LEFT)) // Draw fly image
			g2d.drawImage(landerFlyingLeft, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);

			if (Control.keyboardKeyState(KeyEvent.VK_RIGHT)) // Draw fly image
			g2d.drawImage(landerFlyingRight, x, y, null);
			g2d.drawImage(landerRocket, x, y, null);
		}
		
	}
	
	public Landingspace getLandingSpace() {
		return this.landingSpace;
	}

	public Obstacles getObstacles() {
		return this.obstacles;
	}
	

}