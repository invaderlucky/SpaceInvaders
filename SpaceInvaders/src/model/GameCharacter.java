package model;

import java.util.ArrayList;

import event.CollisionEvent;
import event.PlayerInputEvent;
import event.ShootEvent;
import listener.CollisionHandler;
import listener.PlayerInputHandler;
import listener.ShootHandler;
import processing.core.PApplet;

public class GameCharacter extends GameObject implements CollisionHandler, PlayerInputHandler, ShootHandler {
	private static final long serialVersionUID = 1L;
	private float moveSpeed;
	private int startX;
	private int startY;

	public boolean restart;
	public boolean canShoot;
	private ArrayList<Bullet> playerBullets;
	int screenWidth = 600;
	int screenHeight = 600;

	public GameCharacter(PApplet p, int x, int y, int h, int w, boolean isVisible) {
		super(p, x, y, h, w, isVisible);
		restart = false;
		this.moveSpeed = 5;
		this.startX = x;
		this.startY = y;
		playerBullets = new ArrayList<Bullet>();
		canShoot = true;
	}

	public void display() {
		parent.noStroke();
		parent.fill(0, 255, 255);
		parent.rect(this.x, this.y, this.r.width, this.r.height);
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public void respawn() {
		this.setX(startX);
		this.setY(startY);
		playerBullets = new ArrayList<Bullet>();
		canShoot = true;
	}

	@Override
	public void onEvent(PlayerInputEvent p) {
		
	}

	@Override
	public void onEvent(CollisionEvent c) {
		restart = true;
	}

	@Override
	public void onEvent(ShootEvent s) {
		playerBullets.add(new Bullet(parent, this.x, this.y, 20, 5, true));
	}

	public ArrayList<Bullet> getPlayerBullets() {
		return playerBullets;
	}

	public void setPlayerBullets(ArrayList<Bullet> playerBullets) {
		this.playerBullets = playerBullets;
	}
}
