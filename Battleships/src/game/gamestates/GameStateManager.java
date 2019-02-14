package game.gamestates;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import game.Game;
import game.background.BackgroundParticals;
import game.background.BackgroundWaves;
import game.background.Backgroundmanager;

public class GameStateManager {
	public static final int STATE_MAIN_MENU = 0;
	public static final int STATE_PAUSE = 1;
	public static final int STATE_GAME = 2;
	public static final int STATE_MULTIPLAYER_MENU = 3;
	public static final int STATE_OPTION_MENU = 4;

	private Game parent;
	private Dimension screensize;
	private Graphics2D g2;

	private HashMap<Integer, GameState> gameStates;
	private GameState current;
	
	private HashMap<Integer, Backgroundmanager> backgroundmanager;
	private Backgroundmanager backgroundcurrent;

	private boolean ki;
	private Color color;
	private boolean debug;
	private boolean debugAdvanced = false;

	public GameStateManager(Game parent, Dimension screenSize, Graphics2D g2) {
		this.parent = parent;
		this.screensize = screenSize;
		this.g2 = g2;

		gameStates = new HashMap<Integer, GameState>();
		backgroundmanager = new HashMap<Integer, Backgroundmanager>();
	}

	public void init() {
		backgroundmanager.put(0, new BackgroundWaves(this, screensize, g2));
		backgroundmanager.put(1, new BackgroundParticals(this, screensize, g2));
		
		color = Color.ORANGE;

		gameStates.put(STATE_MAIN_MENU, new MainMenu(this, screensize, g2));
		gameStates.put(STATE_PAUSE, new Pause(this, screensize, g2));
		gameStates.put(STATE_GAME, new GAME(this, screensize, g2));
		gameStates.put(STATE_MULTIPLAYER_MENU, new MultiplayerMenu(this, screensize, g2));
		gameStates.put(STATE_OPTION_MENU, new OptionMenu(this, screensize, g2));
		
		setState(STATE_MAIN_MENU, true);
		
		debug = false;
	}

	public void update() {
		if (backgroundcurrent != null) backgroundcurrent.update();
		if (current != null) current.update();
	}

	public synchronized void draw() {
		if (backgroundcurrent != null) backgroundcurrent.draw();
		if (current != null) current.draw();
	}

	public boolean processInput(KeyEvent event) {
		boolean result = true;

		if (current != null) result = current.processInput(event); 

		return result;
	}

	public void setki(boolean ki) {
		this.ki = ki;
	}

	public void backgroundshipsSpawner(boolean shipsbool) {
		if (backgroundcurrent != null) backgroundcurrent.Spawner(shipsbool);
	}

	public boolean getki() {
		return ki;
	}

	public void setState(int gameState, boolean init) {
		if (gameState < gameStates.size()) {
			if (init) gameStates.get(gameState).init();
			current = gameStates.get(gameState);
		}
	}
	
	public void setbackground(int background) {
		if (background < gameStates.size()) {
			backgroundmanager.get(background).init();
			backgroundcurrent = backgroundmanager.get(background);
		}
	}

	public Game getParent() {
		return parent;
	}

	public void setParent(Game parent) {
		this.parent = parent;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public boolean isDebug() {
		return debug;
	}

	public void switchDebug() {
		debug = !debug;
	}

	public boolean isDebugAdvanced() {
		return debugAdvanced;
	}

	public void switchDebugAdvanced() {
		debugAdvanced = !debugAdvanced;
	}

	public Backgroundmanager getbackground() {
		return backgroundcurrent;
	}
}
