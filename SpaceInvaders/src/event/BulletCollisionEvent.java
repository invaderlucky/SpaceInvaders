package event;

import model.Enemy;

public class BulletCollisionEvent extends Event {
	private Enemy hit;

	public BulletCollisionEvent(int priority, long time, Enemy hit) {
		super(priority, time);
		this.hit = hit;
	}
	
	public Enemy getHit() {
		return hit;
	}

}
