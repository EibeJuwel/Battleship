package game.gameboard;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ship extends Element{

	private final static int alignment_vertical = 0;
	private final static int alignment_horizonal = 1;
	private static final float Round = 1f;

	private int length;
	private int alignment;

	private boolean graped = false;

	private boolean[] die;
	private Color shipColor;
	
	//private BufferedImage ship5;

	public Ship(GameRules rules, int x, int y, float size,int length,int alignment, Color shipColor, Dimension screensize, Rectangle bounds){
		super(rules, x, y, size, bounds, screensize);

		this.length = length;
		this.alignment = alignment;
		this.shipColor = shipColor;
		
		die = new boolean[length];
	}

	public void draw(Graphics2D g2, boolean show, Color playerColor) {
		int index = 0;
		while(index < length) {
			switch(alignment) {
			case alignment_vertical:
				if(show || die[index]) {
					if(die[index]) g2.setPaint(new GradientPaint(0, 0, shipColor.darker(), 250, 250, shipColor, true));
					else g2.setColor(shipColor);
					g2.fillRect(bounds.x + calculateHeight(size)*(x) + calculateHeight(size)/40/2, bounds.y + calculateHeight(size)*(y-index) + calculateHeight(size)/40/2, calculateHeight(size), calculateHeight(size));
				}
				if(die[index]) {
					g2.setColor(playerColor);
					g2.setStroke(new BasicStroke(calculateHeight(size/20), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					g2.drawLine(bounds.x + calculateHeight(size)*(x) + calculateHeight(size)/8, bounds.y + calculateHeight(size)*(y-index) + calculateHeight(size)/8, bounds.x + calculateHeight(size)*(x) + calculateHeight(size)/8*7, bounds.y + calculateHeight(size)*(y-index) + calculateHeight(size)/8*7);
					g2.drawLine(bounds.x + calculateHeight(size)*(x) + calculateHeight(size)/8*7, bounds.y + calculateHeight(size)*(y-index) + calculateHeight(size)/8, bounds.x + calculateHeight(size)*(x) + calculateHeight(size)/8, bounds.y + calculateHeight(size)*(y-index) + calculateHeight(size)/8*7);
					g2.setStroke(new BasicStroke(calculateHeight(size)/40));
				}
				
				
				break;
			case alignment_horizonal:
				if(show || die[index]) {
					if(die[index]) g2.setPaint(new GradientPaint(0, 0, shipColor.darker(), 250, 250, shipColor, true));
					else g2.setColor(shipColor);
					g2.fillRect(bounds.x + calculateHeight(size)*(x-index) + calculateHeight(size)/40/2, bounds.y + calculateHeight(size)*(y) + calculateHeight(size)/40/2, calculateHeight(size), calculateHeight(size));
				}
				if(die[index]) {
					g2.setColor(playerColor);
					g2.setStroke(new BasicStroke(calculateHeight(size/20), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					g2.drawLine(bounds.x + calculateHeight(size)*(x-index) + calculateHeight(size)/8, bounds.y + calculateHeight(size)*(y) + calculateHeight(size)/8, bounds.x + calculateHeight(size)*(x-index) + calculateHeight(size)/8*7, bounds.y + calculateHeight(size)*(y) + calculateHeight(size)/8*7);
					g2.drawLine(bounds.x + calculateHeight(size)*(x-index) + calculateHeight(size)/8*7, bounds.y + calculateHeight(size)*(y) + calculateHeight(size)/8, bounds.x + calculateHeight(size)*(x-index) + calculateHeight(size)/8, bounds.y + calculateHeight(size)*(y) + calculateHeight(size)/8*7);
					g2.setStroke(new BasicStroke(calculateHeight(size)/40));
				}
				break;

			}
			index++;
		}
		
		/*if(length == 5 && alignment == alignment_vertical && show) {
			g2.drawImage(ship5, bounds.x + calculateHeight(size)*(x) + calculateHeight(size)/40/2, bounds.y + calculateHeight(size)*(y - length + 1) + calculateHeight(size)/40/2, 
					calculateHeight(size), calculateHeight(size) * length, null);
		}*/

		if(graped) {
			g2.setStroke(new BasicStroke(calculateHeight(size/10)));
			g2.setColor(playerColor);
			switch(alignment) {
			case alignment_vertical:
				g2.drawRoundRect(bounds.x + calculateHeight(size)*(x), bounds.y + calculateHeight(size)*(y-length+1), calculateHeight(size), calculateHeight(size)*length,calculateHeight(Round),calculateHeight(Round));
				break;
			case alignment_horizonal:
				g2.drawRoundRect(bounds.x + calculateHeight(size)*(x-length+1), bounds.y + calculateHeight(size)*(y), calculateHeight(size)*length, calculateHeight(size),calculateHeight(Round),calculateHeight(Round));
				break;
			}
			g2.setStroke(new BasicStroke(calculateHeight(size/40)));
		}
	}

	public void setGraped(boolean graped) {
		this.graped = graped;
	}

	public boolean isGraped() {
		return graped;
	}

	public int getLength() {
		return length;
	}

	public int getAlignment() {
		return alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
		updateShip(-1);
	}

	public boolean contains(int x, int y) {
		boolean bool = false;
		int index = 0;
		do {
			switch(alignment) {
			case alignment_vertical:
				bool = this.x == x && (this.y - index) == y;
				break;
			case alignment_horizonal:
				bool = (this.x - index) == x && this.y == y;
				break;
			}
		}while(!bool && index++ < length-1);

		return bool;
	}

	public boolean containsShot(int x, int y) {
		boolean bool = false;
		int index = 0;
		do {
			switch(alignment) {
			case alignment_vertical:
				bool = this.x == x && (this.y - index) == y;
				break;
			case alignment_horizonal:
				bool = (this.x - index) == x && this.y == y;
				break;
			}
		}while(!bool && index++ < length-1);

		if(bool) die[index] = true;

		return bool;
	}

	public void updateShip(int nextMoving) {
		switch (nextMoving) {
		case 0:
			y--;
			break;
		case 1:
			y++;
			break;
		case 2:
			x--;
			break;
		case 3:
			x++;
			break;
		}
		switch(alignment) {
		case 0:
			if(y < length) y = length-1;
			else if(y > rules.getCol()-1) y = rules.getCol()-1;
			if(x < 0) x = 0;
			else if(x > rules.getRow()-1) x = rules.getRow()-1;
			break;
		case 1:
			if(x < length) x = length-1;
			else if(x > rules.getRow()-1) x = rules.getRow()-1;
			if(y < 0) y = 0;
			else if(y > rules.getCol()-1) y = rules.getCol()-1;
			break;
		}
	}
	
	public boolean isShipDead() {
		int deathCount = 0;
		int index = 0;
		while(index < length) {
			if(die[index]) {
				deathCount++;
			}
			index++;
		}
		
		return deathCount >= length;
	}
}
