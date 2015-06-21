//import java.awt.Color;
//import java.awt.Graphics2D;
//
//
//public class Enemy {
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
//}
