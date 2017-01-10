package model;

import event.BulletCollisionEvent;
import listener.BulletCollisionHandler;
import manager.ScriptManager;
import processing.core.PApplet;

public class Enemy extends GameObject implements BulletCollisionHandler {
	public boolean alive;
	private int startX;

	private static final long serialVersionUID = 1L;

	public Enemy(PApplet p, int x, int y, int h, int w, boolean isVisible) {
		super(p, x, y, h, w, isVisible);
		alive = true;
		setStartX(x);
	}

	public void display() {
		parent.noStroke();
		parent.fill(128, 0, 255);
		parent.rect(this.x, this.y, this.r.width, this.r.height);
	}
	
	public void move() {
		ScriptManager.loadScript("scripts/move.js");
		ScriptManager.bindArgument("game_object", this);
		ScriptManager.executeScript();
	}

	@Override
	public void onEvent(BulletCollisionEvent b) {
		if (b.getHit().equals(this))
			alive = false;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}
}
