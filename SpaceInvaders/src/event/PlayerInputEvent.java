package event;

public class PlayerInputEvent extends Event {
	private char key;

	public PlayerInputEvent(int priority, long timeStamp, char key) {
		super(priority, timeStamp);
		this.key = key;
	}
	
	public char getKey() {
		return key;
	}
}
