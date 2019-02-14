package game.background;

import java.awt.Dimension;
import java.awt.Point;

import Basic.BasicCalculate;

public class Particel extends BasicCalculate{

	protected float x;
	protected float y;
	protected float angle;
	protected float speed;
	protected float alpha;
	private boolean fadin;
	protected float size;

	public Particel(int x, int y, Dimension screensize) {
		super(screensize);
		this.x = x;
		this.y = y;
		this.angle = (float) ((Math.random() * 360) + 0);
		this.speed = (float) ((Math.random() * 0.500000f) + 0.1f);
		this.size = (float) ((Math.random() * 0.5f) + 0.1f);
		this.alpha = 0;
		this.fadin = true;
	}

	public void update() {
		x += Math.cos(Math.toRadians(angle)) * calculateHeight(speed);
		y += Math.sin(Math.toRadians(angle)) * calculateHeight(speed);
		if(fadin) {
			alpha += 5;
			if(alpha >= 255) {
				alpha = 255;
				fadin = false;
			}
		}else {
			alpha -= 0.5f;
			if(alpha < 0) {
				alpha = 0;
			}
		}
	}

	public synchronized void updateangle(Point point, float strok) {
		strok = (float) (screensize.getWidth() - point.distance(x, y)) / (10000 * strok);
		if(strok < 0) strok = 0;
		float angle = (float) Math.toDegrees(Math.atan2(point.y - y,point.x - x));
		if(x <= point.x) {
			if(y <= point.y) {
				if(angle >= this.angle) this.angle += strok;
				else if(angle <= this.angle) this.angle -= strok;
			}else {
				if(angle >= this.angle) this.angle += strok;
				else if(angle <= this.angle) this.angle -= strok;
			}
		}else  {
			if(y <= point.y) {
				if(angle > this.angle) this.angle += strok;
				else if(angle < this.angle) this.angle -= strok;
			}else  {
				if(angle > this.angle) this.angle += strok;
				else if(angle < this.angle) this.angle -= strok;
			}
		}


		/*strok /= 100;
		//if(this.angle < 0) this.angle = 360;
		if(this.angle >= 360) this.angle = 1;

		if((int)this.angle != (int)angle) {
			speed -= strok;
		}else {
			if(speed < 0.5f) speed += strok;
			else if(speed > 0.5f) speed = 0.5f;
		}

		if(speed <= 0) this.angle = angle;*/
	}

	public synchronized boolean isComplett() {
		boolean bool = false;

		bool = alpha <= 0;

		if(x < (-calculateHeight(size)) || x > screensize.width + calculateHeight(size)) {
			bool = true;
		}
		if(y < (-calculateHeight(size)) || y > screensize.height + calculateHeight(size)) {
			bool = true;
		}
		return bool;
	}

}
