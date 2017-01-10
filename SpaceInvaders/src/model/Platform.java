package model;

import processing.core.PApplet;

public class Platform extends GameObject {
	private static final long serialVersionUID = 1L;
	private int speed;
	private int direction;
	private int leftEdge;
	private int rightEdge;

	public Platform(PApplet p, int x, int y, int h, int w, boolean isVisible, int speed) {
		super(p, x, y, h, w, isVisible);
		this.speed = speed;
		direction = 1;
		leftEdge = x;
		rightEdge = x + 100;
	}
	
	// Move the platform left or right
	public void move() {
		if ((x + (speed * direction)) <= leftEdge)
			direction = 1;
		else if ((x + (speed * direction)) >= rightEdge)
			direction = -1;
		setX(x + (speed * direction));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDirection() {
		return direction;
	}

	public int getLeftEdge() {
		return leftEdge;
	}

	public int getRightEdge() {
		return rightEdge;
	}

	public void display() {
		parent.noStroke();
		parent.fill(0, 128, 255);
		parent.rect(this.x, this.y, this.r.width, this.r.height);
	}
	
}
