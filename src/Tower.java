import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

abstract public class Tower {

	protected int range;
	protected int x;
	protected int y, size;
	protected Point center;
	Color color = Color.blue;
	List<Projectile> bullets = new ArrayList<Projectile>();
	
	public Tower(Cell c, int range) {
		x = c.x;
		y = c.y;
		size = c.size;
		center = c.center;
		this.range = range;
	}
	
	public boolean enemyInRange(Enemy enemy){
		return range > getDistance(center.x,center.y,enemy.getX(),enemy.getY());
	}
	
	private double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt(Math.pow(Math.abs(y1-y2), 2) + Math.pow(Math.abs(x1-x2), 2));
	}

	abstract void draw(Graphics2D g);

	public void update() {
		
	}

}

class Gunner extends Tower{
	
	static final int GUNNER_RANGE = 75;
	

	public Gunner(Cell c) {
		super(c, GUNNER_RANGE);
	}

	@Override
	void draw(Graphics2D g) {
		g.setColor(Color.magenta);
		g.fillRect(x, y, size, size);
		g.setColor(new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, .2f));
		g.fillOval(center.x-range, center.y-range, range*2, range*2);
		g.setColor(color);
		g.drawOval(center.x-range, center.y-range, range*2, range*2);
	}
	
	@Override
	public void update() {
		super.update();
		
		color = Color.blue;
		for (Enemy enemy : TowerDefense.enemies)
			if (enemyInRange(enemy)){
				color = Color.green;
				//System.out.println("In range");
			}
		
	}
	
}
