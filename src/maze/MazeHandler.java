package maze;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MazeHandler extends JFrame implements ActionListener{
	private MazeMap maze;
	private JPanel controls;
	private JButton button;
	private JRadioButton smallButton;
	private JRadioButton mediumButton;
	private JRadioButton largeButton;
	private ButtonGroup sizes;
	private int WINDOW_WIDTH = 500;
	private int WINDOW_HEIGHT = 500;
	private int ROWS = 25;
	private int COLS = 25;
	private int SMALL = 25;
	private int MEDIUM = 51;
	private int LARGE = 101;
	public MazeHandler() {
		maze = new MazeMap(ROWS, COLS);
		drawGUI();
		//maze.generateMaze();
	}

	private void drawGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(new BorderLayout());
		controls = buttonPanel();
		this.add(controls, BorderLayout.SOUTH);
		this.add(maze, BorderLayout.CENTER);
		pack();
		this.setTitle("DFS Maze");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void makeMaze() {

		maze.updateSize(ROWS, COLS);
		maze.generateMaze();
		controls.setPreferredSize(new Dimension(maze.getSize().width, controls.getHeight()));
		pack();
	}

	private JPanel buttonPanel() {
		JPanel temp = new JPanel();
		//temp.setLayout(new GridLayout(1,3));
		temp.setLayout(new FlowLayout());
		sizes = new ButtonGroup();
		smallButton = new JRadioButton("Small: " + SMALL + "x" + SMALL);
		smallButton.addActionListener(this);
		mediumButton = new JRadioButton("Medium:" + MEDIUM + "x" + MEDIUM);
		mediumButton.addActionListener(this);
		largeButton = new JRadioButton("Large: " + LARGE + "x" + LARGE);
		largeButton.addActionListener(this);
		smallButton.setSelected(true);
		sizes.add(smallButton);
		sizes.add(mediumButton);
		sizes.add(largeButton);
		button = new JButton("Generate!");
		button.addActionListener(this);
		temp.add(smallButton);
		temp.add(mediumButton);
		temp.add(largeButton);
		temp.add(button);
		return temp;
	}

	public static void main(String[] args) {
		MazeHandler manager = new MazeHandler();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == smallButton) {
			ROWS = SMALL;
			COLS = SMALL;
		} else if(event.getSource() == mediumButton) {
			ROWS = MEDIUM;
			COLS = MEDIUM;
		} else if(event.getSource() == largeButton) {
			ROWS = LARGE;
			COLS = LARGE;
		}
		else if(event.getSource() == button) makeMaze();
	}
}
