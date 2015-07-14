import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Cell implements Comparable<Cell>{

	int x, y, size;
	private CellTypes type = CellTypes.EMPTY;
	private int row;
	private int col;
	double gCost = Double.MAX_VALUE, hCost = Double.MAX_VALUE, fCost = Double.MAX_VALUE;
	Cell parentCell;
	Tower tower;
	Point center;
	//boolean hasTower = false;
	
	public Cell(int x, int y, int row, int col, int size) {
		this.x = x;
		this.y = y;
		this.setRow(row);
		this.setCol(col);
		this.size = size;
		center = new Point(x+size/2, y+size/2);
	}
	
	public void draw(Graphics2D g){
		
		g.setColor(type.color);
		g.fillRect(x, y, size, size);
		g.setColor(Color.black);
		g.drawRect(x, y, size, size);
		
//		if (hasTower())
//			tower.draw(g);
		
	}
	
	boolean hasTower() {
		return tower != null;
	}

	public CellTypes getType() {
		return type;
	}

	public void setType(CellTypes type) {
		this.type = type;
	}
	
	public boolean contains(MouseEvent e) {
		return new Rectangle(x, y, size, size).contains(e.getPoint());
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	@Override
	public String toString() {
		
		return "Cell: [row: " + row + " col: " + col + "type: " + type + "]";
//		return "Cell: [x: " + x + " y: " + y + " row: " + row + " col: " + col + " type: " + type + " gCost: " + gCost + " hCost: " + hCost + " fCost: " + fCost + "]";
	}
	
	public void reset(){
		gCost = hCost = fCost = Double.MAX_VALUE;
	}

	@Override
	public int compareTo(Cell other) {
		if (other.fCost > this.fCost)
			return -1;
		if (other.fCost < this.fCost)
			return 1;
		
		return 0;
	}

	public boolean click(MouseEvent e) {
		
		if (new Rectangle(x, y, size, size).contains(e.getPoint())){
			if (type == CellTypes.EMPTY)
				type = CellTypes.BARRIER;
			else if (type == CellTypes.BARRIER)
				type = CellTypes.EMPTY;
			return true;
		}
		return false;
		
	}

}