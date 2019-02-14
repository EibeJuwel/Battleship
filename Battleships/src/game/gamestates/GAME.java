package game.gamestates;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import game.background.Backgroundmanager;
import game.gameboard.GameBoard;
import game.gameboard.GameRules;
import game.gameboard.ai.Ai;

public class GAME extends GameState {
	private float size = 8;
	private Rectangle bounds;
	private GameBoard gameBoard1, gameBoard2;
	private int gameMode = 0;
	private String winnerMessage;
	private Backgroundmanager backgroundcurrent;

	private boolean ki;
	private Ai ai;
	
	private GameRules rules;
	
	private Thread aiThread;
	private boolean aistop;

	public GAME(GameStateManager gameStateManager, Dimension screenSize, Graphics2D g2) {
		super(gameStateManager, screenSize, g2);
	}

	public void init() {
		rules = new GameRules(10, 10, 1);

		ki = gameStateManager.getki();

		gameBoard1 = new GameBoard(rules, bounds, size, screensize, g2, gameStateManager.getColor());
		gameBoard1.init();
		gameBoard1.generateShips();
		gameBoard1.setPosition(new Point(screensize.width/2, screensize.height/2), 80, screensize);

		gameBoard2 = new GameBoard(rules, bounds, size, screensize, g2, gameStateManager.getColor());
		gameBoard2.init();
		gameBoard2.generateShips();
		gameBoard2.setPosition(new Point(screensize.width/2, screensize.height/2), 80, screensize);

		if(ki) gameMode = 2;
		else gameMode = 1;

		backgroundcurrent = gameStateManager.getbackground();
		backgroundcurrent.Spawner(false);
		
		if(ki) {
			ai = new Ai(rules, this,gameBoard1, backgroundcurrent);
		}
		
		aiThread = new Thread(ai);
		
		winnerMessage = null;
	}

	@SuppressWarnings("deprecation")
	public void update() {
		updateculcate();
		if(gameBoard1.isShipAlive() && gameBoard2.isShipAlive()) {
			if(ki && gameMode == 6) {
				if(!aiThread.isAlive()) {
					aiThread = new Thread(ai);
					aiThread.start();
				}
			}
		}else if(winnerMessage == null){
			aiThread.stop();
			if(!gameBoard1.isShipAlive()) {
				winnerMessage = "Spieler Blau hat gewonnen";
			}
			if(!gameBoard2.isShipAlive()) {
				winnerMessage = "Spieler Rot hat gewonnen";
			}
		}
	}

	public void draw() {
		g2.setFont(new Font("System", Font.BOLD, calculateHeight(2)));
		if(winnerMessage == null) {
			if(gameMode == 1) {
				g2.setColor(Color.white);
				g2.drawString("(F) Drücken um schiffe zu platzieren und der andere spieler bitte umdrehen", screensize.width/2 - g2.getFontMetrics().stringWidth("(F) Drücken um schiffe zu platzieren und der andere spieler bitte umdrehen")/2,
						(int) (screensize.height/2 + g2.getFontMetrics().getStringBounds("(F) Drücken um schiffe zu platzieren und der andere spieler bitte umdrehen", g2).getHeight() - g2.getFontMetrics().getAscent()));
			}else if(gameMode == 2) {
				gameBoard1.draw(Color.RED, true, true);
				g2.setColor(Color.white);
				g2.drawString("Bitte (F) Drücken wenn sie fertig sind", screensize.width/2 - g2.getFontMetrics().stringWidth("Bitte (F) Drücken wenn sie fertig sind")/2,
						(int) (gameBoard1.getBounds().y + gameBoard1.getBounds().height + calculateHeight(2) + g2.getFontMetrics().getStringBounds("Bitte (F) Drücken wenn sie fertig sind", g2).getHeight() - g2.getFontMetrics().getAscent()));
			}else if(gameMode == 3) {
				g2.setColor(Color.white);
				g2.drawString("(F) Drücken um schiffe zu platzieren und der andere spieler bitte umdrehen", screensize.width/2 - g2.getFontMetrics().stringWidth("(F) Drücken um schiffe zu platzieren und der andere spieler bitte umdrehen")/2,
						(int) (screensize.height/2 + g2.getFontMetrics().getStringBounds("(F) Drücken um schiffe zu platzieren und der andere spieler bitte umdrehen", g2).getHeight() - g2.getFontMetrics().getAscent()));
			}else if(gameMode == 4) {
				gameBoard2.draw(Color.BLUE, true, true);
				g2.setColor(Color.white);
				g2.drawString("Bitte (F) Drücken wenn sie fertig sind", screensize.width/2 - g2.getFontMetrics().stringWidth("Bitte (F) Drücken wenn sie fertig sind")/2,
						(int) (gameBoard2.getBounds().y + gameBoard2.getBounds().height + calculateHeight(2) + g2.getFontMetrics().getStringBounds("Bitte (F) Drücken wenn sie fertig sind", g2).getHeight() - g2.getFontMetrics().getAscent()));
			}else if(gameMode == 5) {
				gameBoard1.draw(Color.BLUE, false, false);
				gameBoard2.draw(Color.RED, false, true);
				g2.setColor(Color.RED);
				g2.drawString("Spieler Rot", screensize.width/4 - g2.getFontMetrics().stringWidth("Spieler Rot")/2, (int) (gameBoard2.getBounds().y + gameBoard2.getBounds().height + calculateHeight(2) + g2.getFontMetrics().getStringBounds("Spieler Rot", g2).getHeight() - g2.getFontMetrics().getAscent()));
			}else if(gameMode == 6) {
				gameBoard1.draw(Color.BLUE, false, true);
				gameBoard2.draw(Color.RED, false, false);
				g2.setColor(Color.BLUE);
				g2.drawString("Spieler Blau", screensize.width/4*3 - g2.getFontMetrics().stringWidth("Spieler Blau")/2, (int) (gameBoard1.getBounds().y + gameBoard1.getBounds().height + calculateHeight(2) + g2.getFontMetrics().getStringBounds("Spieler Blau", g2).getHeight() - g2.getFontMetrics().getAscent()));
			}
		}else {
			gameBoard1.draw(Color.BLUE, true, false);
			gameBoard2.draw(Color.RED, true, false);
		}
		if(gameStateManager.isDebug()) {
			if(ki) gameBoard1.drawrating(ai.getRating(), gameStateManager.isDebugAdvanced());
			if(gameMode == 6 || gameMode == 5) {
				String count = "moves remaining: " + ((rules.getCol() * rules.getRow()) - gameBoard1.Arr_collisionCountofUsing() );
				g2.setFont(new Font("System", Font.BOLD, calculateHeight(2)));
				g2.setColor(Color.WHITE);
				g2.drawString(count, screensize.width/4*3 - g2.getFontMetrics().stringWidth(count)/2, (int) (gameBoard1.getBounds().y - calculateHeight(1) + g2.getFontMetrics().getStringBounds(count, g2).getHeight() - g2.getFontMetrics().getAscent()));
				count = "moves remaining: " + ((rules.getCol() * rules.getRow()) - gameBoard2.Arr_collisionCountofUsing() );
				g2.setColor(Color.WHITE);
				g2.drawString(count, screensize.width/4 - g2.getFontMetrics().stringWidth(count)/2, (int) (gameBoard2.getBounds().y - calculateHeight(1) + g2.getFontMetrics().getStringBounds(count, g2).getHeight() - g2.getFontMetrics().getAscent()));
			}
		}
		if(winnerMessage != null) {
			g2.setFont(new Font("System", Font.BOLD, calculateHeight(10)));
			g2.setColor(Color.WHITE);
			g2.drawString(winnerMessage, screensize.width/2 - g2.getFontMetrics().stringWidth(winnerMessage)/2, (int)( screensize.height/2 + g2.getFontMetrics().getStringBounds(winnerMessage, g2).getHeight() + g2.getFontMetrics().getAscent()));
		}
	}

