package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import game.gamestates.GameStateManager;

@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable, KeyListener {
	private static final Cursor INVISIBLE_CURSOR =
			Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(0, 0), "invisible");
	
	protected static final int wishFPS = 30;
	
	private Dimension screenSize;

	private BufferedImage image;
	private Graphics2D g2;

	private Thread thread;
	private boolean running;

	private GameStateManager gameStateManager;

	private int frames;

	private long currentFrame;

	private long firstFrame;

	private int fps;

	private long delayFrame;
	
	private Thread updateThread;
	private Update update;

	public Game() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocation(0, 0);
		setSize(screenSize);
		setResizable(false);
		setUndecorated(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setIgnoreRepaint(true);

		setCursor(INVISIBLE_CURSOR);
		
		setVisible(true);
		
		System.setProperty("-Dsun.java2d.opengl", "true");
		System.setProperty("sun.java2d.opengl","true");
	}

	@Override
	public void addNotify() {
		super.addNotify();

		if (thread == null) {
			thread = new Thread(this);

			addKeyListener(this);

			running = true;
		}
	}

	public void init() {
		image = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		g2 = image.createGraphics();
		

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		
		gameStateManager = new GameStateManager(this, screenSize, g2);
		gameStateManager.init();
		
		gameStateManager.setbackground(1);
		
		update = new Update(this, gameStateManager);
		updateThread = new Thread(update);
	}

	public void run() {
		init();
		while (running) {
			long lastTime = System.currentTimeMillis();
			
			//gameStateManager.update();
			
			
			if(!updateThread.isAlive()) {
				updateThread.start();
			}
			
			draw();
			drawToScreen();
			
			long time = System.currentTimeMillis();
			time -= lastTime;
			if(1000 / wishFPS > time)time = (1000/wishFPS)-time;
			else time = 0;
			
			try {
				Thread.sleep(time);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		dispose();
	}
	
	public void draw() {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, screenSize.width, screenSize.height);
		
		gameStateManager.draw();
		
		frames++;
		currentFrame = System.currentTimeMillis();
		if(currentFrame > firstFrame + 1000){
			firstFrame = currentFrame;
			fps = frames;
			frames = 0;
		}
		if(gameStateManager.isDebug()) {
			g2.setFont(new Font("System", Font.BOLD, 20));
			g2.setColor(Color.WHITE);
			if(gameStateManager.isDebugAdvanced()) {
				g2.drawString(String.valueOf(currentFrame - delayFrame), 5, 40);
			}
			g2.drawString(String.valueOf(fps), 5, 20);
		}
		delayFrame = currentFrame;
	}

	public void drawToScreen() {
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, screenSize.width, screenSize.height, null);
		g2.dispose();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		running = gameStateManager.processInput(event);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
