package listener;

import event.FallEvent;

public interface FallHandler extends EventHandler {
	public void onEvent(FallEvent f);
}
