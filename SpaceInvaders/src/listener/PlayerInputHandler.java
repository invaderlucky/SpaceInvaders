package listener;

import event.PlayerInputEvent;

public interface PlayerInputHandler extends EventHandler {
	public void onEvent(PlayerInputEvent p);
}
