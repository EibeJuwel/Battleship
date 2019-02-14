package game.gameboard;

import java.util.ArrayList;

public class OfflineRules {

	private ArrayList<Integer> ships;

	public ArrayList<Integer> getShips() {
		ships = new ArrayList<Integer>();
		ships.add(5);
		ships.add(4);
		ships.add(3);
		ships.add(3);
		ships.add(2);
		return ships;
	}
	
}
