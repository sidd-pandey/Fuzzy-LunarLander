/*
 * Landing area
 */
package lunarlander.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Landingspace {

	public int x; // X coordinate

	public int y; // Y coordinate

	private BufferedImage landingSpace;

	public int landingSpaceWidth;
	public int landingSpaceHeight;

	public Landingspace() {
		initialize();
		loadcontent();
		this.x = Conf.get().getPltX();
	}

	private void initialize() {
		this.y = (int) (Framework.frameHeight * 0.95); // 95% of frame height
	}

	private void loadcontent() {
		try {
			URL landingSpaceUrl = this.getClass().getResource("resources/img/landing_area.png");
			landingSpace = ImageIO.read(landingSpaceUrl);
			landingSpaceWidth = landingSpace.getWidth();
		} catch (IOException ex) {
			Logger.getLogger(Landingspace.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(landingSpace, x, y, null);
	}

}