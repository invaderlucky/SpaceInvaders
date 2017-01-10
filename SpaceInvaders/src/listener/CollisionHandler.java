package listener;

import event.CollisionEvent;

public interface CollisionHandler extends EventHandler {
	public void onEvent(CollisionEvent c);
}
