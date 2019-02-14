package game.gamestates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class OptionMenu extends GameState {
	private static final int MENU_COLOR = 0;
	private static final int MENU_BACKGROUND = 1;
	private static final int MENU_BACK = 2;
	
	private static final int Round = 25;

	private Color lime;

	private int menuId;
	private int menuIdvertical;

	private boolean colorPickeractiv;

	private ColorPicker colorpicker;

	public OptionMenu(GameStateManager gameStateManager, Dimension screenSize, Graphics2D g2) {
		super(gameStateManager, screenSize, g2);

		lime = Color.white;
	}

	public void init() {
		menuId = 0;
		menuIdvertical = 0;
		colorPickeractiv = false;
		colorpicker = new ColorPicker(screensize, g2);
		colorpicker.setColor(gameStateManager.getColor());
	}

	public void draw() {
		String strTitle = "BattleShip";
		String strSubTitle = "Option Menu";

		String[] menuItems = {"Back"};

		g2.setColor(lime);        
		g2.setStroke(new BasicStroke(calculateHeight(0.4f)));
		int y = calculateHeight(15);

		g2.setPaint(new GradientPaint(50.0f, 50.0f, Color.LIGHT_GRAY, 50.0f, calculateHeight(20), lime));
		g2.setFont(new Font("System", Font.PLAIN, calculateHeight(8)));
		g2.drawString(strTitle,         
				(screensize.width - g2.getFontMetrics().stringWidth(strTitle)) / 2, y);
		y += calculateHeight(6);
		g2.setFont(new Font("System", Font.PLAIN, calculateHeight(5)));
		g2.drawString(strSubTitle, 
				(screensize.width - g2.getFontMetrics().stringWidth(strSubTitle)) / 2, y);
		y += calculateHeight(6.15f);

		colorPicker(y);

		g2.setFont(new Font("System", Font.PLAIN, calculateHeight(5)));
		y += calculateHeight(35.85f);
		
		String str = "Background";
		g2.setColor(new Color(0, 0, 0, 120));
		g2.fillRoundRect(screensize.width/2 - calculateHeight(40)/2, y - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7*2+1),Round,Round);
		if (menuId == MENU_BACKGROUND) {
			g2.setColor(lime);
			g2.setStroke(new BasicStroke(calculateHeight(0.4f)));
		}
		else {
			g2.setColor(lime);
			g2.setStroke(new BasicStroke(calculateHeight(0.1f)));
		}
		g2.setColor(new Color(0, 0, 0, 120));
		
		g2.fillRoundRect(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4, y + calculateHeight(2) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(15), calculateHeight(7),Round,Round);
		g2.fillRoundRect(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4, y + calculateHeight(2) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(15), calculateHeight(7),Round,Round);
		
		g2.setColor(lime);
		g2.setStroke(new BasicStroke(calculateHeight(0.1f)));
		g2.drawOval(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4 + calculateHeight(2), y + calculateHeight(3) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(2), calculateHeight(2));
		g2.drawOval(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4 + calculateHeight(6), y + calculateHeight(6) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(2), calculateHeight(2));
		g2.drawOval(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4 + calculateHeight(10), y + calculateHeight(4) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(2), calculateHeight(2));
		g2.drawOval(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4 + calculateHeight(6.8f), y + calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(2), calculateHeight(2));
		g2.drawOval(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4 + calculateHeight(6.2f), y + calculateHeight(4.4f) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(2), calculateHeight(2));
		g2.drawOval(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4 + calculateHeight(9), y + calculateHeight(2.5f) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(2), calculateHeight(2));
		
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(2), y + calculateHeight(3) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(6), y + calculateHeight(6) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(7.3f), y + calculateHeight(4) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(6.8f), y + calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(6.2f), y + calculateHeight(4.4f) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(9), y + calculateHeight(6.5f) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(1), y + calculateHeight(3) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(2.3f), y + calculateHeight(4.2f) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(8.3f), y + calculateHeight(6.1f) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(5.8f), y + calculateHeight(4) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		g2.fillOval(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4 + calculateHeight(3.2f), y + calculateHeight(2.4f) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(0.5f), calculateHeight(0.5f));
		
		
		if (menuId == MENU_BACKGROUND) {
			g2.setStroke(new BasicStroke(calculateHeight(0.4f)));
		}
		else {
			g2.setStroke(new BasicStroke(calculateHeight(0.1f)));
		}
		
		if(menuIdvertical == 0)g2.setColor(lime);
		else g2.setColor(new Color(0, 0, 0, 120));
		g2.drawRoundRect(screensize.width/2 - calculateHeight(15)/2 - calculateHeight(40)/4, y + calculateHeight(2) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(15), calculateHeight(7),Round,Round);
		if(menuIdvertical == 1)g2.setColor(lime);
		else g2.setColor(new Color(0, 0, 0, 120));
		g2.drawRoundRect(screensize.width/2 - calculateHeight(15)/2 + calculateHeight(40)/4, y + calculateHeight(2) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(15), calculateHeight(7),Round,Round);
		
		
		g2.setColor(lime);
		g2.drawRoundRect(screensize.width/2 - calculateHeight(40)/2, y - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7*2+1),Round,Round);
		g2.drawString(str, (screensize.width - g2.getFontMetrics().stringWidth(str)) / 2,y + (int)( g2.getFontMetrics().getStringBounds(str, g2).getHeight() - g2.getFontMetrics().getAscent()));
		y += calculateHeight(8*2);
		
		for (int index = 0; index < menuItems.length; index++) {
			g2.setColor(new Color(0, 0, 0, 120));
			g2.fillRoundRect(screensize.width/2 - calculateHeight(40)/2, y - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7),Round,Round);
			if (menuId == index + 2) {
				g2.setColor(lime);
				g2.setStroke(new BasicStroke(calculateHeight(0.4f)));
			}
			else {
				g2.setColor(lime);
				g2.setStroke(new BasicStroke(calculateHeight(0.1f)));
			}
			g2.drawRoundRect(screensize.width/2 - calculateHeight(40)/2, y - calculateHeight(5) + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()), calculateHeight(40), calculateHeight(7),Round,Round);
			g2.drawString(menuItems[index], (screensize.width - g2.getFontMetrics().stringWidth(menuItems[index])) / 2,y + (int)( g2.getFontMetrics().getStringBounds(menuItems[index], g2).getHeight() - g2.getFontMetrics().getAscent()));
			y += calculateHeight(8);
		}
	}

	public void colorPicker(int y) {
		g2.setColor(new Color(0, 0, 0, 120));
		g2.fillRoundRect(screensize.width/2 - calculateHeight(40)/2, y , calculateHeight(40), calculateHeight(31f),Round,Round);
		if (menuId == MENU_COLOR) {
			g2.setColor(lime);
			g2.setStroke(new BasicStroke(calculateHeight(0.4f)));
		}
		else {
			g2.setColor(lime);
			g2.setStroke(new BasicStroke(calculateHeight(0.1f)));
		}
		g2.drawRoundRect(screensize.width/2 - calculateHeight(40)/2, y , calculateHeight(40), calculateHeight(31f),Round,Round);

		g2.drawString("Ship Color", (screensize.width - g2.getFontMetrics().stringWidth("Ship Color")) / 2,y + calculateHeight(4f) + (int)( g2.getFontMetrics().getStringBounds("Ship Color", g2).getHeight() - g2.getFontMetrics().getAscent()));


		colorpicker.draw(screensize.width/2, y + calculateHeight(17));

		if(gameStateManager.isDebug()) {
			g2.setFont(new Font("System", Font.PLAIN, calculateHeight(2)));
			g2.setColor(Color.WHITE);
			Integer temp = colorpicker.getAngle();
			g2.drawString(temp + "°", screensize.width/2 + calculateHeight(40)/2 - calculateHeight(2) - g2.getFontMetrics().stringWidth(temp + "°")/2, (int) (y + calculateHeight(2) + g2.getFontMetrics().getStringBounds(temp + "°", g2).getHeight() - g2.getFontMetrics().getAscent()));
			temp = colorpicker.getY();
			g2.drawString(temp.toString(), screensize.width/2 + calculateHeight(40)/2 - calculateHeight(2) - g2.getFontMetrics().stringWidth(temp.toString())/2, (int) (y + calculateHeight(4) + g2.getFontMetrics().getStringBounds(temp.toString(), g2).getHeight() - g2.getFontMetrics().getAscent()));
			temp = (int) colorpicker.getSpeed();
			g2.drawString(temp.toString(), screensize.width/2 + calculateHeight(40)/2 - calculateHeight(2) - g2.getFontMetrics().stringWidth(temp.toString())/2, (int) (y + calculateHeight(6) + g2.getFontMetrics().getStringBounds(temp.toString(), g2).getHeight() - g2.getFontMetrics().getAscent()));

		}
	}

	public boolean processInput(KeyEvent event) {
		boolean result = true;

		if(colorPickeractiv) {
			colorpicker.processInput(event);
			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				colorPickeractiv = !colorPickeractiv;
				gameStateManager.setColor(colorpicker.getColor());
			}
		}else if (event.getKeyCode() == KeyEvent.VK_UP) {
			menuId--;
			if (menuId < 0) menuId = MENU_BACK;
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			menuId++;
			if (menuId > MENU_BACK) menuId = 0;
		}
		else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			switch (menuId) {
			case MENU_COLOR:
				colorPickeractiv = !colorPickeractiv;
				break;
			case MENU_BACKGROUND:
				if(menuIdvertical == 0) {
					gameStateManager.setbackground(0);
				}
				if(menuIdvertical == 1) {
					gameStateManager.setbackground(1);
				}
				break;
			case MENU_BACK:
				gameStateManager.setState(GameStateManager.STATE_MAIN_MENU, false);
				break;
			}
		}else if(menuId == MENU_BACKGROUND) {
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
				menuIdvertical--;
				if (menuIdvertical < 0) menuIdvertical = 1;
			}
			else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
				menuIdvertical++;
				if (menuIdvertical > 1) menuIdvertical = 0;
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
