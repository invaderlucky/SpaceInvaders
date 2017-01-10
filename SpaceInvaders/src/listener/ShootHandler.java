package listener;

import event.ShootEvent;

public interface ShootHandler extends EventHandler {
	public void onEvent(ShootEvent s);
}
