package game.gameboard;
import java.awt.Dimension;
import java.awt.Rectangle;

public class Element extends Basic.BasicCalculate{

	protected int x;
	protected int y;
	protected float size;
	protected Rectangle bounds;
	protected Dimension screensize;
	protected GameRules rules;
	
	public Element(GameRules rules, int x, int y, float size, Rectangle bounds, Dimension screensize) {
		super(screensize);
		this.rules = rules;
		this.x = x;
		this.y = y;
		this.size = size;
		this.bounds = bounds;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getSize() {
		return size;
	}
	
	public void setSize(float size) {
		this.size = size;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
