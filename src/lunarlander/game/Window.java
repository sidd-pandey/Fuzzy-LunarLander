/*
 * Initialize frame
 */
package lunarlander.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private Window() {
		
		URL iconURL = getClass().getResource("resources/img/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		this.setTitle("Lunar Lander 2.0");
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
		
		JCheckBoxMenuItem playerModeMenu = new JCheckBoxMenuItem("Player Mode");
		playerModeMenu.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (playerModeMenu.isSelected()) {
					Conf.get().setPlayerMode(true);
				}else if (!playerModeMenu.isSelected()) {
					Conf.get().setPlayerMode(false);
				}
			}
		});
		gameMenu.add(playerModeMenu);
		
		menubar.add(gameMenu);
		
		JLabel logLabel = new JLabel("");
		logLabel.setFont(new Font("Consolas", 0, 14));
		logLabel.setMinimumSize(new Dimension(1000, 14));
		logLabel.setMaximumSize(new Dimension(1000, 14));
		
		JLabel lrLabel = new JLabel("");
		lrLabel.setBorder(new CompoundBorder(lrLabel.getBorder(), 
				new EmptyBorder(0,5,0,10)));
		lrLabel.setFont(new Font("Consolas", 1, 12));
		lrLabel.setForeground(Color.DARK_GRAY);
		lrLabel.setMinimumSize(new Dimension(100, 14));
		lrLabel.setMaximumSize(new Dimension(100, 14));
		
		JLabel udLabel = new JLabel("");
		udLabel.setFont(new Font("Consolas", 1, 12));
		udLabel.setBorder(new CompoundBorder(lrLabel.getBorder(), 
				new EmptyBorder(0,5,0, 0)));
		udLabel.setForeground(Color.DARK_GRAY);
		udLabel.setMinimumSize(new Dimension(100, 14));
		udLabel.setMaximumSize(new Dimension(100, 14));
		
		
		menubar.add(logLabel);
		menubar.add(lrLabel);
		menubar.add(udLabel);
		
		this.setJMenuBar(menubar);
		
	}
}