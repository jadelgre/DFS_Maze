package maze;

import java.awt.BorderLayout;
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
	private int WINDOW_WIDTH = 500;
	private int WINDOW_HEIGHT = 500;
	private int ROWS = 25;
	private int COLS = 25;
	private int SMALL = 25;
	private int MEDIUM = 51;
	private int LARGE = 251;
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
		this.setTitle("DFS Maze");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void makeMaze() {
		//maze = new MazeMap(ROWS, COLS);
		maze.generateMaze();
		//this.add(maze, BorderLayout.CENTER);
		repaint();
	}
	
	private JPanel buttonPanel() {
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(1,3));
		ButtonGroup sizes = new ButtonGroup();
		JRadioButton small = new JRadioButton("Small: " + SMALL + "x" + SMALL);
		JRadioButton medium = new JRadioButton("Medium:" + MEDIUM + "x" + MEDIUM);
		JRadioButton large = new JRadioButton("Large: " + LARGE + "x" + LARGE);
		small.setSelected(true);
		sizes.add(small);
		sizes.add(medium);
		sizes.add(large);
		button = new JButton("Generate!");
		button.addActionListener(this);
		temp.add(small);
		temp.add(medium);
		temp.add(large);
		temp.add(button);
		return temp;
	}
	
	public static void main(String[] args) {
		MazeHandler manager = new MazeHandler();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == button) makeMaze();
	}
}
