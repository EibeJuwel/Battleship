package game.gamestates;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class GameState extends Basic.BasicCalculate{
	protected GameStateManager gameStateManager;
	protected Graphics2D g2; 

	public GameState(GameStateManager gameStateManager, Dimension screenSize, Graphics2D g2) {
		super(screenSize);
		this.gameStateManager = gameStateManager;
		this.g2 = g2;
	}

	public void init() {
		
	}

	public void update() {

	}

	public void draw() {

	}
	
	public boolean processInput(KeyEvent event) {
		return true;
	}
}
