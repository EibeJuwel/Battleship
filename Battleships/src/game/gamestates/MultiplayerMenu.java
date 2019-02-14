package game.gamestates;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MultiplayerMenu extends GameState {
	private static final int MENU_HOST = 0;
	private static final int MENU_JOIN = 1;
	private static final int MENU_BACK = 2;

	private Color lime;

	private int menuId;

	public MultiplayerMenu(GameStateManager gameStateManager, Dimension screenSize, Graphics2D g2) {
		super(gameStateManager, screenSize, g2);

		lime = Color.WHITE;
	}

	public void init() {
		menuId = 0;
	}

	public void draw() {
		String strTitle = "BattleShip";
		String strSubTitle = "Multiplayer Menu";

		String[] menuItems = {"Host Game", "Join Game", "Back"};

		g2.setColor(lime);        

		int y = calculateHeight(15);
		
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
			g2.fillRect(screensize.width/2 - calculateHeight(40)/2, y + calculateHeight(8) * index - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7));
			if (menuId == index) g2.setColor(lime);
			else g2.setColor(Color.lightGray);
			g2.drawRect(screensize.width/2 - calculateHeight(40)/2, y + calculateHeight(8) * index - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7));
			g2.drawString(menuItems[index], (screensize.width - g2.getFontMetrics().stringWidth(menuItems[index])) / 2,y + calculateHeight(8) * index + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()));
		}
	}

	public boolean processInput(KeyEvent event) {
		boolean result = true;

		if (event.getKeyCode() == KeyEvent.VK_UP) {
			menuId--;
			if (menuId < 0) menuId = MENU_BACK;
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			menuId++;
			if (menuId > MENU_BACK) menuId = 0;
		}
		else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			switch (menuId) {
			case MENU_HOST:
				break;
			case MENU_JOIN:
				break;
			case MENU_BACK:
				gameStateManager.setState(GameStateManager.STATE_MAIN_MENU, false);
				break;
			}
		}

		return result;
	}
}
