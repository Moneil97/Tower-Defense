import java.awt.Color;
import java.awt.Graphics2D;

abstract public class Tower {

	protected int range;
	protected int x;
	protected int y, size;
	
	
//	public Tower(int x, int y, int range) {
//		// TODO Auto-generated constructor stub
//	}
	
	public Tower(Cell c, int range) {
		x = c.x;
		y = c.y;
		size = c.size;
		this.range = range;
	}
	
	public boolean enemyInRange(Enemy enemy){
		return range > getDistance(x,y,enemy.getX(),enemy.getY());
	}
	
	private double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt(Math.pow(Math.abs(y1-y2), 2) + Math.pow(Math.abs(x1-x2), 2));
	}

	abstract void draw(Graphics2D g);

	public void update() {
		
	}

}

class Gunner extends Tower{
	
	static final int GUNNER_RANGE = 50;

	public Gunner(Cell c) {
		super(c, GUNNER_RANGE);
	}

	@Override
	void draw(Graphics2D g) {
		g.setColor(Color.blue);
		g.drawOval(x-range/2, y-range/2, range*2, range*2);
		
	}
	
	@Override
	public void update() {
		super.update();
		
		for (Enemy enemy : TowerDefense.enemies)
			if (enemyInRange(enemy))
				System.out.println("In range");
		
	}
	
}
