import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TowerDefense extends JFrame{

	public static JPanel canvas;
	
	Cell[][] cells;
	final int size = 50;
	
	public TowerDefense() {
		
		int width = 800, height = 600;
		int rows = height/50;
		int cols = width/50;
		
		say(rows + " " + cols);
		
		cells = new Cell[rows][cols];
		
		for (int r=0; r < rows; r++)
			for (int c=0; c < cols; c++)
				cells[r][c] = new Cell(c*50,r*50,50);
		
		
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
			}
		});
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void say(Object s){
		System.out.println(s);
	}

	public static void main(String[] args) {
		new TowerDefense();
	}

}
