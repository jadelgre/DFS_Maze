package maze;
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
	private enum MoveDirection { U, D, L, R };
	private boolean reachedFinish = false;

	public MazeMap(int rows, int cols) {
		numRows = rows;
		numCols = cols;
		createGrid();
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
		visited = new ArrayList<MazeCell>();
		pile = new Stack<MazeCell>();
		// get a random edge cell
		rand = new Random(numRows);
		int randomNum = rand.nextInt();
		start = getCellAt(1, 1);
		start.setType(CellType.ENTRANCE);
		randomNum = rand.nextInt();
		finish = getCellAt(23, 23);
		finish.setType(CellType.EXIT);
		mazeRecurse(start);
		System.out.println(pile.size());
/*		if(!pile.empty()) {
			calcAdjacencies(2);
		}
		while(!pile.empty()) {
			MazeCell temp = pile.pop();
			for(MazeCell c : adj.get(temp)) {
				mazeRecurse(temp);
			}
		}*/
	}

	private void mazeRecurse(MazeCell currentCell) { 
		if(!currentCell.equals(finish)) {
			pile.push(currentCell);
			currentCell.setType(CellType.PATH);
			visited.add(currentCell);
			//MoveDirection dir = getRandomDir();
			rand = new Random();
			int dir = rand.nextInt(4);
			MazeCell temp;
			Boolean unvisited = true;
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
			if(!unvisited) {
				pile.pop();
				mazeRecurse(currentCell);
			}
			repaint();
		} else if(pile.size() < 1) mazeRecurse(pile.pop());

	}

	private MoveDirection getRandomDir() {
		rand = new Random(MoveDirection.values().toString().length());
		System.out.println(MoveDirection.values().toString());
		return MoveDirection.values()[rand.nextInt()];
	}


	private MazeCell getRandomAdj(MazeCell location) {
		if(getAdjacencies(location).size() == 1) return getAdjacencies(location).get(0);
		rand = new Random(getAdjacencies(location).size());
		return getAdjacencies(location).get(rand.nextInt());
	}

	private boolean isValidMove(MazeCell from, int row, int col) {
		if(col > 0 && row > 0 && col < numCols - 1 && row < numRows - 1 ) return true;
		return false;
	}

	public MazeCell getCellAt(int row, int col) {
		if(row >= 0 && row < numRows && col >= 0 && col < numCols) return grid.get(row).get(col);
		return null;
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
		int windowWidth = this.getWidth() - 1;
		int windowHeight = this.getHeight() - 1;
		int gridWidth;
		int gridHeight;
		int aspectRatio = 1 ; // fixed so grid cells are square
		if( windowWidth < windowHeight) {
			gridWidth = windowWidth / (numRows);
			gridHeight = gridWidth * aspectRatio;
		} else {
			gridWidth = windowHeight / (numCols);
			gridHeight = gridWidth * aspectRatio;
		}
		int x;
		int y;
		for( int i = numRows - 1; i >= 0; i--) {
			for( int k = numCols - 1; k >= 0; k--) {
				x = gridWidth * i;
				y = gridHeight * k;
				grid.get(k).get(i).draw(y, x, gridHeight, gridWidth, g);
			}
		}

	}
}
