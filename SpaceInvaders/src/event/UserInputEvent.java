package event;

public class UserInputEvent extends Event {
	private char key;

	public UserInputEvent(int priority, long timeStamp, char key) {
		super(priority, timeStamp);
		this.key = key;
	}
	
	public char getKey() {
		return key;
	}

}
