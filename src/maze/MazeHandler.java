package maze;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MazeHandler extends JFrame{
	private MazeMap maze;
	private int WINDOW_WIDTH = 500;
	private int WINDOW_HEIGHT = 500;
	private int ROWS = 25;
	private int COLS = 25;
	public MazeHandler() {
		maze = new MazeMap(ROWS, COLS);
		drawGUI();
		maze.generateMaze();

	}
	
	private void drawGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLayout(new BorderLayout());
		this.add(maze, BorderLayout.CENTER);
		this.setTitle("DFS Maze");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		MazeHandler manager = new MazeHandler();
	}
}
