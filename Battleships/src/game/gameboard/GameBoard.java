package game.gameboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends Basic.BasicCalculate{

	private ArrayList<Ship> ships = new ArrayList<Ship>();

	private Cursor cursor;

	private Rectangle bounds;
	private float size;
	private Graphics2D g2;
	private Color shipColor;
	
	private GameRules rules;

	private boolean[][] Arr_ships;
	private boolean[][] Arr_collision;

	public GameBoard(GameRules rules, Rectangle bounds, float size, Dimension screensize, Graphics2D g2, Color shipcolor) {
		super(screensize);
		this.bounds = bounds;
		this.size = size;
		this.g2 = g2;
		this.shipColor = shipcolor;
		this.rules = rules;
	}
	
	public void init() {
		cursor = new Cursor(rules, 0, 0, size, bounds, screensize);
		Arr_collision = new boolean[rules.getRow()][rules.getCol()];
		Arr_ships = new boolean[rules.getRow()][rules.getCol()];
	}

	public void draw(Color color, boolean shipShow, boolean cursorShow) {
		g2.setColor(new Color(0, 0, 0, 120));
		g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(calculateHeight(size)/40));
		for(int index = 0; index <= rules.getRow(); index++) {
			g2.drawLine(bounds.x + calculateHeight(size) * index, bounds.y, bounds.x + calculateHeight(size) * index, bounds.y + bounds.height);
		}
		for(int index = 0; index <= rules.getCol(); index++) {
			g2.drawLine(bounds.x, bounds.y + calculateHeight(size) * index, bounds.x + bounds.width, bounds.y + calculateHeight(size) * index);
		}

		for(int x = 0;x <= rules.getRow()-1;x++) {
			for(int y = 0;y <= rules.getCol()-1;y++) {
				if(Arr_collision[x][y]) {
					g2.setColor(new Color(130, 130, 130, 130));
					g2.fillRect(bounds.x + calculateHeight(size)*(x), bounds.y + calculateHeight(size)*(y), calculateHeight(size), calculateHeight(size));
				}else if(Arr_ships[x][y]){
					g2.setColor(shipColor);
					g2.fillRect(bounds.x + calculateHeight(size)*(x) + calculateHeight(size)/40/2, bounds.y + calculateHeight(size)*(y) + calculateHeight(size)/40/2, calculateHeight(size), calculateHeight(size));
				}
			}
		}

		for(int index = 0;index < ships.size();index++) {
			ships.get(index).draw(g2, shipShow, color);
		}

		if(cursorShow) {
			g2.setColor(color);
			cursor.draw(g2);
		}
	}
	
	public void systemprint() {
		System.out.println('\n');
		System.out.println('\n');
		for(int y = 0;y <= rules.getCol()-1;y++) {
			for(int x = 0;x <= rules.getRow()-1;x++) {
				System.out.print(whatContains(new Point(x, y)) + ":");
			}
			System.out.println("");
		}
	}
	
	public void drawrating(Integer[][] rating, boolean color) {
		for(int x = 0;x < rules.getRow();x++) {
			for(int y = 0;y < rules.getCol();y++) {
				if(rating[x][y] != null) {
					if(!color) {
						if(rating[x][y] == 1) {
							g2.setColor(new Color(0, 127, 255, 50));
						}else if(rating[x][y] == 2) {
							g2.setColor(new Color(33, 255, 0, 50));
						}else if(rating[x][y] <= 0) {
							g2.setColor(new Color(255, 0, 0, 50));
						}
						g2.fillRect(bounds.x + calculateHeight(size)*(x), bounds.y + calculateHeight(size)*(y), calculateHeight(size), calculateHeight(size));
					}else {
						g2.setColor(Color.WHITE);
						g2.drawString(rating[x][y].toString(), bounds.x + calculateHeight(size)*(x) + calculateHeight(5), bounds.y + calculateHeight(size)*(y) + calculateHeight(5));
					}
				}
			}
		}
	}

	public boolean isShipAlive() {
		boolean bool = true;
		int index = 0;
		while(bool && index < ships.size()) {
			bool = ships.get(index).isShipDead();
			index++;
		}
		return !bool;
	}
	
	public void setPosition(Point point, int percent,  Dimension screensize) {
		this.screensize = screensize;
		int size = 10;
		if(rules.getRow() == rules.getCol()) {
			size = percent / rules.getRow();
		}else if(rules.getRow() < rules.getCol()) {
			size = percent / rules.getCol();
		}else if(rules.getRow() > rules.getCol()) {
			size = percent / rules.getRow();
		}
		Rectangle bounds = new Rectangle(point.x - ((rules.getRow() * calculateHeight(size))/2), point.y - ((rules.getCol() * calculateHeight(size))/2), rules.getRow() * calculateHeight(size), rules.getCol() * calculateHeight(size));
		setSize(size);
		setBounds(bounds);
	}

	public void setSize(float size) {
		this.size = size;
		for(int index = 0;index < ships.size();index++) {
			ships.get(index).setSize(size);
		}
		cursor.setSize(size);
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		for(int index = 0;index < ships.size();index++) {
			ships.get(index).setBounds(bounds);
			ships.get(index).setScreensize(screensize);
		}
		cursor.setBounds(bounds);
		cursor.setScreensize(screensize);
	}

	public void createShip(int length) {
		Ship ship;
		do {
			ship = new Ship(rules, new Random().nextInt(rules.getRow()), new Random().nextInt(rules.getCol()), size, length, new Random().nextInt(2), shipColor,screensize, bounds);
			ship.updateShip(-1);
		}while(rules.shipCollision(ships, ship));
		ships.add(ship);
	}

	public void generateShips() {
		ArrayList<Integer> shipsLenght = rules.getShipsLenght();
		int index = 0;
		while(index < shipsLenght.size()) {
			createShip(shipsLenght.get(index));
			index++;
		}
	}

	public void setShipPosition(int index, int x, int y) {
		ships.get(index).setX(x);
		ships.get(index).setY(y);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public synchronized void cursorUp() {
		cursor.updateCursor(0);
		if(cursor.getShipGrap() >= 0) ships.get(cursor.getShipGrap()).updateShip(0);
	}

	public synchronized void cursorDown() {
		cursor.updateCursor(1);
		if(cursor.getShipGrap() >= 0) ships.get(cursor.getShipGrap()).updateShip(1);
	}

	public synchronized void cursorLeft() {
		cursor.updateCursor(2);
		if(cursor.getShipGrap() >= 0) ships.get(cursor.getShipGrap()).updateShip(2);
	}

	public synchronized void cursorRight() {
		cursor.updateCursor(3);
		if(cursor.getShipGrap() >= 0) ships.get(cursor.getShipGrap()).updateShip(3);
	}

	public Cursor getCursor() {
		return cursor;
	}

	public boolean isShipGraped() {
		return cursor.getShipGrap() != -1;
	}

	public int Arr_collisionCountofUsing() {
		int count = 0;
		for(int x = 0;x <= rules.getRow()-1;x++) {
			for(int y = 0;y <= rules.getCol()-1;y++) {
				if(Arr_collision[x][y]) {
					count++;
				}
			}
		}
		return count;
	}

	public boolean Arr_collision_contains(Point point) {
		boolean bool = true;
		if((point.x >= 0 && point.x <= rules.getRow()-1) && (point.y >= 0 && point.y <= rules.getCol()-1)) {
			bool = Arr_collision[point.x][point.y];
		}
		return bool;
	}

	public int whatContains(Point point) {
		int what = 0;
		if((point.x >= 0 && point.x <= rules.getRow()-1) && (point.y >= 0 && point.y <= rules.getCol()-1)) {
			if(Arr_collision[point.x][point.y]) {
				what = 1;
			}
			int index = 0;
			while(index < ships.size() && what != 2) {
				if(ships.get(index).contains(point.x, point.y) && Arr_collision[point.x][point.y]) {
					what = 2;
				}
				index++;
			}
		}else {
			what = -1;
		}
		return what;
	}

	public void grapShip() {
		if(cursor.getShipGrap() == -1) {
			int index = 0;
			while(index < ships.size()) {
				if(ships.get(index).contains(cursor.getX(), cursor.getY())) {
					cursor.setShipGrap(index);
					ships.get(index).setGraped(true);
				}
				index++;
			}
		}else{
			if(!rules.shipCollision(ships, ships.get(cursor.getShipGrap()))) {
				ships.get(cursor.getShipGrap()).setGraped(false);
				cursor.setX(ships.get(cursor.getShipGrap()).getX());
				cursor.setY(ships.get(cursor.getShipGrap()).getY());
				cursor.setShipGrap(-1);

			}
		}
	}

	public void rotatetShip() {
		if(cursor.getShipGrap() >= 0) {
			if(ships.get(cursor.getShipGrap()).getAlignment() == 0) {
				ships.get(cursor.getShipGrap()).setAlignment(1);
			}else {
				ships.get(cursor.getShipGrap()).setAlignment(0);
			}
		}
	}

	public boolean shotOnShip() {
		int index = 0;
		boolean bool = false;

		if(!Arr_collision[cursor.getX()][cursor.getY()]) {
			Arr_collision[cursor.getX()][cursor.getY()] = true;

			while(!bool && index < ships.size()) {
				bool = ships.get(index).containsShot(cursor.getX(), cursor.getY());
				index++;
			}
		}else {
			bool = true;
		}
		
		return bool;
	}

	public Point cursorPosition() {
		return new Point(cursor.x, cursor.y);
	}

	public Point cursorAbsolutePosition() {
		Point point = new Point();
		point.setLocation(bounds.x + calculateHeight(size)*(cursor.getX()) + calculateHeight(size)/2, bounds.y + calculateHeight(size)*(cursor.getY()) + calculateHeight(size)/2);
		return point;
	}

}
