package game.background;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class Waves extends Basic.BasicCalculate{

	private int x;
	private int y;
	
	private int Radius = 0;
	private int strok;
	private float plusRadius;
	private int minusStrok;
	private Graphics2D g2;
	
	public Waves(int x, int y, float plusRadius, int minusStrok, Dimension screensize, Graphics2D g2) {
		super(screensize);
		this.x = x;
		this.y = y;
		this.strok = 255;
		this.plusRadius = plusRadius;
		this.minusStrok = minusStrok;
		this.g2 = g2;
	}

	public void draw() {
		/*g2.setColor(new Color(200, 200, 200, strok));
		g2.setStroke(new BasicStroke(4));
		g2.drawOval(x - Radius/2-1, y - Radius/2-1, Radius+2, Radius+2);
		g2.setColor(new Color(100, 100, 100, strok/2));
		g2.setStroke(new BasicStroke(5));
		g2.drawOval(x - Radius/2-4, y - Radius/2-4, Radius+8, Radius+8);
		*/
		
		
		g2.setColor(new Color(255, 255, 255, strok));
		g2.setStroke(new BasicStroke(5));
		g2.drawOval(x - Radius/2, y - Radius/2, Radius, Radius);
		g2.setStroke(new BasicStroke(1));
	}
	
	public void update() {
		Radius += calculateHeight(plusRadius);
		strok -= minusStrok;
		if(strok < 0) {
			strok = 0;
		}
		
	}
	
	public boolean isComplett() {
		return strok <= 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRadius() {
		return Radius;
	}

	public void setRadius(int radius) {
		Radius = radius;
	}

}
