import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TowerDefense extends JFrame implements Runnable{
	
	JPanel canvas;
	PathFinder pathFinder;
	List<Cell> fastest;
	int size = 50;
	Cell[][] cells;
	protected boolean spawnEnemyWhenAvailable = false;
	
	public TowerDefense(){
		
		int width = 800, height = 600;
		int rows = height/50;
		int cols = width/50;
		
		cells = new Cell[rows][cols];
		
		for (int r=0; r < rows; r++)
			for (int c=0; c < cols; c++)
				cells[r][c] = new Cell(c*size, r*size, r,c, size);
		
		Enemy.finalCell = new Cell(cols*size, rows*size, rows,cols, size);
		
		for (int i=0; i < rows-1;i++)
			cells[i][1].setType(CellTypes.BARRIER);
		for (int i=1; i < rows;i++)
			cells[i][3].setType(CellTypes.BARRIER);
		for (int i=0; i < rows/2;i++)
			cells[i][8].setType(CellTypes.BARRIER);
		
		cells[1][3].tower = new Gunner(cells[1][3]);
		
		
		pathFinder = new PathFinder(cells, cells[0][0], cells[rows-1][cols-1], true, false);
		fastest = pathFinder.calculateShortestPath();
		
		this.add(canvas = new JPanel(){
			@Override
			protected void paintComponent(Graphics g1) {
				super.paintComponent(g1);
				Graphics2D g = (Graphics2D) g1;
				for (Cell[] row : cells)
					for (Cell cell : row)
						cell.draw(g);
				
				for (Cell[] row : cells)
					for (Cell cell : row)
						if (cell.hasTower())
							cell.tower.draw(g);
				
				for (Enemy enemy : enemies)
					enemy.draw(g); 
				
				drawFastestPath(g);	
			}
			
			private void drawFastestPath(Graphics2D g){
				
				if (fastest != null){
					
					g.setColor(Color.red);
					
					for (int i=0; i < fastest.size()-1; i++)
						g.drawLine(fastest.get(i).x + size/2, fastest.get(i).y + size/2, fastest.get(i+1).x + size/2, fastest.get(i+1).y + size/2);
				}
			}
		});
		
		canvas.setPreferredSize(new Dimension(cols*50, rows*50));
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				List<Cell> newFastest;
				
				for (Cell[] row : cells)
					for (Cell cell : row)
						if (cell.click(e)){
							if ((newFastest = pathFinder.calculateShortestPath()) != null)
								fastest = newFastest; //This may need to be cloned, but it seems to work fine without
							else
								cell.click(e);
							break;
						}
			}
			
		});
		
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				spawnEnemyWhenAvailable  = true;
			}
		});
		
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
	
	static List<Enemy> enemies = new ArrayList<Enemy>();
	
	@Override
	public void run() {
		while (true){
			try {
				
				if (spawnEnemyWhenAvailable){
					enemies.add(new Enemy(0, 0, fastest));
					spawnEnemyWhenAvailable = false;
				}
				
				for (int i =0; i < enemies.size(); i++){
					Enemy enemy = enemies.get(i);
					enemy.move();
					if (enemy.isAtEnd()){
						System.out.println("-1 life");
						enemies.remove(i);
						continue;
					}
					else if (enemy.isDead()){
						System.out.println("good job");
						enemies.remove(i);
						continue;
					}
				}
				
				for (Cell[] row : cells)
					for (Cell cell : row){
						if (cell.hasTower())
							cell.tower.update();
					}
				
				repaint();
				Thread.sleep(20);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
//	private void print2D(Object[][] array){
//	for (Object[] row : array)
//		System.out.println(Arrays.toString(row));
//}
}
