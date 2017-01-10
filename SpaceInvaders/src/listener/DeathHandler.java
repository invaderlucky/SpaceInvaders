package listener;

import event.DeathEvent;

public interface DeathHandler extends EventHandler {
	public void onEvent(DeathEvent d);
}
