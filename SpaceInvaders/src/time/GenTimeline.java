package time;

public class GenTimeline implements Timeline {
	long origin;
	int tic;
	Timeline anchor;
	
	// Use for custom timeline
	public GenTimeline(int tic, Timeline anchor) {
		this.tic = tic;
		this.anchor = anchor;
		this.origin = this.anchor.getTime();
	}
	
	// Change the tic size
	public void setTic(int tic) {
		this.tic = tic;
	}
	
	// Start time relative to anchor
	public long origin() {
		return origin;
	}
	
	// Current time
	public long getTime() {
		return (anchor.getTime() - origin) / tic;
	}
}
