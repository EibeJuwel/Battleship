package game.gameboard;

import java.util.ArrayList;

public class GameRules {

	private int row;
	private int col;
	private int mode;
	
	private OfflineRules offlineRules;
	
	public GameRules(int row, int col, int mode) {
		this.row = row;
		this.col = col;
		this.mode = mode;
		
		offlineRules = new OfflineRules();
		
	}
	
	public ArrayList<Integer> getShipsLenght(){
		return offlineRules.getShips();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public boolean shipCollision(ArrayList<Ship> ships, Ship ship) {
		boolean bool = false;
		int lenght;
		switch(ship.getAlignment()) {
		case 0:
			for(int i = 0; i < ships.size() && !bool; i++) {
				if(ship != ships.get(i)) {
					lenght = ship.getLength() - 1;
					while(lenght >= 0 && !bool) {
						bool = ships.get(i).contains(ship.getX(), ship.getY() - lenght);
						lenght--;
					}
					lenght = ship.getLength() + 1;
					while(lenght >= 0 && !bool) {
						bool = ships.get(i).contains(ship.getX() - 1, ship.getY() + 1 - lenght);
						lenght--;
					}
					lenght = ship.getLength() + 1;
					while(lenght >= 0 && !bool) {
						bool = ships.get(i).contains(ship.getX() + 1, ship.getY() + 1 - lenght);
						lenght--;
					}
					lenght = ship.getLength();
					if(!bool) {
						bool = ships.get(i).contains(ship.getX(), ship.getY() + 1) || ships.get(i).contains(ship.getX(), ship.getY() - lenght);
					}
				}

			}
			break;
		case 1:
			for(int i = 0; i < ships.size() && !bool; i++) {
				if(ship != ships.get(i)) {
					lenght = ship.getLength() - 1;
					while(lenght >= 0 && !bool) {
						bool = ships.get(i).contains(ship.getX() - lenght, ship.getY());
						lenght--;
					}
					lenght = ship.getLength() + 1;
					while(lenght >= 0 && !bool) {
						bool = ships.get(i).contains(ship.getX() + 1 - lenght, ship.getY() - 1);
						lenght--;
					}
					lenght = ship.getLength() + 1;
					while(lenght >= 0 && !bool) {
						bool = ships.get(i).contains(ship.getX() + 1 - lenght, ship.getY() + 1);
						lenght--;
					}
					lenght = ship.getLength();
					if(!bool) {
						bool = ships.get(i).contains(ship.getX() + 1, ship.getY()) || ships.get(i).contains(ship.getX() - lenght, ship.getY());
					}
				}
			}
			break;
		}
		return bool;
	}

}
