package game.gameboard;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Cursor extends Element{

	private static final int MOVING_UP = 0; 
	private static final int MOVING_DOWN = 1; 
	private static final int MOVING_LEFT = 2; 
	private static final int MOVING_RIGHT = 3; 
	
	private static final float Round = 1f; 

	private int shipGrap = -1;

	public Cursor(GameRules rules, int x, int y, float size, Rectangle bounds, Dimension screensize) {
		super(rules, x, y, size, bounds, screensize);
	}
	
	public void draw(Graphics2D g2) {
		if(shipGrap == -1) {
			g2.setStroke(new BasicStroke(calculateHeight(size/10)));
			g2.drawRoundRect(bounds.x + calculateHeight(size)*(x), bounds.y + calculateHeight(size)*(y), calculateHeight(size), calculateHeight(size),calculateHeight(Round),calculateHeight(Round));
			g2.setStroke(new BasicStroke(calculateHeight(size)/40));
		}
	}

	public void updateCursor(int nextMoving) {
		switch(nextMoving) {
		case MOVING_UP:
			if(y > 0) y--;
			break;
		case MOVING_DOWN:
			if(y < rules.getCol()-1) y++;
			break;
		case MOVING_LEFT:
			if(x > 0) x--;
			break;
		case MOVING_RIGHT:
			if(x < rules.getRow()-1) x++;
			break;
		}
	}

	public int getShipGrap() {
		return shipGrap;
	}

	public void setShipGrap(int shipGrap) {
		this.shipGrap = shipGrap;
	}

}
