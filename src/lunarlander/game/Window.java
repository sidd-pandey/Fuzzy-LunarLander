/*
 * Initialize frame
 */
package lunarlander.game;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private Window() {

		URL iconURL = getClass().getResource("resources/img/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		this.setTitle("Lunar Lander 1.1");
		this.setSize(Conf.SCREEN_WIDTH, Conf.SCREEN_HEIGHT); // 1280x720px
		this.setLocationRelativeTo(null); // Centered
		this.setResizable(false); // Not resizable

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Stop thread when user exists frame

		this.setContentPane(new Framework());

		this.setVisible(true);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // Run thread
			@Override
			public void run() {
				new Window();
			}
		});
	}
}