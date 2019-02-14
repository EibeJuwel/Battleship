package game.background;

import java.awt.Dimension;
import java.awt.Graphics2D;

import Basic.BasicCalculate;
import game.gamestates.GameStateManager;

public class Backgroundmanager extends BasicCalculate {
	protected GameStateManager gameStateManager;
	protected Graphics2D g2; 
	
	public Backgroundmanager(GameStateManager gameStateManager, Dimension screensize, Graphics2D g2) {
		super(screensize);
		this.gameStateManager = gameStateManager;
		this.g2 = g2;
	}

	public void init() {
		
	}

	public void update() {

	}

	public void draw() {

	}

	public void shotEffect(int x, int y) {
		
	}

	public void Spawner(boolean bool) {
		
	}
}
