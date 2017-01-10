package listener;

import event.UserInputEvent;

public interface UserInputHandler extends EventHandler {

	public void onEvent(UserInputEvent u);
}
