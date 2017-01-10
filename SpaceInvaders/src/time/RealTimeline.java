package time;

// Special timeline to represent real time
public class RealTimeline {
	// Empty on purpose
	public RealTimeline() {
		
	}
	
	// Get system time
	public long getTime() {
		return System.nanoTime();
	}
}
