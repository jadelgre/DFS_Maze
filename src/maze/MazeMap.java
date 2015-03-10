package maze;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import javax.swing.JPanel;

import maze.MazeCell.CellType;

public class MazeMap extends JPanel {
	private ArrayList<ArrayList<MazeCell>> grid;
	private ArrayList<MazeCell> visited;
	private Map<MazeCell,ArrayList<MazeCell>> adj;
	private Stack<MazeCell> pile;
	private MazeCell start;
	private MazeCell finish;
	private Random rand;
	private int numRows;
	private int numCols;
	private boolean reachedFinish = false;

	public MazeMap(int rows, int cols) {
		numRows = rows;
		numCols = cols;
		createGrid();
		this.setPreferredSize(new Dimension(510,510));
		this.setMinimumSize(new Dimension(510,510));
	}

	private void createGrid() {
		grid = new ArrayList<ArrayList<MazeCell>>();
		for(int i = 0; i < numRows; i++){
			ArrayList<MazeCell> temp = new ArrayList<MazeCell>();
			for(int j = 0; j < numCols; j++) {
				temp.add(new MazeCell(i, j));
			}
			grid.add(temp);
		}
		calcAdjacencies(1);
	}

	private void calcAdjacencies(int offset) {
		adj = new HashMap<MazeCell, ArrayList<MazeCell>>();
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++){
				ArrayList<MazeCell> temp = new ArrayList<MazeCell>();
				MazeCell here = getCellAt(i, j);	//test each direction with isValidMove
				if(isValidMove(here, i-offset, j))
					temp.add(getCellAt(i-offset, j));
				if(isValidMove(here, i+offset, j))
					temp.add(getCellAt(i+offset, j));
				if(isValidMove(here, i, j-offset))
					temp.add(getCellAt(i, j-offset));
				if(isValidMove(here, i, j+offset)){
					temp.add(getCellAt(i, j+offset));
				}
				adj.put(here, temp);
			}
		}
	}

	public void generateMaze() {
		createGrid();
		visited = new ArrayList<MazeCell>();
		pile = new Stack<MazeCell>();
		// get a random edge cell
		rand = new Random(numRows);
		int randomNum = rand.nextInt();
		start = getCellAt(1, 1);
		randomNum = rand.nextInt();
		finish = getCellAt(numRows-2, numCols-2);
		finish.setType(CellType.EXIT);
		mazeRecurse(start);
		start.setType(CellType.ENTRANCE);
	}

	private void mazeRecurse(MazeCell currentCell) { 

		if(!currentCell.equals(finish)) {
			pile.push(currentCell);
			currentCell.setType(CellType.PATH);
			visited.add(currentCell);
			rand = new Random();
			int dir = rand.nextInt(4);
			MazeCell temp;
			Boolean unvisited = true;
			for(int i = 0; i < 4; i++) {
				switch (dir){
				case 0: 
					temp = getCellAt(currentCell.getRow() - 2, currentCell.getCol());
					if(!visited.contains(temp) && temp != null){
						getCellAt(currentCell.getRow() - 1, currentCell.getCol()).setType(CellType.PATH);
						mazeRecurse(temp);
						unvisited = false;
					}
				case 1:
					temp = getCellAt(currentCell.getRow() + 2, currentCell.getCol());
					if(!visited.contains(temp) && temp != null){
						getCellAt(currentCell.getRow() + 1, currentCell.getCol()).setType(CellType.PATH);
						mazeRecurse(temp);
						unvisited = false;
					}
				case 2:
					temp = getCellAt(currentCell.getRow(), currentCell.getCol() + 2);
					if(!visited.contains(temp) && temp != null) {
						getCellAt(currentCell.getRow(), currentCell.getCol() + 1).setType(CellType.PATH);
						mazeRecurse(temp);
						unvisited = false;
					}
				case 3:
					temp = getCellAt(currentCell.getRow(), currentCell.getCol() - 2);
					if(!visited.contains(temp) && temp != null) {
						getCellAt(currentCell.getRow(), currentCell.getCol() - 1).setType(CellType.PATH);
						mazeRecurse(temp);
						unvisited = false;
					}
				}
				dir = rand.nextInt(4);
			}
			if(!unvisited && pile.size() > 1) {
				pile.pop();
				mazeRecurse(pile.pop());
			}
		} else {
			visited.add(finish);
		}
		repaint();
	}
	
	public void updateSize(int rows, int cols) {
		numRows = rows;
		numCols = cols;
	}

	private boolean isValidMove(MazeCell from, int row, int col) {
		if(col > 0 && row > 0 && col < numCols - 1 && row < numRows - 1 ) return true;
		return false;
	}

	public MazeCell getCellAt(int row, int col) {
		if(row >= 0 && row < numRows && col >= 0 && col < numCols) return grid.get(row).get(col);
		return null;
	}
	
	public void solveMaze() {
		pile.clear();
		calcAdjacencies(1);
		solveRecurse(start);
	}
	
	private void solveRecurse(MazeCell current) {
/*		try {
			Thread.sleep(50);
			repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.out.println(pile.size());
		if(!current.getVisited() && !reachedFinish) {
			current.setVisited(true);
			pile.push(current);
			if(current.equals(finish))	reachedFinish = true;
			for(MazeCell c : getAdjacencies(current)) {
				if(!c.getVisited() && !reachedFinish){
					solveRecurse(c);
				}
			}
		} else if (!reachedFinish){
			solveRecurse(pile.pop());
		}
	}

	public ArrayList<ArrayList<MazeCell>> getGrid() {
		return grid;
	}

	public ArrayList<MazeCell> getAdjacencies(MazeCell cell) {
		//System.out.println(adj.get(getCellAt(0,0).toString()));
		return adj.get(cell);
	}

	public int getNumRows() {
		return grid.size();
	}

	public int getNumCols() {
		return grid.get(0).size();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		// Account for window being scalable
		int windowWidth = this.getSize().width;
		int windowHeight = this.getSize().height;
		int gridWidth;
		int gridHeight;
		int aspectRatio = 1 ; // fixed so grid cells are square
		// System.out.println(windowWidth + " " + windowWidth / numCols);
		gridWidth = windowWidth / (numCols);
		gridHeight = windowHeight / (numRows);
		if( windowWidth <= windowHeight) {
			gridWidth = windowWidth / (numCols);
			gridHeight = gridWidth * aspectRatio;
		} else {
			gridWidth = windowHeight / (numRows);
			gridHeight = gridWidth * aspectRatio;
		}
		int x;
		int y;
/*		for(int i = 0; i < numRows; i++) {
			for(int k = 0; k < numCols; k++) {
				g.drawRect(i*gridWidth, k*gridHeight, gridWidth, gridHeight);
			}
		}*/
		for( int i = numRows - 1; i >= 0; i--) {
			for( int k = numCols - 1; k >= 0; k--) {
				x = gridWidth * i;
				y = gridHeight * k;
				grid.get(k).get(i).draw(x, y, gridHeight, gridWidth, g);
			}
		}

	}
}
