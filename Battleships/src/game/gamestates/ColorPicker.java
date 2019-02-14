package game.gamestates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Basic.BasicCalculate;

public class ColorPicker extends BasicCalculate{
	private static final int arc = 2;
	private static final int minSize = 4;
	private static final int maxSize = 10;
	private static final int offset_angel = -90;

	private int y = 255;
	private int angle;
	private Graphics2D g2;

	private double speed;
	private long time;
	
	public ColorPicker(Dimension screensize, Graphics2D g2) {
		super(screensize);
		this.g2 = g2;
	}

	public void draw(int x, int y) {
		int angle = this.angle;
		
		g2.setColor(Color.WHITE);
		int index = 0;
		ArrayList<Polygon> poly = new ArrayList<Polygon>();
		for(int i = 0;i < 360;i += arc) {
			poly.add(new Polygon());
			poly.get(index).addPoint((int)(Math.cos(Math.toRadians((i - (arc)) - angle + offset_angel)) * calculateHeight(maxSize) + x), (int)(Math.sin(Math.toRadians((i - (arc)) - angle + offset_angel)) * calculateHeight(maxSize) + y));
			poly.get(index).addPoint((int)(Math.cos(Math.toRadians((i + (arc)) - angle + offset_angel)) * calculateHeight(maxSize) + x), (int)(Math.sin(Math.toRadians((i + (arc)) - angle + offset_angel)) * calculateHeight(maxSize) + y));
			poly.get(index).addPoint((int)(Math.cos(Math.toRadians((i + (arc)) - angle + offset_angel)) * calculateHeight(minSize) + x), (int)(Math.sin(Math.toRadians((i + (arc)) - angle + offset_angel)) * calculateHeight(minSize) + y));
			poly.get(index).addPoint((int)(Math.cos(Math.toRadians((i - (arc)) - angle + offset_angel)) * calculateHeight(minSize) + x), (int)(Math.sin(Math.toRadians((i - (arc)) - angle + offset_angel)) * calculateHeight(minSize) + y));
			index++;
		}
		
		
		for(int i = 0;i < poly.size();i++) {
			g2.setColor(calculateColor(i * arc));
			g2.fillPolygon(poly.get(i));
		}
		g2.setColor(Color.WHITE);
		Polygon polygon = new Polygon();
		polygon.addPoint(x - calculateHeight(1), (int) (y - calculateHeight(minSize)));
		polygon.addPoint(x + calculateHeight(1), (int) (y - calculateHeight(minSize)));
		polygon.addPoint(x, (int) (y - calculateHeight(minSize + 1)));
		g2.fillPolygon(polygon);
		g2.setColor(getColor());
		g2.fillOval(x - calculateHeight(minSize*2 + 0.5f)/2, y - calculateHeight(minSize*2 + 0.5f)/2, calculateHeight(minSize*2 + 0.5f), calculateHeight(minSize*2 + 0.5f));
		g2.setStroke(new BasicStroke(calculateHeight(0.4f)));
		g2.setColor(Color.WHITE);
		g2.drawOval(x - calculateHeight(maxSize*2)/2, y - calculateHeight(maxSize*2)/2, calculateHeight(maxSize*2), calculateHeight(maxSize*2));
		
		//Funktion für Jason (;
		g2.drawOval(x - calculateHeight(minSize*2)/2 - 1, y - calculateHeight(minSize*2)/2 - 1, calculateHeight(minSize*2) + 2, calculateHeight(minSize*2) + 2);
		
		Color tempcolor = getColorbyAngel();
		for(int i = 0;i < 255;i++) {
			g2.setColor(calculateYcolor((int) (i * 2), tempcolor));
			g2.fillRect(x + (calculateHeight(i*0.11764705882352941176470588235294f)) - calculateHeight(30)/2, y - calculateHeight(1.5f)/2 + calculateHeight(maxSize + 2), calculateHeight(1*0.11764705882352941176470588235294f + 0.1f), calculateHeight(1.5f));
		}
		g2.setColor(Color.WHITE);
		int yValue = this.y;
		if(yValue > 505)yValue = 505;
		g2.fillRect(x + (calculateHeight(yValue*(0.11764705882352941176470588235294f/2))) - calculateHeight(30)/2, y - calculateHeight(1.5f)/2 + calculateHeight(maxSize + 2), calculateHeight(0.5f), calculateHeight(1.5f));
		g2.setColor(Color.WHITE);
		g2.drawRect(x - calculateHeight(30)/2, y - calculateHeight(1.5f)/2 + calculateHeight(maxSize + 2), calculateHeight(30), calculateHeight(1.5f));
	}

