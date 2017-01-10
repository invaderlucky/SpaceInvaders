package time;

public class BaseTimeline implements Timeline {
	private long time;
	private int tic;
	
	public BaseTimeline() {
		time = 0;
		tic = 2;
	}
	
	public void incTime() {
		time += tic;
	}
	
	public long getTime() {
		return time;
	}

}
