/*
 * Initialize frame
 */
package lunarlander.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		
		this.createMenu();
		
		this.setContentPane(new Framework(this));

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
	
	private void createMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenuItem configItem = new JMenuItem("Configurations");
		configItem.addActionListener(new ConfigMenu(this));
		gameMenu.add(configItem);
		
		JMenuItem killGameItem = new JMenuItem("Kill Game");
		killGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Framework.gameState = Framework.GameState.GAMEOVER;
				Conf.reset();
			}
		});
		gameMenu.add(killGameItem);
		
		menubar.add(gameMenu);
		this.setJMenuBar(menubar);
		
	}
}