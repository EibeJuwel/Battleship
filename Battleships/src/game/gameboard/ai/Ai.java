package game.gameboard.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import game.Game;
import game.background.Backgroundmanager;
import game.gameboard.GameBoard;
import game.gamestates.GAME;

public class Ai implements Runnable {

	private GameBoard gameBoard;
	private GAME game;
	private Backgroundmanager background;
	private Point target = null;
	private game.gameboard.GameRules rules;

	private Integer[][] rating;
	private boolean stop;

	public Ai(game.gameboard.GameRules rules, GAME game,GameBoard gameBoard, Backgroundmanager background2) {
		this.gameBoard = gameBoard;
		this.game = game;
		this.background = background2;
		this.rules = rules;
		this.rating = new Integer[rules.getRow()][rules.getCol()];
	}
	
	private void setTarget() {
		calculateRating();
		ArrayList<Point> pointarr = new ArrayList<Point>();
		for(int x = 0;x < rules.getRow();x++) for(int y = 0;y < rules.getCol();y++) if(rating[x][y] > 1) pointarr.add(new Point(x, y));
		if(pointarr.size() <= 0) {
			Point random;
			int index = 0;
			do {
				random = new Point(new Random().nextInt(rules.getRow()), new Random().nextInt(rules.getCol()));
				if(rating[random.x][random.y] == 1) 
					if((!gameBoard.Arr_collision_contains(new Point(random.x, random.y - 1)) || random.y <= 0) && (!gameBoard.Arr_collision_contains(new Point(random.x, random.y + 1)) || random.y >= rules.getCol()-1) && 
							(!gameBoard.Arr_collision_contains(new Point(random.x - 1, random.y)) || random.x <= 0) && (!gameBoard.Arr_collision_contains(new Point(random.x + 1, random.y)) || random.x >= rules.getRow()-1)) pointarr.add(random);
					else if(index > 10) pointarr.add(random);
				index++;
			}while(pointarr.size() <= 0 && index < 5000);
		}
		if(pointarr.size() > 0) target = pointarr.get(new Random().nextInt(pointarr.size()));	
		else target = null;

	}

	private void calculateRating() {
		Point point;
		int count = 0;
		for(int x = 0;x < rules.getRow();x++) {
			for(int y = 0;y < rules.getCol();y++) {
				rating[x][y] = 0;
				count = 0;
				if(gameBoard.whatContains(new Point(x, y)) > -1 && gameBoard.whatContains(new Point(x, y)) != 1) {
					point = new Point(x - 1, y - 1);
					if(gameBoard.whatContains(point) != -1) if(gameBoard.whatContains(point) == 2) rating[x][y] = -1;
					point = new Point(x + 1, y - 1);
					if(gameBoard.whatContains(point) != -1) if(gameBoard.whatContains(point) == 2) rating[x][y] = -1;
					point = new Point(x + 1, y + 1);
					if(gameBoard.whatContains(point) != -1) if(gameBoard.whatContains(point) == 2) rating[x][y] = -1;
					point = new Point(x - 1, y + 1);
					if(gameBoard.whatContains(point) != -1) if(gameBoard.whatContains(point) == 2) rating[x][y] = -1;

					if(rating[x][y] != -1) {
						point = new Point(x, y - 1);
						if(gameBoard.whatContains(point) != -1) {
							if(gameBoard.whatContains(point) == 2) rating[x][y] = 2;
							else if(rating[x][y] <= 1) rating[x][y] = 1;
							if(gameBoard.whatContains(point) == 1 || test(point)) count++;
						}else count++;
						point = new Point(x + 1, y);
						if(gameBoard.whatContains(point) != -1) {
							if(gameBoard.whatContains(point) == 2) rating[x][y] = 2;
							else if(rating[x][y] <= 1) rating[x][y] = 1;
							if(gameBoard.whatContains(point) == 1 || test(point)) count++;
						}else count++;
						point = new Point(x, y + 1);
						if(gameBoard.whatContains(point) != -1) {
							if(gameBoard.whatContains(point) == 2) rating[x][y] = 2;
							else if(rating[x][y] <= 1) rating[x][y] = 1;
							if(gameBoard.whatContains(point) == 1 || test(point)) count++;
						}else count++;
						point = new Point(x - 1, y);
						if(gameBoard.whatContains(point) != -1) {
							if(gameBoard.whatContains(point) == 2) rating[x][y] = 2;
							else if(rating[x][y] <= 1)rating[x][y] = 1;
							if(gameBoard.whatContains(point) == 1 || test(point)) count++;
						}else count++;
						if(count >= 4) rating[x][y] = -1;
					}
					point = new Point(x, y);
					if(gameBoard.whatContains(point) != -1) if(gameBoard.whatContains(point) == 2) rating[x][y] = -2;
				}
			}
		}
	}

	private boolean test(Point point) {
		return rating[point.x][point.y] != null && rating[point.x][point.y] == -1;
	}

	public synchronized Integer[][] getRating(){
		return rating;
	}

	private void update() {
		if(target == null) setTarget();
		else {
			if(gameBoard.getCursor().getX() < target.x) gameBoard.cursorRight();
			else if(gameBoard.getCursor().getX() > target.x) gameBoard.cursorLeft();
			else if(gameBoard.getCursor().getY() < target.y) gameBoard.cursorDown();
			else if(gameBoard.getCursor().getY() > target.y) gameBoard.cursorUp();
			else if(gameBoard.getCursor().getX() == target.x && gameBoard.getCursor().getY() == target.y && !gameBoard.Arr_collision_contains(gameBoard.cursorPosition())) {
				if(!gameBoard.shotOnShip()) {
					game.setGameMode(5);
					stop = true;
				}
				setTarget();
				Point point = new Point(gameBoard.cursorAbsolutePosition());
				if (background != null) background.shotEffect(point.x, point.y);
			}else target = null;
		}
		
	}

	@Override
	public void run() {
		stop = false;
		while (!stop) {
			long lastTime = System.currentTimeMillis();

			update();
			
			int wishFPS = 30;
			long time = System.currentTimeMillis();
			time -= lastTime;
			if(1000 / wishFPS > time)time = (1000/wishFPS)-time;
			else time = 0;
			
			try {
				Thread.sleep(time);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
