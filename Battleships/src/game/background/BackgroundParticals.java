package game.background;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import game.gamestates.GameStateManager;

public class BackgroundParticals extends Backgroundmanager{

	ArrayList<Particel> particals;

	private boolean magnetbool;

	private Point magnetpoint;
	private int magnettimer;

	public BackgroundParticals(GameStateManager gameStateManager, Dimension screensize, Graphics2D g2) {
		super(gameStateManager, screensize, g2);
	}

	public void init() {
		particals = new ArrayList<Particel>();
		magnetbool = true;
		magnetpoint = null;
	}

	public synchronized boolean isSpawner() {
		return magnetbool;
	}

	public synchronized void Spawner(boolean bool) {
		magnetbool = bool;
	}

	private synchronized void createParticals() {
		int avenge = (int) ((Math.random() * 100) + 10);
		if(particals.size() < 2000) {
			for(int i = 0;i < avenge;i++) {
				particals.add(new Particel(new Random().nextInt(screensize.width), new Random().nextInt(screensize.height), screensize));
			}
		}

	}

	private synchronized void createParticals(int x,int y, int max, int min) {
		int avenge = (int) ((Math.random() * max) + min);
		if(particals.size() < 4000) {
			for(int i = 0;i < avenge;i++) {
				particals.add(new Particel(x, y, screensize));
			}
		}

	}

	public void update() {
		synchronized (particals) {
			for(int i = 0;i < particals.size();i++) {
				particals.get(i).update();
			}
			/*if(magnetbool && new Random().nextInt(5) == 1) {
				Point point = new Point(new Random().nextInt(screensize.width), new Random().nextInt(screensize.height));
				for(int i = 0;i < particals.size();i++) {
					particals.get(i).updateangle(point, 1f);
				}
			}*/
			if(magnetpoint != null && magnettimer > 0 && magnetbool) {
				for(int i = 0;i < particals.size();i++) {
					particals.get(i).updateangle(magnetpoint, 0.15f);
				}
				magnettimer--;
				if(magnettimer <= 0) {
					magnetpoint = null;
				}
			}else {
				if(new Random().nextInt(30) == 1 && magnetbool) {
					magnetpoint = new Point(new Random().nextInt(screensize.width), new Random().nextInt(screensize.height));
					magnettimer = new Random().nextInt(50);
				}
			}

			int index = 0;
			while(index < particals.size()) {
				if(particals.get(index).isComplett()) {
					particals.remove(index);
				}else {
					index++;
				}
			}
			if(new Random().nextInt(10) == 1) {
				createParticals();
			}
		}
	}

	public void draw() {
		g2.setColor(new Color(14, 100, 180));
		g2.fillRect(0, 0, screensize.width, screensize.height);
		g2.setColor(Color.WHITE);
		synchronized (particals) {
			int x, y, strok, size;
			for(int i = 0;i < particals.size();i++) {
				x = (int) particals.get(i).x;
				y = (int) particals.get(i).y;
				strok = (int) particals.get(i).alpha;
				size = calculateHeight(particals.get(i).size);
				g2.setColor(new Color(255, 255, 255, strok));
				g2.fillOval(x - size/2, y - size/2, size, size);
			}

			if(gameStateManager.isDebug()) {
				g2.setColor(new Color(255, 255, 255));
				g2.drawString(String.valueOf(particals.size()), 5, 60);
				if(gameStateManager.isDebugAdvanced()) {
					int angle;
					float speed;
					g2.setStroke(new BasicStroke(calculateHeight(0.1f)));
					for(int i = 0;i < particals.size();i++) {
						x = (int) particals.get(i).x;
						y = (int) particals.get(i).y;
						speed = (float) particals.get(i).speed;
						angle = (int) particals.get(i).angle;
						g2.setColor(Color.red);

						g2.drawLine(x, y,(int)(x + (Math.cos(Math.toRadians(angle)) * calculateHeight(speed*5))),(int) (y + (Math.sin(Math.toRadians(angle)) * calculateHeight(speed*5))));
					}
					if(magnetpoint != null)g2.fillOval(magnetpoint.x - 10, magnetpoint.y - 10, 20, 20);
				}
			}
		}
	}

	public void shotEffect(int x, int y) {
		synchronized (particals) {
			createParticals(x, y, 200, 100);
		}
	}

}
