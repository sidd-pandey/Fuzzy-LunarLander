package lunarlander.game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConfigMenu implements ActionListener {

	JFrame parent;
	
	public ConfigMenu(JFrame parent) {
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JDialog dialog = new JDialog(parent, "Configurations", false);
		dialog.add(tabbedPane());
		dialog.setSize(600, 400);
		dialog.setVisible(true);
	}

	private JTabbedPane tabbedPane() {
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JComponent initGameConfig = getInitConfigPanel();
		tabbedPane.addTab("Coordinates & Obstacles", initGameConfig);
		
		JComponent rulesConfig = getRulesConfigPanel();
		tabbedPane.addTab("Fuzzy Rules", rulesConfig);
		
		return tabbedPane;
	}
	
	private JPanel getInitConfigPanel() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel screenSizeLabel = new JLabel("Screen size is fixed at " + Conf.SCREEN_WIDTH + " X " + Conf.SCREEN_HEIGHT);
		panel.add(screenSizeLabel);
		
		JPanel rocketCoordinatesPanel = new JPanel(new GridLayout(1, 4));
		rocketCoordinatesPanel.add(new JLabel("Rocket's Inital (X, Y)"));
		JTextField rocketXTextField = new JTextField(10);
		rocketCoordinatesPanel.add(rocketXTextField);
		JTextField rocketYTextField = new JTextField(10);
		rocketCoordinatesPanel.add(rocketYTextField);
		JButton rocketUpdateButton = new JButton("Update");
		rocketCoordinatesPanel.add(rocketUpdateButton);
		panel.add(rocketCoordinatesPanel);
		
		panel.add(Box.createVerticalStrut(30));
		
		JPanel platformCoordinatePanel = new JPanel(new GridLayout(1, 4));
		platformCoordinatePanel.add(new JLabel("Platform's X Coordinate"));
		JTextField pltXTextField = new JTextField(10);
		platformCoordinatePanel.add(pltXTextField);
		platformCoordinatePanel.add(new JLabel());
		JButton pltUpdateButton = new JButton("Update");
		platformCoordinatePanel.add(pltUpdateButton);
		panel.add(platformCoordinatePanel);
		
		panel.add(Box.createVerticalStrut(30));
		
		JPanel obstaclesLocPanel = new JPanel();
		obstaclesLocPanel.setLayout(new GridLayout(1, 4));
		obstaclesLocPanel.add(new JLabel("Obstacle's Location"));
		JTextArea obstacleSpecArea = new JTextArea(10, 5);
		JScrollPane sp = new JScrollPane(obstacleSpecArea);
		obstaclesLocPanel.add(sp);;
		obstaclesLocPanel.add(new JLabel());
		
		JButton obstacleUpdateButton = new JButton("Update");
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(4, 1));
		temp.add(new JLabel());
		temp.add(new JLabel());
		temp.add(obstacleUpdateButton);
		obstaclesLocPanel.add(temp);
		panel.add(obstaclesLocPanel);
		
		
		panel.add(Box.createVerticalStrut(100));
		
		return panel;
	}
	
	private JPanel getRulesConfigPanel() {
		
		return new JPanel();
	}

}
