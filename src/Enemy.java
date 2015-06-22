import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;


public class Enemy {
	
	private float diag = (float) Math.sqrt(2);
	private List<Cell> currentPath;
	private int x = 0, y = 0;
	private int size = 20;
	private int speed = 5;
	private Cell target;
	private int targetCounter = 0;
	
	public Enemy(int x, int y, List<Cell> path){
		this.x = x;
		this.y = y;
		setCurrentPath(path);
	}
	
	public void move(){
		
		int targetX = target.x + target.size/2;
		int targetY = target.y + target.size/2;
		
		if (x == targetX && y == targetY)
			if (targetCounter < currentPath.size()-1)
				target = currentPath.get(++targetCounter);
			else
				System.out.println("you win");
		
		if (x != targetX && y != targetY){
			x += Math.min(getXDirection(targetX) * speed / diag , Math.abs(targetX - x));
			y += Math.min(getYDirection(targetY) * speed / diag , Math.abs(targetY - y));
		}
		else if (x != targetX){
			x += Math.min(getXDirection(targetX) * speed , Math.abs(targetX - x));
		}
		else{
			y += Math.min(getYDirection(targetY) * speed , Math.abs(targetY - y));
		}
		
	}

	private int getXDirection(int targetX) {
		if (targetX > x)
			return 1;
		else 
			return -1;
	}
	
	private int getYDirection(int targetY) {
		if (targetY > y)
			return 1;
		else 
			return -1;
	}

	public void draw(Graphics2D g){
		g.setColor(Color.orange);
		g.fillRect(x-size/2, y-size/2, size, size);
	}

	List<Cell> getCurrentPath() {
		return currentPath;
	}

	void setCurrentPath(List<Cell> currentPath) {
		targetCounter = 0;
		this.currentPath = currentPath;
		target = currentPath.get(0);
	}
	
	
	
	
//
//	private int x = 0, y = 0;
//	private int size = 20;
//	private Path path;
////	private Cell currentCell;
//	private int currentCellNum;
//	
//	public Enemy(Path shortestPath) {
//		currentCellNum = 0;
//		setPath(shortestPath);
//		setX(getCurrentCell().x+getCurrentCell().size/2);
//		setY(getCurrentCell().y+getCurrentCell().size/2);
//		say(x + " " + y + " " + shortestPath);
//	}
//	
//	public Cell getCurrentCell(){
//		Slot slot = path.slots.get(currentCellNum);
//		return TowerDefense.cells[slot.row][slot.col];
//	}
//	
//	public Cell getCell(int i){
//		Slot slot = path.slots.get(i);
//		return TowerDefense.cells[slot.row][slot.col];
//	}
//	
//	public void moveToNext(){
//		currentCellNum ++;
//		setX(getCurrentCell().x+getCurrentCell().size/2);
//		setY(getCurrentCell().y+getCurrentCell().size/2);
//	}
//
//	public void draw(Graphics2D g){
//		g.setColor(Color.green);
//		g.fillRect(x-size/2, y-size/2, size, size);
//	}
//	
//	
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public Path getPath() {
//		return path;
//	}
//
//	public void setPath(Path path) {
//		this.path = path;
//	}
//	
//	public int getSize() {
//		return size;
//	}
//
//	public void setSize(int size) {
//		this.size = size;
//	}
//
//	public static void say(Object s){
//		System.out.println(s);
//	}
//
////	public Cell getCurrentCell() {
////		return currentCell;
////	}
////
////	public void setCurrentCell(Cell currentCell) {
////		this.currentCell = currentCell;
////	}
//	
}
