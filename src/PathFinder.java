import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PathFinder
{
	/**Holds matrix*/
	private Cell[][] matrix;
	/**Used by hasPath() to keep track of previously checked points*/
	private ArrayList<Slot> marked = new ArrayList<Slot>();
	protected PathManager pathManager = new PathManager();
	protected Path shortestPath;
	protected int startRow=-1, endRow=-1, startCol=-1, endCol=-1;
	
	public PathFinder(Cell[][] cells, int sr, int sc, int er, int ec)
	{
		matrix = cells;
		setStart(sr,sc);
		setEnd(er,ec);
	}
	
	/**
	 * Sets starting point
	 * <br>Set to a negative number to ignore
	 * <br>Ex: (2,-1) will have a starting point of the entire row 2
	 * @param row row to check
	 * @param col column to check
	 */
	
	public void setStart(int row, int col){
		startRow = row;
		startCol = col;
	}
	
	public void setStartRow(int row){
		startRow = row;
	}
	
	public void setStartCol(int col){
		startCol = col;
	}
	
	/**
	 * Sets ending point
	 * <br>Set to a negative number to ignore
	 * <br>Ex: (2,-1) will have a starting point of the entire row 2
	 * @param row row to check
	 * @param col column to check
	 */
	
	public void setEnd(int row, int col){
		endRow = row;
		endCol = col;
	}
	
	public void setEndRow(int row){
		endRow = row;
	}
	
	public void setEndCol(int col){
		endCol = col;
	}
	
	/**
	 * Quick check if there is an available path
	 * @return true if has path
	 */
	
	public boolean hasPath(){
		marked.clear();
		return hasPath(startRow, startCol);
	}

	/**
	 * recursive function used by hasPath()
	 * @param r row
	 * @param c	col
	 * @return true if has available path
	 */
	
	private boolean hasPath(int r, int c)
	{
		if (r>=0 && c>=0 && r<matrix.length && c < matrix.length && matrix[r][c].isEmpty() && !isMarked(r,c)){
			if (isAtEnd(new Slot(r,c)))
				return true;
			mark(r,c);
			return hasPath(r+1,c) || hasPath(r-1,c) || hasPath(r,c+1) || hasPath(r,c-1);
		}
		return false;
	}
	
	private void mark(int r, int c){
		marked.add(new Slot(r,c));
	}
	
	private boolean isMarked(int r, int c){
		return marked.contains(new Slot(r,c));
	}
	
	public void updateAllPaths(){
		pathManager.clear();
		updateAllPaths(startRow,startCol, new ArrayList<Slot>());
	}
	
	private void updateAllPaths(int r, int c, ArrayList<Slot> used){
		
		if (r>=0 && c>=0 && r<matrix.length && c < matrix.length){
			if (!used.contains(new Slot(r,c))){
				if (matrix[r][c].isEmpty()){
					
					used.add(new Slot(r,c));
					if (isAtEnd(new Slot(r,c))){
						pathManager.add(new Path(used));
						return;
					}
					
					updateAllPaths(r,c+1, new ArrayList<Slot>(used));
					updateAllPaths(r,c-1, new ArrayList<Slot>(used));
					updateAllPaths(r+1,c, new ArrayList<Slot>(used));
					updateAllPaths(r-1,c, new ArrayList<Slot>(used));
				}
			}
		}
	}
	
	public void sortAllPaths(){
		pathManager.sort();
	}
	
	
	long start;
	
	public void updateShortestPath(){
		
		start = System.nanoTime();
		ArrayList<Path> paths = new ArrayList<Path>();
		paths.add(new Path(new Slot(startRow, startCol)));
		ArrayList<Path> nextGen = new ArrayList<Path>();
		ArrayList<Slot> usedSlots = new ArrayList<Slot>();
		shortestPath = null;

		while (shortestPath == null && paths.size() != 0){
		
			//say(paths);
			for (Path path : paths){
				Slot slot = path.slots.get(path.slots.size()-1);
				if (usedSlots.contains(slot))
					continue;
				else
					usedSlots.add(slot);
				
				if (isAtEnd(slot)){
					shortestPath = path;
					say("----Made it! ----" + shortestPath);
					say("It took: " + (System.nanoTime() - start)/1000000000.0 + " seconds");
					break;
				}
				
				if (slot.col < matrix[slot.row].length-1 && matrix[slot.row][slot.col+1].isEmpty()){
					Path temp = new Path(path.slots);
					temp.add(new Slot(slot.row,slot.col+1));
					nextGen.add(temp);
				}
				if (slot.row < matrix.length-1 && matrix[slot.row+1][slot.col].isEmpty()){
					Path temp = new Path(path.slots);
					temp.add(new Slot(slot.row+1,slot.col));
					nextGen.add(temp);
				}
				if (slot.row > 0 && matrix[slot.row-1][slot.col].isEmpty()){
					Path temp = new Path(path.slots);
					temp.add(new Slot(slot.row-1,slot.col));
					nextGen.add(temp);
				}
				if (slot.col > 0 && matrix[slot.row][slot.col-1].isEmpty() ){
					Path temp = new Path(path.slots);
					temp.add(new Slot(slot.row,slot.col-1));
					nextGen.add(temp);
				}
			}
			paths = new ArrayList<Path>(nextGen);
			nextGen.clear();
		}
		
	}
	
	public boolean isAtEnd(Slot slot){
		return isAtEnd(slot.row, slot.col);
	}
	
	public boolean isAtEnd(int r, int c){
		return ((endCol >= 0 && c == endCol && endRow < 0) || (endRow >= 0 && r == endRow && endCol < 0) || (endRow >= 0 && r == endRow && endCol >= 0 && c == endCol));
	}
	
	public static void say(Object s){
		System.out.println(s);
	}
	
	public String toString()
	{
		String out = "";
		
		for (Cell [] m : matrix)
			out += Arrays.toString(m) + "\n";
		
		return out;
	}
}

