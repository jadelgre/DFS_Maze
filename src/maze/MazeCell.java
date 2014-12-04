package maze;

import java.awt.Color;
import java.awt.Graphics;

public class MazeCell {
	public enum CellType { PATH, WALL, ENTRANCE, EXIT };
	private CellType type;
	private int row;
	private int col;
	
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
	
	public void draw(int y, int x, int gridHeight, int gridWidth, Graphics g) {
		g.setColor(Color.black);
		//g.drawRect(x, y, gridWidth, gridHeight);
		if(this.type == CellType.WALL) g.fillRect(x, y, gridWidth, gridHeight);	
	}
}