	private Color calculateColor(int angle){
		int temp = 0;
		int red = 0, green = 0, blue = 0;
		if(angle <= 60 || angle >= 300) {
			red = 255;
		}else if(angle >= 60 && angle < 120) {
			temp = (angle - 120)*-1;
			red = (int) (temp * 4.25f);
		}else if(angle <= 300 && angle > 240) {
			temp = (angle - 240);
			red = (int) (temp * 4.25f);
		}
		if(angle >= 60 && angle <= 180) {
			green = 255;
		}else if(angle > 0 && angle <= 60) {
			temp = angle;
			green = (int) (temp * 4.25f);
		}else if(angle >= 180 && angle < 240) {
			temp = (angle - 240)*-1;
			green = (int) (temp * 4.25f);
		}
		if(angle >= 180 && angle <= 300) {
			blue = 255;
		}else if(angle <= 180 && angle > 120) {
			temp = (angle - 120);
			blue = (int) (temp * 4.25f);
		}else if(angle < 360 && angle >= 300) {
			temp = (angle - 360)*-1;
			blue = (int) (temp * 4.25f);
		}
		return new Color(red, green, blue);
	}
	
	public Color getColorbyAngel() {
		return calculateColor(angle);
	}
	
	public Color calculateYcolor(int y, Color color) {
		if(y <= 0) {
			return Color.BLACK;
		}else if(y < 255) {
			y = (y-255)*-1;
			int red = color.getRed() - y,green = color.getGreen() - y,blue = color.getBlue() - y;
			if(red < 0) red = 0;
			if(green < 0) green = 0;
			if(blue < 0) blue = 0;
			return new Color(red, green, blue);
		}else if(y > 255) {
			y -= 255;
			int red = color.getRed() + y,green = color.getGreen() + y,blue = color.getBlue() + y;
			if(red > 255) red = 255;
			if(green > 255) green = 255;
			if(blue > 255) blue = 255;
			return new Color(red, green, blue);
		}else {
			return color;
		}
		
		/*float v, s;
		if(y <= 255) v = (float)y / 255;
		else v = 1;
		if(y >= 255 && y <= 510) s = (float)((y - 510)*-1) / 255;
		else s = 1;
		float c = v * s;
		float x = c * (1 - (angle / 60) % 2 - 1);
		float m = v - s;
		System.out.println(m + " : " + v + " : " + s);
		float r = 0,g = 0,b = 0;
		if(0 <= angle && angle < 60) {
			r = c;
			g = x;
			b = 0;
		}
		if(60 <= angle && angle < 120) {
			r = x;
			g = c;
			b = 0;
		}
		
		
		int red = (int) ((r + m) * 255);
		int green = (int) ((g + m) * 255); 
		int blue = (int) ((b + m) * 255);
		if(red < 0) red = 0;
		else if(red > 255) red = 255;
		if(green < 0) green = 0;
		else if(green > 255) green = 255;
		if(blue < 0) blue = 0;
		else if(blue > 255) blue = 255;
		
		return new Color(red, green, blue);*/
	}
	
	public void setColor(Color color) {
		float r = (float)color.getRed() / 255, g = (float)color.getGreen() / 255, b = (float)color.getBlue() / 255;
		float Cmax = Math.max(r,Math.max(g, b));
		float Cmin = Math.min(r, Math.min(g, b));
		float d = (r==Cmin) ? g-b : ((b==Cmin) ? r-g : b-r);
		float h = (r==Cmin) ? 3 : ((b==Cmin) ? 1 : 5);
		 
		angle = (int) (60*(h - d/(Cmax - Cmin)));
		
		float S = (Cmax - Cmin)/Cmax;
		float V = Cmax;
		int y = (int) ((V - S) * 255 + 255);
		if(y < 0) y = 0;
		else if(y > 510) y = 510;
		
		this.y = y;
	}
	
	public Color getColor() {
		return calculateYcolor(y, getColorbyAngel());
	}

	public int getY() {
		return y;
	}

	public int getAngle() {
		return angle;
	}

	public double getSpeed() {
		return speed;
	}

	public void processInput(KeyEvent event) {
		long currenttime = System.currentTimeMillis();
		if(currenttime - time < 80) {
			if(speed < 5) speed += 0.1;
		}else {
			speed = 1;
		}
		time = currenttime;
		
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			if(y < 510) {
				y += speed;
			}
			if(y > 510) y = 510;
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			if(y > 0) {
				y -= speed;
			}
			if(y < 0) y = 0;
		}
		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			if(angle >= 360) {
				angle = (int) (0 + speed);
			}else {
				angle += speed;
			}
		}
		else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(angle <= 0) {
				angle = (int) (360 - speed);
			}else {
				angle -= speed;
			}
		}
	}

}
