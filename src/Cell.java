import java.awt.Graphics2D;

public class Cell {

	int x, y, size;

	public Cell(int x, int y) {
		this(x,y,50);
	}
	
	public Cell(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void draw(Graphics2D g){
		g.drawRect(x, y, size, size);
	}

	public boolean isEmpty() {
		return true;
	}

}
