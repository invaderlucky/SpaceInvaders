package model;

import processing.core.PApplet;

public class Bullet extends GameObject {

	private static final long serialVersionUID = 1L;
	private boolean used;

	public Bullet(PApplet p, int x, int y, int h, int w, boolean isVisible) {
		super(p, x, y, h, w, isVisible);
		setUsed(false);
	}
	
	public void display() {
		parent.noStroke();
		parent.fill(128, 0, 128);
		parent.rect(this.x, this.y, this.r.width, this.r.height);
	}

	public void move() {
		this.setY((int) this.getY() - 10);
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
