package Tests;
import maze.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConfigTests {
	private MazeMap testMap;
	private int ROWS = 25;
	private int COLS = 50;
	private int x;
	private int y;
	private MazeCell temp;
	@Before
	public void setUp() {
		testMap = new MazeMap(ROWS, COLS);
	}
	
	@Test
	public void testGridSize() {
		assertEquals(ROWS, testMap.getNumRows());
		assertEquals(COLS, testMap.getNumCols());
	}
	
	@Test
	public void testCellConfig() {
		// Test top left corner
		x = 0;
		y = 0;
		temp = new MazeCell(y,x);
		assertEquals(temp, testMap.getCellAt(y, x));
		// Test top right corner
		x = 49;
		temp = new MazeCell(y,x);
		assertEquals(temp, testMap.getCellAt(y, x));
		// Test bottom right corner 
		y = 24;
		temp = new MazeCell(y,x);
		assertEquals(temp, testMap.getCellAt(y, x));
		// Test bottom left corner
		x = 0;
		temp = new MazeCell(y,x);
		assertEquals(temp, testMap.getCellAt(y, x));
	}
	
	@Test
	public void testAdjacencies() {
		x = 0;
		y = 0;
		int expectedSize = 2;
		// Test a corner
		MazeCell temp = testMap.getCellAt(y, x);
		assertEquals(expectedSize, testMap.getAdjacencies(temp).size());
		MazeCell adjTemp = testMap.getCellAt(y + 1,x);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		adjTemp = testMap.getCellAt(y,x + 1);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		// Test somewhere in the middle 
		x = 10;
		y = 10;
		expectedSize = 4;
		temp = testMap.getCellAt(y, x);
		assertEquals(expectedSize, testMap.getAdjacencies(temp).size());
		adjTemp = testMap.getCellAt(y + 1,x);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		adjTemp = testMap.getCellAt(y - 1,x);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		adjTemp = testMap.getCellAt(y, x + 1);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		adjTemp = testMap.getCellAt(y,x - 1);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		// Test an edge
		x = 49;
		y = 10;
		expectedSize = 3;
		temp = testMap.getCellAt(y, x);
		assertEquals(expectedSize, testMap.getAdjacencies(temp).size());
		adjTemp = testMap.getCellAt(y + 1,x);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		adjTemp = testMap.getCellAt(y - 1,x);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
		adjTemp = testMap.getCellAt(y, x - 1);
		assertTrue(testMap.getAdjacencies(temp).contains(adjTemp));
	}

}
