package game.background;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;

public class Ship extends Basic.BasicCalculate{

	private int x;
	private int y;
	private Graphics2D g2;
	
	private Polygon front, back;
	
	private boolean destroy = false;
	private int destroyValue = 0;
	
	private BackgroundWaves background;
	
	public Ship(BackgroundWaves background ,Dimension screensize , Graphics2D g2) {
		super(screensize);
		this.g2 = g2;
		this.x = calculateHeight(20) + screensize.width;
		this.y = new Random().nextInt(screensize.height);
		this.background = background;
	}
	
	public void setDestroy() {
		if(!destroy) background.shotEffect(x, y);
		this.destroy = true;
		
	}

	public void update() {
		if(!destroy) this.x -= calculateHeight(0.1f);
		
		int x = this.x;
		int y = this.y;
		if(destroy) {
			x -= destroyValue / 4;
			y += destroyValue;
		}
		front = new Polygon();
		front.addPoint(x - calculateHeight(10), y);
		front.addPoint(x - calculateHeight(8.5f), y + calculateHeight(5));
		front.addPoint(x + 1, y + calculateHeight(5));
		front.addPoint(x - calculateHeight(1) + 1, y + calculateHeight(3));
		front.addPoint(x + calculateHeight(1) + 1, y + calculateHeight(2));
		front.addPoint(x + 1, y);
		front.addPoint(x - calculateHeight(2), y);
		front.addPoint(x - calculateHeight(3), y - calculateHeight(1));
		front.addPoint(x - calculateHeight(8), y - calculateHeight(1));
		front.addPoint(x - calculateHeight(8), y - calculateHeight(0.5f));
		front.addPoint(x - calculateHeight(7), y - calculateHeight(0.5f));
		front.addPoint(x - calculateHeight(7), y);
		back = new Polygon();
		x = this.x;
		y = this.y;
		if(destroy) {
			x += destroyValue / 4;
			y += destroyValue;
		}
		back.addPoint(x + calculateHeight(10), y);
		back.addPoint(x + calculateHeight(8.5f), y + calculateHeight(5));
		back.addPoint(x, y + calculateHeight(5));
		back.addPoint(x - calculateHeight(1), y + calculateHeight(3));
		back.addPoint(x + calculateHeight(1), y + calculateHeight(2));
		back.addPoint(x, y);
		back.addPoint(x + calculateHeight(3), y);
		back.addPoint(x + calculateHeight(3), y - calculateHeight(2));
		back.addPoint(x + calculateHeight(8), y - calculateHeight(2));
		if(destroyValue <= calculateHeight(15) && destroy) destroyValue++;
		
	}
	
	public boolean isComplett() {
		return destroyValue >= calculateHeight(15) || x < 0 - calculateHeight(20);
	}
	
	public void draw() {
		g2.setColor(Color.BLACK);
		g2.fillPolygon(front);
		g2.setColor(Color.BLACK);
		g2.fillPolygon(back);
		//g2.setColor(Color.red);
		g2.setColor(new Color(14, 100, 180));
		g2.fillRect(x - calculateHeight(15), y + calculateHeight(5), calculateHeight(30), calculateHeight(16));
	}

}
