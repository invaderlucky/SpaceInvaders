package listener;

import event.SpawnEvent;

public interface SpawnHandler extends EventHandler {
	public void onEvent(SpawnEvent s);
}
