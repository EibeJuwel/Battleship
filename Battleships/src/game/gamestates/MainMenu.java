package game.gamestates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MainMenu extends GameState {
	private static final int MENU_GAME_START = 0;
	private static final int MENU_GAME_START_KI = 1;
	private static final int MENU_MULTIPLAYER = 2;
	private static final int MENU_OPTIONS = 3;
	private static final int MENU_EXIT = 4;
	
	private static final int Round = 25;

	private Color lime;


	private int menuId;

	public MainMenu(GameStateManager gameStateManager, Dimension screenSize, Graphics2D g2) {
		super(gameStateManager, screenSize, g2);

		lime = Color.WHITE;
	}

	public void init() {
		menuId = 0;
		gameStateManager.backgroundshipsSpawner(true);
	}

	public void draw() {
		String strTitle = "BattleShip";
		String strSubTitle = "Main Menu";

		String[] menuItems = {"Start Game","Start Game AI", "Multiplayer", "Options", "Exit"};
 
		int y = calculateHeight(15);

		g2.setPaint(new GradientPaint(50.0f, 50.0f, Color.LIGHT_GRAY, 50.0f, calculateHeight(20), lime));
		g2.setFont(new Font("System", Font.PLAIN, calculateHeight(8)));
		g2.drawString(strTitle,         
				(screensize.width - g2.getFontMetrics().stringWidth(strTitle)) / 2, y);
		y += calculateHeight(6);
		g2.setFont(new Font("System", Font.PLAIN, calculateHeight(5)));
		g2.drawString(strSubTitle, 
				(screensize.width - g2.getFontMetrics().stringWidth(strSubTitle)) / 2, y);
		y += calculateHeight(10);
		for (int index = 0; index < menuItems.length; index++) {
			g2.setColor(new Color(0, 0, 0, 120));
			g2.fillRoundRect(screensize.width/2 - calculateHeight(40)/2, y + calculateHeight(8) * index - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7),Round,Round);
			if (menuId == index) {
				g2.setColor(lime);
				g2.setStroke(new BasicStroke(calculateHeight(0.4f)));
			}
			else {
				g2.setColor(lime);
				g2.setStroke(new BasicStroke(calculateHeight(0.1f)));
			}
			g2.drawRoundRect(screensize.width/2 - calculateHeight(40)/2, y + calculateHeight(8) * index - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7),Round,Round);
			g2.drawString(menuItems[index], (screensize.width - g2.getFontMetrics().stringWidth(menuItems[index])) / 2,y + calculateHeight(8) * index + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()));
		}
	}

	public boolean processInput(KeyEvent event) {
		boolean result = true;

		if (event.getKeyCode() == KeyEvent.VK_UP) {
			menuId--;
			if (menuId < 0) menuId = MENU_EXIT;
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			menuId++;
			if (menuId > MENU_EXIT) menuId = 0;
		}
		else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			switch (menuId) {
			case MENU_GAME_START:
				gameStateManager.setki(false);
				gameStateManager.setState(GameStateManager.STATE_GAME, true);
				break;
			case MENU_GAME_START_KI:
				gameStateManager.setki(true);
				gameStateManager.setState(GameStateManager.STATE_GAME, true);
				break;
			case MENU_MULTIPLAYER:
				gameStateManager.setState(GameStateManager.STATE_MULTIPLAYER_MENU, true);
				break;
			case MENU_OPTIONS:
				gameStateManager.setState(GameStateManager.STATE_OPTION_MENU, true);
				break;
			case MENU_EXIT:
				result = false;
				break;
			}
		}

		if(event.getKeyCode() == KeyEvent.VK_F1) {
			gameStateManager.switchDebug();
		}
		if(event.getKeyCode() == KeyEvent.VK_F2) {
			gameStateManager.switchDebugAdvanced();
		}

		return result;
	}
}
