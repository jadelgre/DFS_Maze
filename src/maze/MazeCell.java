package maze;

import java.awt.Color;
import java.awt.Graphics;

public class MazeCell {
	public enum CellType { PATH, WALL, ENTRANCE, EXIT };
	private CellType type;
	private int row;
	private int col;
	private Boolean visited = false;
	private Boolean correct = false;
	
	public MazeCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.type = CellType.WALL;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setType(CellType type) {
		this.type = type;
	}
	
	public CellType getType() {
		return type;
	}
	@Override
	public boolean equals(Object other) {
		MazeCell temp = (MazeCell) other;
		if(this.row == temp.getRow() && this.col == temp.getCol() && this.type == temp.getType()) return true;
		return false;
	}
	
	public void draw(int x, int y, int gridHeight, int gridWidth, Graphics g) {
		g.setColor(Color.black);
		if(this.type == CellType.WALL) g.fillRect(x, y, gridWidth, gridHeight);	
		else if(this.type == CellType.PATH) {
			if(visited) {
				if(!correct) g.setColor(Color.GREEN);	
				else g.setColor(Color.BLUE);
				g.fillRect(x, y, gridWidth, gridHeight);	
			}
		} else {
			if(this.type == CellType.ENTRANCE){ g.setColor(Color.GREEN);
			System.out.println("YAR");
			}
			else if(this.type == CellType.EXIT) g.setColor(Color.RED);
			g.fillRect(x, y, gridWidth, gridHeight);	
		}
	}

	public Boolean getVisited() {
		return visited;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
