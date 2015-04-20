import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TowerDefense extends JFrame implements Runnable{

	public static JPanel canvas;
	
	Cell[][] cells;
	final int size = 50;

	private PathFinder pathfinder;
	
	public TowerDefense() {
		
		int width = 800, height = 600;
		int rows = height/50;
		int cols = width/50;
		
		cells = new Cell[rows][cols];
		
		for (int r=0; r < rows; r++)
			for (int c=0; c < cols; c++)
				cells[r][c] = new Cell(c*50,r*50,50);
		
		for (int i=0; i < rows-1;i++)
			cells[i][1].setEmpty(false);
		for (int i=1; i < rows;i++)
			cells[i][3].setEmpty(false);
		
		for (int i=0; i < rows/2;i++)
			cells[i][8].setEmpty(false);
		
		this.add(canvas = new JPanel(){
			@Override
			protected void paintComponent(Graphics g1) {
				super.paintComponent(g1);
				Graphics2D g = (Graphics2D) g1;
				for (Cell[] row : cells)
					for (Cell cell : row)
						if (cell != null) cell.draw(g);
			}
		});
		
		canvas.setPreferredSize(new Dimension(cols*50, rows*50));
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				Cell changed = null;
				
				for (Cell[] row : cells)
					for (Cell cell : row){
						if (cell.click(e))
							changed = cell;
					}
				
				Path shortest = pathfinder.updateShortestPath();
				
				if (shortest != null){
					for (Cell[] row : cells)
						for (Cell cell : row)
							cell.setPartOfPath(false);
					
					for (Slot slot : shortest.slots)
						cells[slot.row][slot.col].setPartOfPath(true);
					
				}
				else{
					changed.click(e);
				}
				
			}
		});
		
		pathfinder = new PathFinder(cells, 0, 0, cells.length-1, cells[0].length-1);
		Path shortest = pathfinder.updateShortestPath();
		say(shortest);
		
		for (Slot slot : shortest.slots){
			cells[slot.row][slot.col].setPartOfPath(true);
		}
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		new Thread(this).start();
	}
	
	public static void say(Object s){
		System.out.println(s);
	}

	public static void main(String[] args) {
		new TowerDefense();
	}

	@Override
	public void run() {
		while (true){
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
