package event;

public abstract class Event implements Comparable<Event> {
	int priority; // 1 for most important
	long timeStamp;
	
	public Event(int priority, long time) {
		this.priority = priority;
		this.timeStamp = time;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	@Override
	public int compareTo(Event other) {
		// Check time first
		if (this.timeStamp < other.timeStamp)
			return -1;
		else if (this.timeStamp > other.timeStamp)
			return 1;
		
		// Check priority second
		if (this.priority < other.priority)
			return -1;
		else if (this.priority > other.priority)
			return 1;
		return 0;
	}
}
