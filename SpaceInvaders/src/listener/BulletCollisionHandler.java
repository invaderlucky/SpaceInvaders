package listener;

import event.BulletCollisionEvent;

public interface BulletCollisionHandler extends EventHandler {
	public void onEvent(BulletCollisionEvent b);
}
