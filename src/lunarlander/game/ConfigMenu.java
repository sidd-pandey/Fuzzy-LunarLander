package lunarlander.game;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
		
		JPanel rocketCoordinatesPanel = new JPanel(new GridLayout(1, 5));
		
		rocketCoordinatesPanel.add(new JLabel("Rocket's Inital (X, Y)"));
		
		JTextField rocketXTextField = new JTextField(10);
		rocketXTextField.setText(""+Conf.get().getRocketX());
		rocketCoordinatesPanel.add(rocketXTextField);
		
		JTextField rocketYTextField = new JTextField(10);
		rocketYTextField.setText(""+Conf.get().getRocketY());
		rocketCoordinatesPanel.add(rocketYTextField);
		
		JButton rocketUpdateButton = new JButton("Update");
		rocketCoordinatesPanel.add(rocketUpdateButton);
		rocketUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String xStr = rocketXTextField.getText();
				String yStr = rocketYTextField.getText();
				if (xStr != null && !xStr.isEmpty()) {
					xStr = xStr.trim();
					Conf.get().setRocketX(Integer.parseInt(xStr));
				}
				if (yStr != null && !yStr.isEmpty()) {
					yStr = yStr.trim();
					Conf.get().setRocketY(Integer.parseInt(yStr));
				}
			}
		});
		panel.add(rocketCoordinatesPanel);
		
		panel.add(Box.createVerticalStrut(30));
		
		JPanel platformCoordinatePanel = new JPanel(new GridLayout(1, 4));
		platformCoordinatePanel.add(new JLabel("Platform's X Coordinate"));
		
		JTextField pltXTextField = new JTextField(10);
		pltXTextField.setText("" + Conf.get().getPltX());
		platformCoordinatePanel.add(pltXTextField);
		platformCoordinatePanel.add(new JLabel());
		JButton pltUpdateButton = new JButton("Update");
		pltUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String xStr = pltXTextField.getText();
				if (xStr != null && !xStr.isEmpty()) {
					xStr = xStr.trim();
					Conf.get().setPltX(Integer.parseInt(xStr));
				}
			}
		});
		
		platformCoordinatePanel.add(pltUpdateButton);
		panel.add(platformCoordinatePanel);
		
		panel.add(Box.createVerticalStrut(30));
		
		JPanel obstaclesLocPanel = new JPanel();
		obstaclesLocPanel.setLayout(new GridLayout(1, 4));
		obstaclesLocPanel.add(new JLabel("Obstacle's Location"));
		JTextArea obstacleSpecArea = new JTextArea(10, 5);
		obstacleSpecArea.setText(Conf.get().getObstaclesAsStr());
		JScrollPane sp = new JScrollPane(obstacleSpecArea);
		obstaclesLocPanel.add(sp);;
		obstaclesLocPanel.add(new JLabel());
		
		JButton obstacleUpdateButton = new JButton("Update");
		obstacleUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String obstacleStr = obstacleSpecArea.getText();
				if (obstacleStr != null) {
					String[] pointsStr = obstacleStr.split("\n");
					List<Point> centers = new ArrayList<>();
					for (String str : pointsStr) {
						 centers.add(new Point(Integer.parseInt(str.split(",")[0]), 
								 Integer.parseInt(str.split(",")[1])));
					}
					Conf.get().setObstalceCenters(centers);
				}
			}
		});
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(4, 1));
		temp.add(new JLabel());
		temp.add(new JLabel());
		temp.add(obstacleUpdateButton);
		obstaclesLocPanel.add(temp);
		panel.add(obstaclesLocPanel);
		
		JPanel randomCheckPanel = new JPanel(new GridLayout(1,  4));
		randomCheckPanel.add(new JLabel("Random"));

		JCheckBox randomCheckBox = new JCheckBox();
		randomCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (randomCheckBox.isSelected()) {
					rocketXTextField.setEnabled(false);
					rocketYTextField.setEnabled(false);
					pltXTextField.setEnabled(false);
					obstacleSpecArea.setEnabled(false);
					Conf.get().setRandom(true);
					Conf.reset();
				}else if(!randomCheckBox.isSelected()) {
					rocketXTextField.setEnabled(true);
					rocketYTextField.setEnabled(true);
					pltXTextField.setEnabled(true);
					obstacleSpecArea.setEnabled(true);
					Conf.get().setRandom(false);
				}
			}
		});
		randomCheckBox.setSelected(Conf.get().isRandom());
		randomCheckPanel.add(randomCheckBox);
		
		randomCheckPanel.add(new JLabel());
		randomCheckPanel.add(new JLabel());

		panel.add(randomCheckPanel);
		
		panel.add(Box.createVerticalStrut(100));
		
		return panel;
	}
	
	private JPanel getRulesConfigPanel() {	
		return new JPanel();
	}

}