class PathManager{
	
	ArrayList<Path> paths;
	
	public PathManager() {
		paths = new ArrayList<Path>();
	}
	
	public void add(Path path){
		paths.add(path);
	}
	
	public void clear() {
		paths.clear();
	}
	
	public void sort(){
		Collections.sort(paths);
	}
	
	@Override
	public String toString() {
		
		String out = "Paths: \n";
		
		for (Path path : paths)
			//out += "[" + path.toString()+"]\n";
			out += path.fancyToString()+"\n\n";
		
		return out;
	}
	
}

class Path implements Comparable<Path>{
	
	public ArrayList<Slot> slots;
	
	public Path(Slot slot) {
		slots = new ArrayList<Slot>();
		slots.add(slot);
	}
	
	public Path(ArrayList<Slot> used) {
		slots = new ArrayList<Slot>(used);
	}
	
	public Path(ArrayList<Slot> used, Slot slot) {
		slots = new ArrayList<Slot>(used);
		slots.add(slot);
	}

	public void add(Slot slot){
		slots.add(slot);
	}
	
	public boolean contains(Slot slot){
		return slots.contains(slot);
	}
	
	public int getSize(){
		return slots.size();
	}
	
	@Override
	public int compareTo(Path other) {
		return (this.getSize() == other.getSize()? 0: (this.getSize() < other.getSize() ? -1: 1));
	}
	
	public String fancyToString(){
		
		int largestRow=1, largestCol=1;
		
		for (Slot slot : slots){
			largestRow = Math.max(largestRow, slot.row+1);
			largestCol = Math.max(largestCol, slot.col+1);
		}
		
		int matrix[][] = new int[largestRow][largestCol];
		for (int i=0; i <matrix.length; i++)
			Arrays.fill(matrix[i], 0);
		
		int x=0;
		for (Slot slot : slots){
			matrix[slot.row][slot.col] = ++x;
			if (x > 122-48)
				x=0;
		}
		
		String out = "";

		for (int[] row : matrix){
			//out+= Arrays.toString(row) + "\n";
			for (int col : row){
				if (col == 0) out += "* ";
				else out+=(char)(col+48) + " ";//Integer.toUnsignedString(col, 32) + " ";
			}
			out+= "\n";
		}
		
		return out = out.substring(0, out.length());
	}
	
	@Override
	public String toString() {
		
		String out = "Path: {";
		
		for (Slot s : slots)
			out+= s.toString() + ", ";
		
		out = out.substring(0, out.length()-2) + "} size: " + slots.size();
		
		return out;
	}
	
}

class Slot{
	
	public int row;
	public int col;

	public Slot(int r, int c){
		this.row = r;
		this.col = c;
	}
	
	@Override
	public boolean equals(Object paramObject) {
		Slot other = (Slot)paramObject;
		return row == other.row && col == other.col; 
	}
	
	@Override
	public String toString() {
		return "[" + row + "][" + col + "]";
	}
}