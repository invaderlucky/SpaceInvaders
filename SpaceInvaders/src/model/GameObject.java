package model;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.UUID;

import processing.core.PApplet;
import processing.core.PShape;

public abstract class GameObject extends PShape implements Serializable {
	private static final long serialVersionUID = 1L;

	public transient PApplet parent;
	int x;
	int y;
	boolean isVisible;
	Rectangle r;
	String guid;
	
	public GameObject(PApplet p, int x, int y, int h, int w, boolean isVisible) {
		parent = p;
		this.x = x;
		this.y = y;
		this.isVisible = isVisible;
		r = new Rectangle(x, y, w, h);
		guid = UUID.randomUUID().toString();
	}

	public String getGuid() {
		return guid;
	}
	
	public void setX(int x) {
		this.x = x;
		this.r.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
		this.r.y = y;
	}
	
	public void setVisibility(boolean visible) {
		this.isVisible = visible;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public Rectangle getR() {
		return r;
	}
}
