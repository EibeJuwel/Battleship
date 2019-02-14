package game.background;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import game.gamestates.GameStateManager;

public class BackgroundWaves extends Backgroundmanager{

	private ArrayList<Waves> waves;
	private ArrayList<Ship> ships;

	private boolean Shipsbool;

	public BackgroundWaves(GameStateManager gameStateManager, Dimension screensize, Graphics2D g2) {
		super(gameStateManager, screensize, g2);
	}

	public void init() {
		waves = new ArrayList<Waves>();
		ships = new ArrayList<Ship>();
		Shipsbool = true;
	}

	public synchronized boolean isShipsbool() {
		return Shipsbool;
	}

	public synchronized void Spawner(boolean bool) {
		Shipsbool = bool;
	}

	public void update() {
		synchronized (waves) {
			for(int i = 0;i < waves.size();i++) {
				if(waves.get(i).isComplett()) {
					waves.remove(i);
				}
			}
		}
		synchronized (ships) {
			for(int i = 0;i < ships.size();i++) {
				if(ships.get(i).isComplett()) {
					ships.remove(i);
				}
			}
		}
		synchronized (waves) {
			if(new Random().nextInt(30) == 1 && waves.size() < 3 && ships.size() <= 0) {
				waves.add(new Waves(new Random().nextInt(screensize.width), new Random().nextInt(screensize.height),
						(float)(Math.random() * 2.5f + 0.1), (int) (Math.random() * 10) + 2, screensize, g2));
			}
		}
		synchronized (ships) {
			if(new Random().nextInt(2000) == 1 && ships.size() < 1 && Shipsbool) {
				ships.add(new Ship(this, screensize, g2));
			}
			if((new Random().nextInt(4000) == 1 && ships.size() >= 1) || (!Shipsbool && ships.size() >= 1)) {
				ships.get((int)(Math.random() * ships.size()-1 + 0)).setDestroy();;
			}
		}
	}

	public void draw() {
		g2.setColor(new Color(14, 100, 180));

		g2.fillRect(0, 0, screensize.width, screensize.height);;	
		synchronized (ships) {
			for(int i = 0;i < ships.size();i++) {
				ships.get(i).update();
				ships.get(i).draw();
			}
		}
		synchronized (waves) {
			for(int i = 0;i < waves.size();i++) {
				waves.get(i).update();
				waves.get(i).draw();
			}
		}
	}

	public void shotEffect(int x, int y) {
		synchronized (waves) {
			if(waves.size() < 10)
				waves.add(new Waves(x, y, (float)(Math.random() * 1.2 + 0.8), (int) (Math.random() * 6) + 4, screensize, g2));
		}
	}
}
