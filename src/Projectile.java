import java.awt.Graphics2D;

public class Projectile {

	int x,y,speed=10;
	int width = 6, height = 2;
	
	public Projectile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics2D g){
		g.fillRect(x-width/2, y-height/2, width, height);
	}

}