	public void updateculcate() {
		switch (gameMode) {
		case 2: case 4:
			gameBoard1.setPosition(new Point(screensize.width/2, screensize.height/2), 80, screensize);
			gameBoard2.setPosition(new Point(screensize.width/2, screensize.height/2), 80, screensize);
			break;
		case 5: case 6:
			gameBoard1.setPosition(new Point(screensize.width/4*3, screensize.height/2), (int) (screensize.width/25), screensize);
			gameBoard2.setPosition(new Point(screensize.width/4, screensize.height/2), (int) (screensize.width/25), screensize);
			break;
		}
	}

	public boolean processInput(KeyEvent event) {
		boolean result = true;
		if(winnerMessage == null) {
			if(gameMode == 1) {
				if(event.getKeyCode() == KeyEvent.VK_F) {
					gameMode = 2;
				}
			}else if(gameMode == 2) {
				cursorControll(event);

				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					gameBoard1.grapShip();
				}
				if(event.getKeyCode() == KeyEvent.VK_SPACE) {
					gameBoard1.rotatetShip();
				}
				if(event.getKeyCode() == KeyEvent.VK_F && !gameBoard1.isShipGraped()) {
					if(ki) gameMode = 5;
					else gameMode = 3;
				}
			}else if(gameMode == 3) {
				if(event.getKeyCode() == KeyEvent.VK_F) {
					gameMode = 4;
				}
			}else if(gameMode == 4) {
				cursorControll(event);

				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					gameBoard2.grapShip();
				}
				if(event.getKeyCode() == KeyEvent.VK_SPACE) {
					gameBoard2.rotatetShip();
				}
				if(event.getKeyCode() == KeyEvent.VK_F  && !gameBoard2.isShipGraped()) {
					gameMode = 5;
				}
			}else if(gameMode == 5 || gameMode == 6) {
				cursorControll(event);

				if(gameMode == 5 && event.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!gameBoard2.shotOnShip()) {
						gameMode = 6;
					}
					if (backgroundcurrent != null) backgroundcurrent.shotEffect(gameBoard2.cursorAbsolutePosition().x, gameBoard2.cursorAbsolutePosition().y);
				}else if(gameMode == 6 && event.getKeyCode() == KeyEvent.VK_ENTER && !ki) {
					if(!gameBoard1.shotOnShip()) {
						gameMode = 5;
					}
					if (backgroundcurrent != null) backgroundcurrent.shotEffect(gameBoard1.cursorAbsolutePosition().x, gameBoard1.cursorAbsolutePosition().y);
				}
			}

			if(event.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameStateManager.setState(GameStateManager.STATE_PAUSE, true);
			}
		}else {
			if(event != null) {
				gameStateManager.setState(GameStateManager.STATE_MAIN_MENU, false);
				gameStateManager.backgroundshipsSpawner(true);
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

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	private void cursorControll(KeyEvent e) {
		if(gameMode == 2 || (gameMode == 6 && !ki)) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				gameBoard1.cursorUp();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				gameBoard1.cursorDown();
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				gameBoard1.cursorLeft();
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				gameBoard1.cursorRight();
			}
		}else if(gameMode == 4 || gameMode == 5) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				gameBoard2.cursorUp();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				gameBoard2.cursorDown();
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				gameBoard2.cursorLeft();
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				gameBoard2.cursorRight();
			}
		}
	}
}
