package model;

import processing.core.PApplet;

public class DeathZone extends GameObject {
	private static final long serialVersionUID = 1L;
	
	public DeathZone(PApplet p, int x, int y, int h, int w, boolean isVisible) {
		super(p, x, y, h, w, isVisible);
	}
	
	public void display() {
		parent.noStroke();
		parent.fill(255, 0, 0);
		parent.rect(this.x, this.y, this.r.width, this.r.height);
		
	}

}
